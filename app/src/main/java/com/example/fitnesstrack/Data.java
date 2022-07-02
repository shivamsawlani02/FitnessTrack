package com.example.fitnesstrack;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Data extends RealmObject {
    @PrimaryKey
    private String date;
    private String exerciseName;
    private String sets, reps,weights;

    public Data() {
    }

    public Data(String date, String sets, String reps, String weights, String exerciseName) {
        this.date = date;
        this.sets = sets;
        this.reps = reps;
        this.exerciseName = exerciseName;
        this.weights=weights;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSets() {
        return sets;
    }

    public void setSets(String sets) {
        this.sets = sets;
    }

    public String getReps() {
        return reps;
    }

    public void setReps(String reps) {
        this.reps = reps;
    }

    public String getWeights() {
        return weights;
    }

    public void setWeights(String weights) {
        this.weights = weights;
    }

    public String getVolume() {
        int r=Integer.parseInt(reps);
        int s=Integer.parseInt(sets);
        int w=Integer.parseInt(weights);
        int v=r*s*w;
        return String.valueOf(v);
    }
}
