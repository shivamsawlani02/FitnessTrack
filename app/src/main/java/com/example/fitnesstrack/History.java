package com.example.fitnesstrack;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class History extends AppCompatActivity {

    RecyclerView recyclerView;
    FitnessAdapter adapter;
    ArrayList<Fitness> arrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        arrayList=new ArrayList<>();
        //adapter=new FitnessAdapter(arrayList,this);
        initRecyclerView();
    }

    private void initRecyclerView() {
        recyclerView=findViewById(R.id.recycler_view);
        adapter=new FitnessAdapter(arrayList,this);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setAdapter(adapter);
    }
}
