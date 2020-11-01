package com.example.appp3;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;

import java.util.Locale;

public class Settings1Activity extends AppCompatActivity {

    private Button aboutButton;
    private Button changeLang;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context=this;
        setContentView(R.layout.drawer_layout);
        initViews();
        addEvents();

    }
    private void initViews() {
        aboutButton = findViewById(R.id.about_button);
        changeLang = findViewById(R.id.langauges_but);
    }

    private void addEvents()
    {
        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent aboutB = new Intent(Settings1Activity.this, AboutUsActivity.class);
                startActivity(aboutB);
            }
        });
//        changeLang.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showChangeLanguageDialog();
//            }
//        });
    }

//    private void showChangeLanguageDialog() {
//        final String [] listenItems={"Español","English","Deutsch","日本人"};
//        AlertDialog.Builder mBuilder = new AlertDialog.Builder(Settings1Activity.this);
//        mBuilder.setTitle("Choose Language...");
//        mBuilder.setSingleChoiceItems(listenItems, -1, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int which) {
//                if(which ==0){
//                    //Spanish
//                    setLocale("es");
//                    recreate();
//                }
//                 else if(which ==1){
//                    //English
//                    setLocale("");
//                    recreate();
//                }
//                else if(which ==2){
//                    //German
//                    setLocale("de-rDE");
//                    recreate();
//                }
//                 else if(which ==3){
//                    //Japanese
//                    setLocale("ja-rJP");
//                    recreate();
//                }
//
//                 dialogInterface.dismiss();
//            }
//        });
//        AlertDialog mDialog = mBuilder.create();
//        mDialog.show();
//    }
//    private void setLocale(String lang){
//        Locale locale = new Locale(lang);
//        Locale.setDefault(locale);
//        Configuration config = new Configuration();
//        config.locale = locale;
//        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
//        SharedPreferences.Editor editor= getSharedPreferences("Settings",MODE_PRIVATE).edit();
//        editor.putString("My Lang", lang);
//        editor.apply();
//
//    }
//
//    public void loadLocate(){
//        SharedPreferences prefs = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
//        String language = prefs.getString("My_lang", "");
//        setLocale(language);
//
//    }

}
