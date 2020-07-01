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
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.appp3.utils.Constants;
import com.google.android.material.navigation.NavigationView;
import com.example.appp3.adapter.SongRecyclerViewAdapter;
import com.example.appp3.adapter.SongViewHolder;
import com.example.appp3.callback.SongClickCallBack;
import com.example.appp3.model.SongItem;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class FirstMenu extends AppCompatActivity{

    private Context context;
    private List<AllSongsTask> items = new ArrayList<>();
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
    private ImageButton play;
    private int pauseTime;
    private MediaPlayer mp;
    private SongViewHolder SongViewHolder;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    private AllSongsTask canc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_fistr_menu);
        canc = items.get(0);
        initialSongs();
        initViews();
        addEvents();

    }
    private void initViews() {
        play = findViewById(R.id.play);
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
            public void onSongClick(AllSongsTask song) {
                canc = song;
                nameSong.setText(song.getSong_name());
                album.setText(song.getSong_name());
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
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mp == null){
                    mp = MediaPlayer.create(FirstMenu.this, R.raw.cant_stop);
                    mp.start();
                }else if(!mp.isPlaying()){
                    mp.seekTo(pauseTime);
                    mp.start();
                    play.setImageResource(R.drawable.ic_player_pause);
                }else{
                    mp.pause();
                    pauseTime = mp.getCurrentPosition();
                    play.setImageResource(R.drawable.ic_player_play_arrow);
                }
            }
        });
        barra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent musicPlayer = new Intent(FirstMenu.this, PlayerActivity.class);
                String songName = new Gson().toJson(canc);
                musicPlayer.putExtra(Constants.INTENT_SONG_NAME, songName);
                startActivity(musicPlayer);
                adapter.notifyDataSetChanged();
            }
        });

    }
    private void initialSongs(){
        items.add(new AllSongsTask(items.size(), "Can't Stop",
                "Red Hot Chilli Peppers"));
        items.add(new AllSongsTask(items.size(), "Snow (Hey Oh)",
                "Red Hot Chilli Pepper"));
        items.add(new AllSongsTask(items.size(), "Toxicity",
                "System Of A Dawn"));
        items.add(new AllSongsTask(items.size(), "High Hopes",
                "Panic! At The Disco"));
        items.add(new AllSongsTask(items.size(), "All That She Wants",
                "Ace of Base"));
        items.add(new AllSongsTask(items.size(), "Your Woman",
                "White Town"));
        items.add(new AllSongsTask(items.size(), "Fantasy",
                "Mariah Carey"));
        items.add(new AllSongsTask(items.size(), "Undone-The Sweater Song",
                "Weezer"));
        items.add(new AllSongsTask(items.size(), "Cannonball",
                "The Breeders"));
        items.add(new AllSongsTask(items.size(), "Killing My Softly",
                "The Fugges"));
        items.add(new AllSongsTask(items.size(), "Loaded",
                "Primal Screen"));
        items.add(new AllSongsTask(items.size(), "...Baby One MOre Time",
                "Britney Spears"));
        items.add(new AllSongsTask(items.size(), "Torn",
                "Natalie Imbruglia"));
        items.add(new AllSongsTask(items.size(), "On a Ragga Tip",
                "SL2"));
        items.add(new AllSongsTask(items.size(), "Say You'll Be There",
                "Spice Girls"));
        items.add(new AllSongsTask(items.size(), "You Don't Know Me",
                "Armand Van Helden"));
        items.add(new AllSongsTask(items.size(), "Ray of Light",
                "Madonna"));
        items.add(new AllSongsTask(items.size(), "Pony",
                "Guinuwine"));
        items.add(new AllSongsTask(items.size(), "1979",
                "Smashing Pumpkings"));
        items.add(new AllSongsTask(items.size(), "You Oughta Know",
                "Alanis Morisette"));
        items.add(new AllSongsTask(items.size(), "Animale Nitrade",
                "Suede"));
        items.add(new AllSongsTask(items.size(), "Step On",
                "Happy Mondays"));
        items.add(new AllSongsTask(items.size(), "Windowlicker",
                "Aphex Twin"));
        items.add(new AllSongsTask(items.size(), "Rebel Girl",
                "Bikini Kill"));
        items.add(new AllSongsTask(items.size(), "Doo Wop(That Thing)",
                "Lauryn Hill"));
        items.add(new AllSongsTask(items.size(), "Enter Sandman",
                "Metallica"));
        items.add(new AllSongsTask(items.size(), "Connection",
                "Elastica"));
        items.add(new AllSongsTask(items.size(), "Juicy",
                "The Notorious Big"));
        items.add(new AllSongsTask(items.size(), "Poison",
                "The Prodigy"));
        items.add(new AllSongsTask(items.size(), "Inner City",
                "Goldie"));
        items.add(new AllSongsTask(items.size(), "Bittersweet Symphony",
                "The Verve"));
        items.add(new AllSongsTask(items.size(), "Killing in the Name",
                "Rage Against The Machine"));
        items.add(new AllSongsTask(items.size(), "Sure Shot",
                "Beastie Boys"));
        items.add(new AllSongsTask(items.size(), "Nothing Compare 2 U'",
                "Sinnnead O'Connor"));
        items.add(new AllSongsTask(items.size(), "Born Slippy.Nuxx",
                "Underworld"));
        items.add(new AllSongsTask(items.size(), "Loser",
                "Beck"));
        items.add(new AllSongsTask(items.size(), "Deceptacon",
                "La Tigre"));
        items.add(new AllSongsTask(items.size(), "Paranoid Android",
                "Radiohead"));
    }
}

