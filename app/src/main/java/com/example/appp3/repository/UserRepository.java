package com.example.appp3.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.appp3.model.User;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class  UserRepository {
//    private static UserRepository instance;

    private List<User> users = new ArrayList<>();
    private Context context;
    private SharedPreferences preferences;
//    public static UserRepository getInstance() {
//        if (instance == null) {
//            instance = new UserRepository();
//        }
//        return instance;
//    }

    public UserRepository(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public boolean login(int numero) {
        for (User user : users) {
            if (Integer.parseInt(user.getNumber_of_times()) == numero) {
                return true;
            }
        }
        return false;
    }

    public void register(User user) {
        users.add(user);
    }

    public void setUserLogged(User userLogged) {

        String userLoggedString = new Gson().toJson(userLogged);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("user",userLoggedString);
        editor.putLong("timestamp", Calendar.getInstance().getTime().getTime());
        editor.apply();

    }
    public User getUserLogged(){

        if(preferences.contains("user")){

            String userLoggedString = preferences.getString("user", null);

            if(userLoggedString != null){
                if(preferences.contains("timestamp")){

                    long timestamp = preferences.getLong("timestamp", 0);
                    Date date = new Date(timestamp);
                    Log.e("Ultimo acceso", date.toLocaleString());
                }

                User userLogged = new Gson().fromJson(userLoggedString, User.class);
                return userLogged;
            }
        }
        return null;
    }

    public void deleteUserLogged() {
        //Editor y eliminamos el valor
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove("user");
        editor.apply();
    }
}
