package com.example.fitnesstrack;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import io.realm.Realm;

public class AddData extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    TextView date;
    EditText exerName,sets, reps,weights;
    Button addData;
    RecyclerView recyclerView;
    DataAdapter adapter;
    Realm realm1,realm2;
    ArrayList<Data> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);
        init();
        arrayList=new ArrayList<>();
        date.setOnClickListener(view -> {
            DialogFragment datepicker = new DatePickerFragment();
            datepicker.show(getSupportFragmentManager(), "date picker");
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter=new DataAdapter(arrayList,this,date.getText().toString());
        addData.setOnClickListener(view -> {
            if (check()) {
                Data data = new Data(date.getText().toString(), sets.getText().toString(), reps.getText().toString(),weights.getText().toString(), exerName.getText().toString());
                adapter.addData(data);
                realm2 = Realm.getDefaultInstance();
                realm2.beginTransaction();
                realm2.insertOrUpdate(data);
                realm2.commitTransaction();

                Fitness fitness = new Fitness(date.getText().toString());
                realm1= Realm.getDefaultInstance();
                realm1.beginTransaction();
                realm1.insertOrUpdate(fitness);
                realm1.commitTransaction();

                exerName.setText("");
                sets.setText("");
                reps.setText("");
                weights.setText("");
            }
        });
        recyclerView.setAdapter(adapter);
    }

    void init() {
        exerName = findViewById(R.id.ed_exercise_name);
        sets = findViewById(R.id.ed_sets);
        reps = findViewById(R.id.ed_reps);
        addData = findViewById(R.id.add_data);
        weights=findViewById(R.id.ed_weights);
        date = findViewById(R.id.select_date);
        recyclerView = findViewById(R.id.rv_data);
        //realm=Realm.getDefaultInstance();

    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance().format(c.getTime());
        date.setText(currentDateString);

    }
    private boolean check()
    {
        if(date.getText().toString().equals("Select Date"))
        {
            Toast.makeText(this, "Please select date", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (exerName.getText().toString().isEmpty())
        {
            Toast.makeText(this, "Add exercise Name", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (sets.getText().toString().isEmpty())
        {
            Toast.makeText(this, "Add sets", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (reps.getText().toString().isEmpty())
        {
            Toast.makeText(this, "Add reps", Toast.LENGTH_SHORT).show();
            return false;
        }
        else
        {
            return true;
        }
    }
}
