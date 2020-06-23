package com.example.appp3;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.appp3.adapter.TaskAdapter;
import com.example.appp3.model.User;
import com.example.appp3.utils.Constants;
import com.google.gson.Gson;

import  java.util.List;
import java.util.ArrayList;

public class AllSongsActivity extends AppCompatActivity {

    public static String LOG = AllSongsActivity.class.getName();
    private User user;
    private Context context;
    private List<AllSongsTask> items = new ArrayList<>();
    private Button addButton;
    private Button change;
    private Button settings;
    private TaskAdapter adapter;
    private ListView taskListView;
    private Toolbar toolbar; // Usaremos un toolbar personalizado, para agregar el icono del Drawer a la izquierda
    private ActionBarDrawerToggle drawerToggle; // El objeto del botón del drawer
    private DrawerLayout drawerLayout; // Nuestro DrawerLayout



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this;
        setContentView(R.layout.activity_menu);
        Log.d(LOG, "onCreate");
        //receiveValues();
        initViews();
        addEvents();
        fillAllSongsTasks();
        fillAllSongsTasks();
        fillAllSongsTasks();
        fillAllSongsTasks();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(LOG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(LOG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(LOG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(LOG, "onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(LOG, "onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(LOG, "onDestroy");
    }

    private void initViews() {
       // addButton = findViewById(R.id.addButton);
        taskListView = findViewById(R.id.taskListView);
        adapter = new TaskAdapter(context, items);
        taskListView.setAdapter(adapter);
        change = findViewById(R.id.songMatriz);
        settings = findViewById(R.id.settings);
    }


    private void addEvents() {
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent settings = new Intent(AllSongsActivity.this, Settings1Activity.class);
                startActivity(settings);
            }
        });
        taskListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                AllSongsTask task = items.get(position);
                Intent musicPlayer = new Intent(AllSongsActivity.this, PlayerActivity.class);
                String songName = new Gson().toJson(items.get(position));
                musicPlayer.putExtra(Constants.INTENT_SONG_NAME, songName);
                startActivity(musicPlayer);
                adapter.notifyDataSetChanged();
            }
        });
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fistrMenu = new Intent(AllSongsActivity.this, FistrMenu.class);
                startActivity(fistrMenu);
            }
        });
    }

    private void receiveValues()
    {
        Toast.makeText(AllSongsActivity.this, getString(R.string.registerSuccess),Toast.LENGTH_SHORT);
        Intent intent = getIntent();
        if (intent.hasExtra(Constants.REGISTER_SUCCESS))
        {
            String userString = intent.getStringExtra(Constants.REGISTER_SUCCESS);
            user = new Gson().fromJson(userString, User.class);
        }
    }

    private void fillAllSongsTasks() {
        items.add(new AllSongsTask(items.size(), "Can't Stop",
                "Red Hot Chilli Peppers"));
        items.add(new AllSongsTask(items.size(), "Snow (Hey Oh)",
                "Red Hot Chilli Pepper"));
        items.add(new AllSongsTask(items.size(), "Toxicity",
                "System Of A Dawn"));
        items.add(new AllSongsTask(items.size(), "High Hopes ",
                "Panic! At The Disco"));
    }
}
