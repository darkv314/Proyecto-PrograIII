package com.example.appp3;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import com.example.appp3.adapter.SongRecyclerViewAdapter;
import com.example.appp3.adapter.SongViewHolder;
import com.example.appp3.callback.SongClickCallBack;
import com.example.appp3.model.SongItem;

import java.util.ArrayList;
import java.util.List;

public class FirstMenu extends AppCompatActivity{

    private Context context;
    private List<SongItem> items = new ArrayList<>();
    private FrameLayout backGroundBarra;
    private TextView nameSong;
    private TextView album;
    private TextView letra;
    private TextView song_name;
    private TextView song_artist;
    private Button change;
    private Button settings;
    private LinearLayout barra;

    private RecyclerView songRecyclerView;
    private SongRecyclerViewAdapter adapter;
    private SongViewHolder SongViewHolder;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Toolbar toolbar;
    private NavigationView navigationView;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_fistr_menu);
        initialSongs();
        initViews();
        addEvents();

    }
    private void initViews() {
        settings = findViewById(R.id.settings1);
        change = findViewById(R.id.listSongs);
        barra = findViewById(R.id.barraReproductor);
        song_artist = findViewById(R.id.song_artist);
        song_name = findViewById(R.id.song_name);
        backGroundBarra = findViewById(R.id.reproductorBackground);
        nameSong = findViewById(R.id.songReproductor);
        album = findViewById(R.id.albumReproductor);
        letra = findViewById(R.id.reproductorLetterTextView);
        songRecyclerView = findViewById(R.id.songRecyclerView);
        adapter = new SongRecyclerViewAdapter(context, items);
        songRecyclerView.setAdapter(adapter);
        songRecyclerView.setLayoutManager(new GridLayoutManager(context, 3));
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();




    }
    private void addEvents() {
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent settings = new Intent(FirstMenu.this, Settings1Activity.class);
                startActivity(settings);
            }
        });
        adapter.setCallback(new SongClickCallBack() {
            @Override
            public void onSongClick(SongItem song) {
                nameSong.setText(song.getName());
                album.setText(song.getAlbum());
                letra.setText(song.getLetter());
                adapter.notifyDataSetChanged();
            }
        });
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent change = new Intent(FirstMenu.this, AllSongsActivity.class);
                startActivity(change);
            }
        });

    }
    private void initialSongs(){
        items.add(new SongItem(items.size(), "Can't Stop",
                "Red Hot Chilli Peppers","C"));
        items.add(new SongItem(items.size(), "Snow (Hey Oh)",
                "Red Hot Chilli Pepper","S"));
        items.add(new SongItem(items.size(), "Toxicity",
                "System Of A Dawn","T"));
        items.add(new SongItem(items.size(), "High Hopes ",
                "Panic! At The Disco","H"));
    }
}

