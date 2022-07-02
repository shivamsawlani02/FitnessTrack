package com.example.fitnesstrack;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DataOfSpecificDate extends AppCompatActivity {
    RecyclerView recyclerView;
    DataAdapter adapter;
    ArrayList<Data> arrayList;
    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_of_specific_date);
        arrayList=new ArrayList<>();
        date=getIntent().getStringExtra("Date");
        recyclerView=findViewById(R.id.rv_specific_data);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter=new DataAdapter(arrayList,this,date);
        recyclerView.setAdapter(adapter);

    }
}
