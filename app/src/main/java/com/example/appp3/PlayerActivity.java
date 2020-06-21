package com.example.appp3;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import com.example.appp3.utils.Constants;
import com.google.gson.Gson;

import java.util.concurrent.Executor;

public class PlayerActivity extends AppCompatActivity
{
    public static String LOG = AllSongsActivity.class.getName();
    private ImageButton enterVault;
    private TextView songName;
    private TextView songArtist;
    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricManager biometricManager;
    private BiometricPrompt.PromptInfo promptInfo;
    private Context context=this;
    private RelativeLayout popupRelativeLayout;
    private AllSongsTask song;
    private int enterVaultCont = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        receiveValues();
        initViews();
        addEvents();
    }

    private void receiveValues()
    {
        Intent intent = getIntent();
        if (intent.hasExtra(Constants.INTENT_SONG_NAME))
        {
            String songName = intent.getStringExtra(Constants.INTENT_SONG_NAME);
            song = new Gson().fromJson(songName, AllSongsTask.class);
        }
    }

    private void initViews()
    {
        popupRelativeLayout = findViewById(R.id.popUp);
        popupRelativeLayout.setVisibility(View.INVISIBLE);
        songArtist = findViewById(R.id.song_artist);
        songName = findViewById(R.id.song_name);
        songName.setText(song.getSong_name());
        songArtist.setText(song.getSong_artist());
        enterVault = findViewById(R.id.music_note);
        biometricManager = BiometricManager.from(this);
        switch (biometricManager.canAuthenticate())
        {
            case BiometricManager.BIOMETRIC_SUCCESS:
                Log.d("MY_APP_TAG", "App can authenticate using biometrics.");
                break;
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                Log.e("MY_APP_TAG", "No biometric features available on this device.");
                break;
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                Log.e("MY_APP_TAG", "Biometric features are currently unavailable.");
                break;
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                Log.e("MY_APP_TAG", "The user hasn't associated " +
                        "any biometric credentials with their account.");
                break;
        }
        executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(PlayerActivity.this,
                executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode,
                                              @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                if(errString.equals("Use account password"))
                {
                    popupRelativeLayout.setVisibility(View.VISIBLE);
                }
                else
                {
                    Log.d("MY_APP_TAG",""+errString);
                    Toast.makeText(getApplicationContext(),
                            "Authentication error: " + errString, Toast.LENGTH_SHORT)
                            .show();
                }
            }

            @Override
            public void onAuthenticationSucceeded(
                    @NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(getApplicationContext(),
                        "Authentication succeeded!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(getApplicationContext(), "Authentication failed",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric login for my app")
                .setSubtitle("Log in using your biometric credential")
                .setNegativeButtonText("Use account password")
                .build();

    }
    private void addEvents()
    {
        popupRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupRelativeLayout.setVisibility(View.INVISIBLE);
            }
        });

        enterVault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                enterVaultCont++;
                if(enterVaultCont==3)//////////////////
                {
                    enterVaultCont = 0;
                    biometricPrompt.authenticate(promptInfo);
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(LOG, "onStart1");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(LOG, "onResume1");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(LOG, "onPause1");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(LOG, "onStop1");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(LOG, "onRestart1");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(LOG, "onDestroy1");
    }
}
