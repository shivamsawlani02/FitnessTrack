package com.example.fitnesstrack;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import pub.devrel.easypermissions.EasyPermissions;

public class SignUp extends AppCompatActivity {
    EditText name, email, password;
    Button signin;
    TextView login;
    ImageView add_from_camera,add_from_gallery;
    FirebaseAuth fAuth;
    StorageReference fStorageRef;
    DatabaseReference fDataBaseRef;
    ProgressBar progressBar;
    ImageView profilePhoto;
//    String imgPath;
//    boolean imgflag=false;
    private Uri mImageUri;
    private static final int PERTMISSION_REQUEST_CODE = 777;
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 778;
    private static final int GALLERY_REQUEST_CODE = 107;
    private static final int CAMERA_REQUEST = 108;
    Dialog cameraOrGalleryDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        attachID();

        profilePhoto.setOnClickListener(view -> {
            cameraOrGalleryDialog.setContentView(R.layout.custom_dialog);
            add_from_camera=cameraOrGalleryDialog.findViewById(R.id.camera_button);
            add_from_gallery=cameraOrGalleryDialog.findViewById(R.id.gallery_button);

            add_from_camera.setOnClickListener(view1 -> {
                cameraOrGalleryDialog.dismiss();
                if(EasyPermissions.hasPermissions(SignUp.this,
                        Manifest.permission.CAMERA)){
                    opencamera();
                }else {
                    EasyPermissions.requestPermissions(SignUp.this,
                            "ALLOW CAMERA ACCESS",
                            CAMERA_PERMISSION_REQUEST_CODE,
                            Manifest.permission.CAMERA);
                }
            });
            add_from_gallery.setOnClickListener(view12 -> {
                cameraOrGalleryDialog.dismiss();

                if (EasyPermissions.hasPermissions(SignUp.this, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    openGallery();
                } else
                    EasyPermissions.requestPermissions(SignUp.this, "Allow this app to access your storage", PERTMISSION_REQUEST_CODE,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE);

            });
            cameraOrGalleryDialog.show();

        });

        signin.setOnClickListener(view -> {
            final String n = name.getText().toString();
            final String mail = email.getText().toString().trim();
            String pass = password.getText().toString();
            if(n.isEmpty()||pass.isEmpty()||mail.isEmpty())
            {
                Toast.makeText(this, "Fields are empty", Toast.LENGTH_SHORT).show();
            }
            else {
                progressBar.setVisibility(View.VISIBLE);
                fAuth.createUserWithEmailAndPassword(mail, pass).addOnSuccessListener(authResult -> {
                    User user = new User(n, mail);
                    FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user);
                    upload();
                    Toast.makeText(SignUp.this, "User created Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUp.this, StepsDaily.class);
                    startActivity(intent);
                    finish();
                })
                        .addOnFailureListener(e -> {
                            Toast.makeText(SignUp.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);
                        });
            }
        });



        login.setOnClickListener(view -> {
            Intent loginPage = new Intent(SignUp.this, Login.class);
            startActivity(loginPage);
        });
    }
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
        Picasso.get().load(data.getData()).into(profilePhoto);
        mImageUri=data.getData();
        //picasso directly displays images from its uri.
    }
    if (requestCode == CAMERA_REQUEST &&
            resultCode == RESULT_OK &&
            data != null &&
            data.getExtras() != null) {
        Bitmap photo = (Bitmap) data.getExtras().get("data");
        profilePhoto.setImageBitmap(photo);
        mImageUri=data.getData();
    }
}
    private void attachID() {
        name = findViewById(R.id.ed_name);
        email = findViewById(R.id.ed_email);
        password = findViewById(R.id.ed_pass);
        //phone = findViewById(R.id.ed_phone);
        signin = findViewById(R.id.btnl_signup);
        login = findViewById(R.id.tv_login);
        progressBar = findViewById(R.id.pgbar);
        fAuth = FirebaseAuth.getInstance();
        profilePhoto=findViewById(R.id.person_photo);
        fStorageRef=FirebaseStorage.getInstance().getReference();
        fDataBaseRef=FirebaseDatabase.getInstance().getReference();
        cameraOrGalleryDialog= new Dialog(this);
    }
    private void upload() {
        if (mImageUri != null) {
            StorageReference profileRef = fStorageRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()+"profile.jpg");

            profileRef.putFile(mImageUri)
                    .addOnSuccessListener(taskSnapshot -> {
                        // Get a URL to the uploaded content
//                            Uri downloadUrl = taskSnapshot.getDownloadUrl();
                       // Toast.makeText(SignUp.this, "Upload Successful", Toast.LENGTH_SHORT).show();
                        profileRef.getDownloadUrl();
                    })
                    .addOnFailureListener(exception ->
                            Toast.makeText(SignUp.this,exception.getMessage(), Toast.LENGTH_SHORT).show());
        }
        else {
            Toast.makeText(this, "Choose an image", Toast.LENGTH_SHORT).show();
        }
    }
private void opencamera()
{
    Log.i("FUNCTION", "openCamera() started");
    Intent cameraIntent = new Intent(
            android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
    startActivityForResult(cameraIntent, CAMERA_REQUEST);
}
    private void openGallery() {
        Log.i("FUNCTION", "openGallery() started");
        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE);
    }
}
