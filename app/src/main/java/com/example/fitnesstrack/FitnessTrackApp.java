package com.example.fitnesstrack;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class FitnessTrackApp extends Application {
    public void onCreate() {

        super.onCreate();
        Realm.init(this);
        Realm.setDefaultConfiguration(new RealmConfiguration.Builder().build());
    }
}
