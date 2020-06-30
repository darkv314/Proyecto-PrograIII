package com.example.appp3.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public class  UserRepository {
    //    private static UserRepository instance;
    private Context context;
    private SharedPreferences preferences;
//    public static UserRepository getInstance() {
//        if (instance == null) {
//            instance = new UserRepository();
//        }
//        return instance;
//    }
//
    //
    // Intent musicPlayer = new Intent(AllSongsActivity.this, PlayerActivity.class);
    //                String songName = new Gson().toJson(items.get(position));
    //
    //                musicPlayer.putExtra(Constants.INTENT_SONG_NAME, songName);
    //
    //
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