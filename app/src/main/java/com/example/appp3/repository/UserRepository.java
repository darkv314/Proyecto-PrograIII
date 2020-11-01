package com.example.appp3.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class  UserRepository {

    private Context context;
    private SharedPreferences preferences;

    //llenar campo
    public void setNumberOfTimes(int numberOfTimes) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("CLICKS", numberOfTimes);
        editor.apply();
    }
    //si no llenado == 0 mostramos pantalla
    //para los click pantalla del reproductor
    public int getNumberOfTimes() {
        return preferences.getInt("CLICKS", 10);
    }

    public void clearNumberOfTimes() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove("CLICKS");
        editor.apply();
    }
    public UserRepository(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

}