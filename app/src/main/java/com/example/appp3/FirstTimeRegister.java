package com.example.appp3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appp3.model.User;
import com.example.appp3.repository.UserRepository;

public class FirstTimeRegister extends AppCompatActivity
{
    private EditText setPassword;
    private EditText setNumberOfTimes;
    private Button doneButton;
    private Context context;
    SqliteHelper sqliteHelper;



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
        context = this;
        setContentView(R.layout.activity_form);
        UserRepository userRepository = new UserRepository( FirstTimeRegister.this);
        User userLogged = userRepository.getUserLogged();
        if(userLogged != null) {
            Intent allSongs = new Intent(FirstTimeRegister.this, AllSongsActivity.class);
            startActivity(allSongs);
        }else{
            initViews();
            addEvents();
        }

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

        UserRepository userRepository = new UserRepository(context);
        userRepository.register(user);
        sqliteHelper.addUser(user);
        Intent allSongs = new Intent(FirstTimeRegister.this, AllSongsActivity.class);
        startActivity(allSongs);
    }
}
