package com.example.fitnesstrack;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.DataVH> {
    ArrayList<Data> arrayList;
    Context context;
    Realm realm;
    String date;

    public DataAdapter(ArrayList<Data> arrayList, Context context,String date) {
        this.arrayList = arrayList;
        this.context = context;
        realm= Realm.getDefaultInstance();
        this.date=date;
        loadData();
    }
    private void loadData()
    //method to retrieve data from realm database.We send a query and realm sends us a response.
    {
        RealmQuery<Data> realmQuery = realm.where(Data.class);
        RealmResults<Data> realmResults = realmQuery.equalTo("date",date).findAll();
        arrayList.addAll(realmResults);
        notifyDataSetChanged();
    }
    public void addData(Data data){
        arrayList.add(data);
        realm.beginTransaction();
        realm.insertOrUpdate(data);
        realm.commitTransaction();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DataVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DataAdapter.DataVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_data,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull DataVH holder, int position) {
        holder.populate(arrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class DataVH extends RecyclerView.ViewHolder
    {
        TextView exerciseName,sets,reps,weights,volume;

        public DataVH(@NonNull View itemView) {
            super(itemView);
            exerciseName=itemView.findViewById(R.id.exercise_name);
            sets=itemView.findViewById(R.id.sets);
            reps=itemView.findViewById(R.id.reps);
            weights=itemView.findViewById(R.id.weights);
            volume=itemView.findViewById(R.id.volume);
        }
        void populate(Data data)
        {
            exerciseName.setText(data.getExerciseName());
            sets.setText(data.getSets());
            reps.setText(data.getReps());
            weights.setText(data.getWeights());
            int s=Integer.parseInt(data.getSets());
            int r=Integer.parseInt(data.getReps());
            int w=Integer.parseInt(weights.getText().toString());
            volume.setText(String.valueOf(s*r*w));
        }
    }
}
