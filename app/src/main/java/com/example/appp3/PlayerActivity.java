package com.example.appp3;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;


import com.bumptech.glide.Glide;
import com.example.appp3.model.User;
import com.example.appp3.services.OnClearFromRecentService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.concurrent.Executor;

import static com.example.appp3.AllSongsActivity.musicFiles;

public class PlayerActivity extends AppCompatActivity implements MediaPlayer.OnCompletionListener{

    TextView song_name, artist_name, duration_played, duration_total;
    ImageView cover_art, nextBtn, prevBtn, shuffleBtn, repeatBtn;
    SeekBar seekBar;
    FloatingActionButton playPauseBtn;
    static ArrayList<MusicFiles> listSongs = new ArrayList<>();
    static Uri uri;
    private Thread playThread, prevThread, nextThread;
    static MediaPlayer mediaPlayer;
    int position = -1;
    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricManager biometricManager;
    private BiometricPrompt.PromptInfo promptInfo;
    private RelativeLayout popupRelativeLayout;
    private Button loginButton;
    private ImageButton enterVault;
    private EditText passText;
    private User user;
    NotificationManager notificationManager;
    UserSQLiteHelper userSQLiteHelper;
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getExtras().getString("actionName");

            switch (action)
            {
                case CreateNotification.ACTION_PREVIOUS:
                    prevBtnClicked();
                    break;
                case CreateNotification.ACTION_PLAY:
                    playPauseBtnClicked();
                    break;
                case CreateNotification.ACTION_NEXT:
                    nextBtnClicked();
                    break;
            }
        }
    };

    /*private void onTrackPlay() {
    }

    private void onTrackNext() {
    }

    private void onTrackPause() {
    }

    private void onTrackPrev() {

    }*/

    private int enterVaultCont = 0;

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        initViews();
        receiveValues();
        CreateNotification.CreateNotification(PlayerActivity.this, listSongs.get(position), R.drawable.ic_pause_black_24dp,
                1, listSongs.size()-1);
        addEvents();
        song_name.setText(listSongs.get(position).getTitle());
        artist_name.setText(listSongs.get(position).getArtist());
        mediaPlayer.setOnCompletionListener(this);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mediaPlayer != null && fromUser) {
                    mediaPlayer.seekTo(progress * 1000);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        PlayerActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null) {
                    int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                    seekBar.setProgress(mCurrentPosition);
                    duration_played.setText(formattedTime(mCurrentPosition));
                }
                handler.postDelayed(this, 1000);
            }
        });

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            createChannel();
            registerReceiver(broadcastReceiver, new IntentFilter("TRACKS_TRACKS"));
            startService(new Intent(getBaseContext(), OnClearFromRecentService.class));
        }
    }

    private void createChannel()
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel channel = new NotificationChannel(CreateNotification.CHANNEL_ID,"Music notification",NotificationManager.IMPORTANCE_LOW);

            notificationManager = getSystemService(NotificationManager.class);
            if(notificationManager !=null)
            {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    private void addEvents()
    {
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    Intent change = new Intent(PlayerActivity.this, HiddenFilesActivity.class);
                    passText.setText("");
                    popupRelativeLayout.setVisibility(View.INVISIBLE);
                    startActivity(change);
                }
            }
        });
        popupRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupRelativeLayout.setVisibility(View.INVISIBLE);

            }
        });

        enterVault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterVaultCont++;
                if (enterVaultCont == user.getNumber_of_times()) {
                    enterVaultCont = 0;
                    biometricPrompt.authenticate(promptInfo);
                }
            }
        });
    }

    public boolean validate() {
        String password = passText.getText().toString().trim();

        //Handling validation for Password field
        if (password.isEmpty()) {
            passText.setError("Please enter valid password!");
            return false;
        } else if (password.length() < 4) {
            passText.setError(getString(R.string.passTooShort));
            return false;
        }
        if (user.getPassword().equals(password)) {
            Toast.makeText(PlayerActivity.this, getString(R.string.logged), Toast.LENGTH_SHORT).show();
            return true;
        } else {
            Toast.makeText(PlayerActivity.this, getString(R.string.loginFail), Toast.LENGTH_SHORT).show();
            return false;
        }

    }

    @Override
    protected void onResume() {
        playThreadBtn();
        nextThreadBtn();
        prevThreadBtn();
        super.onResume();
    }

    private void prevThreadBtn() {
        prevThread = new Thread() {
            @Override
            public void run() {
                super.run();
                prevBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        prevBtnClicked();
                    }
                });
            }
        };
        prevThread.start();
    }

    private void prevBtnClicked() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            position = ((position - 1) < 0 ? (listSongs.size() - 1) : (position - 1));
            CreateNotification.CreateNotification(PlayerActivity.this, listSongs.get(position), R.drawable.ic_pause_black_24dp,
                    1, listSongs.size()-1);
            uri = Uri.parse(listSongs.get(position).getPath());
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            metaData(uri);
            song_name.setText(listSongs.get(position).getTitle());
            artist_name.setText(listSongs.get(position).getArtist());
            seekBar.setMax(mediaPlayer.getDuration() / 1000);
            PlayerActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mediaPlayer != null) {
                        int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                        seekBar.setProgress(mCurrentPosition);
                    }
                    handler.postDelayed(this, 1000);
                }
            });
            mediaPlayer.setOnCompletionListener(this);
            playPauseBtn.setBackgroundResource(R.drawable.ic_player_pause);
            mediaPlayer.start();
        } else {
            mediaPlayer.stop();
            mediaPlayer.release();
            position = ((position - 1) < 0 ? (listSongs.size() - 1) : (position - 1));
            CreateNotification.CreateNotification(PlayerActivity.this, listSongs.get(position), R.drawable.ic_pause_black_24dp,
                    1, listSongs.size()-1);
            uri = Uri.parse(listSongs.get(position).getPath());
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            metaData(uri);
            song_name.setText(listSongs.get(position).getTitle());
            artist_name.setText(listSongs.get(position).getArtist());
            seekBar.setMax(mediaPlayer.getDuration() / 1000);
            PlayerActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mediaPlayer != null) {
                        int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                        seekBar.setProgress(mCurrentPosition);
                    }
                    handler.postDelayed(this, 1000);
                }
            });
            mediaPlayer.setOnCompletionListener(this);
            playPauseBtn.setBackgroundResource(R.drawable.ic_player_play_arrow);
        }
    }

    private void nextThreadBtn() {
        nextThread = new Thread() {
            @Override
            public void run() {
                super.run();
                nextBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        nextBtnClicked();
                    }
                });
            }
        };
        nextThread.start();
    }

    private void nextBtnClicked() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            position = ((position + 1) > listSongs.size() - 1 ? (0) : (position + 1));
            CreateNotification.CreateNotification(PlayerActivity.this, listSongs.get(position), R.drawable.ic_pause_black_24dp,
                    1, listSongs.size()-1);
            uri = Uri.parse(listSongs.get(position).getPath());
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            metaData(uri);
            song_name.setText(listSongs.get(position).getTitle());
            artist_name.setText(listSongs.get(position).getArtist());
            seekBar.setMax(mediaPlayer.getDuration() / 1000);
            PlayerActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mediaPlayer != null) {
                        int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                        seekBar.setProgress(mCurrentPosition);
                    }
                    handler.postDelayed(this, 1000);
                }
            });
            mediaPlayer.setOnCompletionListener(this);
            playPauseBtn.setBackgroundResource(R.drawable.ic_player_pause);
            mediaPlayer.start();
        } else {
            mediaPlayer.stop();
            mediaPlayer.release();
            position = ((position + 1) > listSongs.size() - 1 ? (0) : (position + 1));
            CreateNotification.CreateNotification(PlayerActivity.this, listSongs.get(position), R.drawable.ic_pause_black_24dp,
                    1, listSongs.size()-1);
            uri = Uri.parse(listSongs.get(position).getPath());
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            metaData(uri);
            song_name.setText(listSongs.get(position).getTitle());
            artist_name.setText(listSongs.get(position).getArtist());
            seekBar.setMax(mediaPlayer.getDuration() / 1000);
            PlayerActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mediaPlayer != null) {
                        int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                        seekBar.setProgress(mCurrentPosition);
                    }
                    handler.postDelayed(this, 1000);
                }
            });
            mediaPlayer.setOnCompletionListener(this);
            playPauseBtn.setBackgroundResource(R.drawable.ic_player_play_arrow);
        }
    }

    private void playThreadBtn() {
        playThread = new Thread() {
            @Override
            public void run() {
                super.run();
                playPauseBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        playPauseBtnClicked();
                    }
                });
            }
        };
        playThread.start();
    }

    private void playPauseBtnClicked() {
        if (mediaPlayer.isPlaying()) {
            playPauseBtn.setImageResource(R.drawable.ic_player_play_arrow);
            mediaPlayer.pause();
            CreateNotification.CreateNotification(PlayerActivity.this, listSongs.get(position), R.drawable.ic_play_arrow_black_24dp,
                    1, listSongs.size()-1);
            seekBar.setMax(mediaPlayer.getDuration() / 1000);
            PlayerActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mediaPlayer != null) {
                        int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                        seekBar.setProgress(mCurrentPosition);
                    }
                    handler.postDelayed(this, 1000);
                }
            });
        } else {
            playPauseBtn.setImageResource(R.drawable.ic_player_pause);
            mediaPlayer.start();
            CreateNotification.CreateNotification(PlayerActivity.this, listSongs.get(position), R.drawable.ic_pause_black_24dp,
                    1, listSongs.size()-1);
            seekBar.setMax(mediaPlayer.getDuration() / 1000);
            PlayerActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mediaPlayer != null) {
                        int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                        seekBar.setProgress(mCurrentPosition);
                    }
                    handler.postDelayed(this, 1000);
                }
            });
        }
    }

    private String formattedTime(int mCurrentPosition) {
        String totalOut = "";
        String totalNew = "";
        String seconds = String.valueOf(mCurrentPosition % 60);
        String minutes = String.valueOf(mCurrentPosition / 60);
        totalOut = minutes + ":" + seconds;
        totalNew = minutes + ":" + "0" + seconds;
        if (seconds.length() == 1) {
            return totalNew;
        } else {
            return totalOut;
        }
    }

    private void receiveValues() {
        position = getIntent().getIntExtra("position", -1);
        listSongs = musicFiles;
        if (listSongs != null) {
            playPauseBtn.setImageResource(R.drawable.ic_player_pause);
            uri = Uri.parse(listSongs.get(position).getPath());
        }
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            mediaPlayer.start();
        } else {
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            mediaPlayer.start();
        }
        seekBar.setMax(mediaPlayer.getDuration() / 1000);
        metaData(uri);
    }

    private void initViews() {
        passText = findViewById(R.id.passwordText);
        loginButton = findViewById(R.id.loginButton);
        enterVault = findViewById(R.id.music_note);
        song_name = findViewById(R.id.song_name);
        popupRelativeLayout = findViewById(R.id.popUp);
        artist_name = findViewById(R.id.song_artist);
        duration_played = findViewById(R.id.durationPlayed);
        duration_total = findViewById(R.id.durationTotal);
        cover_art = findViewById(R.id.song_image);
        prevBtn = findViewById(R.id.previousSong);
        nextBtn = findViewById(R.id.nextSong);
        shuffleBtn = findViewById(R.id.shuffleButton);
        repeatBtn = findViewById(R.id.repeatSong);
        playPauseBtn = findViewById(R.id.play_pause);
        seekBar = findViewById(R.id.seekBar);
        userSQLiteHelper = new UserSQLiteHelper(PlayerActivity.this);
        setUser();
        biometricManager = BiometricManager.from(this);
        switch (biometricManager.canAuthenticate()) {
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
                if (errString.equals("Use account password")) {
                    popupRelativeLayout.setVisibility(View.VISIBLE);
                } else {
                    Log.d("MY_APP_TAG", "" + errString);
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
                        getString(R.string.logged), Toast.LENGTH_SHORT).show();
                Intent change = new Intent(PlayerActivity.this, HiddenFilesActivity.class);
                startActivity(change);
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

    private void metaData(Uri uri) {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(uri.toString());
        int durationTotal = Integer.parseInt(listSongs.get(position).getDuration()) / 1000;
        duration_total.setText(formattedTime(durationTotal));
        byte[] art = retriever.getEmbeddedPicture();
        Bitmap bitmap;
        if (art != null) {
            bitmap = BitmapFactory.decodeByteArray(art,0,art.length);
            ImageAnimation(this,cover_art,bitmap);
            Glide.with(this)
                    .asBitmap()
                    .load(art)
                    .into(cover_art);
        } else {
            Glide.with(this)
                    .asBitmap()
                    .load(R.drawable.owo)
                    .into(cover_art);
        }
    }

    void setUser() {
        Cursor cursor = userSQLiteHelper.readUser();
        if (cursor.getCount() == -1) {
            Log.e("Lol", "This is working)?");
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        } else {
            Log.e("Lol", "This is not working)?");
            while (cursor.moveToNext()) {
                user = new User(cursor.getString(0),cursor.getString(1), Integer.parseInt(cursor.getString(2)));
            }
        }
    }
    public void ImageAnimation(final Context context, final ImageView imageView, final Bitmap bitmap)
    {
        final Animation animOut = AnimationUtils.loadAnimation(context, android.R.anim.fade_out);
        final Animation animIn = AnimationUtils.loadAnimation(context, android.R.anim.fade_in);
        animOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Glide.with(context).load(bitmap).into(imageView);
                animIn.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                imageView.startAnimation(animOut);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        imageView.startAnimation(animIn);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        nextBtnClicked();
        if(mediaPlayer != null)
        {
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(this);
        }
    }

    /*@Override
    protected void onDestroy() {
        super.onDestroy();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            notificationManager.cancelAll();
        }
        unregisterReceiver(broadcastReceiver);
    }*/
}
