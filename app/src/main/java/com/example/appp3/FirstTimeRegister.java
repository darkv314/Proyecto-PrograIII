package com.example.appp3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class FirstTimeRegister extends AppCompatActivity
{
    private EditText setPassword;
    private EditText setNumberOfTimes;
    private Button doneButton;
    UserSQLiteHelper userSQLiteHelper;

    String firstRegister = "firstRegister";
    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedpreferences = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
        if (!sharedpreferences.getBoolean(firstRegister, false)) {
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putBoolean(firstRegister, Boolean.TRUE);
            editor.apply();
        } else {
            Intent allSongs = new Intent(FirstTimeRegister.this, AllSongsActivity.class);
            startActivity(allSongs);
        }
    }
    @Override
    protected void onPause(){
        super.onPause();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        initViews();
        addEvents();
    }

    private void initViews()
    {
        setPassword = findViewById(R.id.passwordText);
        setNumberOfTimes = findViewById(R.id.numberOfTimes);
        doneButton = findViewById(R.id.doneButton);
        userSQLiteHelper = new UserSQLiteHelper(this);
    }

    private void addEvents()
    {
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateRegister();
            }
        });
    }

    private void validateRegister()
    {
        String pass = setPassword.getText().toString().trim();
        String nOfTimes =  setNumberOfTimes.getText().toString().trim();
        if(pass.isEmpty())
        {
            setPassword.setError(getString(R.string.emptyError));
            return;
        }
        else if(pass.length()<4)
        {
            setPassword.setError(getString(R.string.passTooShort));
            return;
        }
        if(nOfTimes.isEmpty())
        {
            setNumberOfTimes.setError(getString(R.string.emptyError));
            return;
        }
        else if(nOfTimes.equals("0"))
        {
            setNumberOfTimes.setError(getString(R.string.numberNotAccepted));
            return;
        }
        userSQLiteHelper.addUser(pass, Integer.parseInt(nOfTimes));
        Intent allSongs = new Intent(FirstTimeRegister.this, AllSongsActivity.class);
        startActivity(allSongs);
    }
}
