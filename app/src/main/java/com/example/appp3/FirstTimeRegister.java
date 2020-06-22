package com.example.appp3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appp3.model.User;
import com.example.appp3.utils.Constants;
import com.google.gson.Gson;

public class FirstTimeRegister extends AppCompatActivity
{
    private EditText setPassword;
    private EditText setNumberOfTimes;
    private Button doneButton;
    SqliteHelper sqliteHelper;
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
        sqliteHelper = new SqliteHelper(this);
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
        User user = new User(null, nOfTimes, pass);
        sqliteHelper.addUser(user);
        Intent allSongs = new Intent(FirstTimeRegister.this, AllSongsActivity.class);
        startActivity(allSongs);
    }
}
