package com.example.fitnesstrack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    EditText email, password;
    Button login;
    TextView signin;
    ProgressBar progressBar;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        attachID();
        if (fAuth.getCurrentUser() != null) {
            startActivity(new Intent(Login.this,StepsDaily.class));
            finish();
        }
        login.setOnClickListener(view -> {
            final String user = email.getText().toString();
            final String pwd = password.getText().toString();
            if(user.isEmpty()||pwd.isEmpty())
            {
                Toast.makeText(this, "Fields are empty", Toast.LENGTH_SHORT).show();
            }
            else {

                progressBar.setVisibility(View.VISIBLE);
                fAuth.signInWithEmailAndPassword(user, pwd).addOnSuccessListener(authResult -> {
                    Toast.makeText(Login.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Login.this, StepsDaily.class));
                    finish();
                })
                        .addOnFailureListener(e -> Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_SHORT).show());
            }

        });
        signin.setOnClickListener(view -> {
            Intent signUpPage=new Intent(Login.this,SignUp.class);
            startActivity(signUpPage);
        });

    }

    private void attachID() {
        email = findViewById(R.id.ed_email);
        password = findViewById(R.id.ed_pass);
        login = findViewById(R.id.btnl_login);
        signin=findViewById(R.id.tv_signup);
        progressBar=findViewById(R.id.pgbar);
        fAuth=FirebaseAuth.getInstance();

    }
}
