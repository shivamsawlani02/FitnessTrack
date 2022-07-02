package com.example.fitnesstrack;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class FitnessAdapter extends RecyclerView.Adapter<FitnessAdapter.FitnessViewHolder>{

    ArrayList<Fitness> arrayList;
    Context context;
    Realm realm;

    public FitnessAdapter(ArrayList<Fitness> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
        realm= Realm.getDefaultInstance();
        loadData();
    }

    private void loadData()
    //method to retrieve data from realm database.We send a query and realm sends us a response.
    {
        RealmQuery<Fitness> realmQuery = realm.where(Fitness.class);
        RealmResults<Fitness> realmResults = realmQuery.findAll();
        arrayList=new ArrayList<>();
        arrayList.addAll(realmResults);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FitnessViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FitnessViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fitness,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull FitnessViewHolder holder, int position) {
        holder.populateFitness(arrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class FitnessViewHolder extends RecyclerView.ViewHolder
    {
        TextView date,x,sets,reps;
        LinearLayout linearLayout;
        public FitnessViewHolder(@NonNull View itemView) {
            super(itemView);

            date=itemView.findViewById(R.id.date_rv);
//            x=itemView.findViewById(R.id.x_rv);
//            sets=itemView.findViewById(R.id.sets_rv);
//            reps=itemView.findViewById(R.id.reps_rv);
            linearLayout=itemView.findViewById(R.id.linear_layout);
        }
        void populateFitness(final Fitness fitness)
        {
            date.setText(fitness.getDate());
//            x.setText(fitness.getX());
//            sets.setText(fitness.getSet());
//            reps.setText(fitness.getRep());
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(context,DataOfSpecificDate.class);
                    intent.putExtra("Date",date.getText().toString());
                    context.startActivity(intent);
                }
            });
            linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    openDialog(fitness);
                    return true;
                }
            });

        }
    }
    public void openDialog(final Fitness f)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setTitle("Delete");
        builder.setMessage("Do you want to delete this item?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                realm.beginTransaction();
                arrayList.remove(f);
                notifyDataSetChanged();//this sets adapter again so data is refreshed.
                f.deleteFromRealm();
                realm.commitTransaction();
            }
        });
        builder.show();
    }
}
