package com.example.fitnesstrack;

import io.realm.RealmObject;

public class Fitness extends RealmObject {

    String date;

    public Fitness() {
    }

    public Fitness(String date) {
        this.date = date;
    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
