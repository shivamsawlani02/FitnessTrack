package com.example.fitnesstrack;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.text.DateFormat;
import java.util.Calendar;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    EditText setsE,repsE;
    Button addData,date,history;
    Realm realm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();



//        date.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                DialogFragment datepicker=new DatePickerFragment();
//                datepicker.show(getSupportFragmentManager(),"date picker");
//            }
//        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent history=new Intent(MainActivity.this,History.class);
                startActivity(history);
            }
        });
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent add=new Intent(MainActivity.this,AddData.class);
                startActivity(add);
            }
        });

//        addData.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(date.getText().toString().equals("Select Date"))
//                    Toast.makeText(MainActivity.this, "Please enter date", Toast.LENGTH_SHORT).show();
//                else if(setsE.getText().toString().isEmpty())
//                    Toast.makeText(MainActivity.this, "Please enter Sets", Toast.LENGTH_SHORT).show();
//                else if(repsE.getText().toString().isEmpty())
//                    Toast.makeText(MainActivity.this, "Please enter Reps", Toast.LENGTH_SHORT).show();
//                else {
//                    int s = Integer.parseInt(setsE.getText().toString());
//                    int r = Integer.parseInt(repsE.getText().toString());
//                    int product = s * r;
//                    String p = Integer.toString(product);
//                    Fitness fitness = new Fitness(date.getText().toString());
//
//                    realm = Realm.getDefaultInstance();
//                    realm.beginTransaction();
//                    realm.insertOrUpdate(fitness);
//                    realm.commitTransaction();
//                    Toast.makeText(MainActivity.this, "Data saved successfully", Toast.LENGTH_SHORT).show();
//
//
//                }
//
//            }
//        });
    }

    private void init() {
        setsE=findViewById(R.id.ed_sets);
        repsE=findViewById(R.id.ed_reps);
        //date=findViewById(R.id.date);
        addData=findViewById(R.id.save);
        history=findViewById(R.id.history);

    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        Calendar c=Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,month);
        c.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        String currentDateString= DateFormat.getDateInstance().format(c.getTime());
        //date.setText(currentDateString);
    }
}
