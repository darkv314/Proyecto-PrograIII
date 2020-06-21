package com.example.appp3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.appp3.adapter.SongRecyclerViewAdapter;
import com.example.appp3.adapter.SongViewHolder;
import com.example.appp3.callback.SongClickCallBack;
import com.example.appp3.model.SongItem;

import java.util.ArrayList;
import java.util.List;

public class FistrMenu extends AppCompatActivity {

    private Context context;
    private List<SongItem> items = new ArrayList<>();
    private FrameLayout backGroundBarra;
    private TextView nameSong;
    private TextView album;
    private TextView letra;

    private RecyclerView songRecyclerView;
    private SongRecyclerViewAdapter adapter;
    private SongViewHolder SongViewHolder;

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
        backGroundBarra = findViewById(R.id.reproductorBackground);
        nameSong = findViewById(R.id.songReproductor);
        album = findViewById(R.id.albumReproductor);
        letra = findViewById(R.id.reproductorLetterTextView);
        songRecyclerView = findViewById(R.id.songRecyclerView);
        adapter = new SongRecyclerViewAdapter(context, items);
        songRecyclerView.setAdapter(adapter);
        songRecyclerView.setLayoutManager(new GridLayoutManager(context, 3));

    }
    private void addEvents() {
        adapter.setCallback(new SongClickCallBack() {
            @Override
            public void onSongClick(SongItem song) {
                nameSong.setText(song.getName());
                album.setText(song.getAlbum());
                letra.setText(song.getLetter());
                adapter.notifyDataSetChanged();
            }
        });
    }
    private void initialSongs(){
        items.add(new SongItem(items.size(),"Despacito","Album Desconocido","D"));
        items.add(new SongItem(items.size(),"Toy","Album Desconocido","T"));
        items.add(new SongItem(items.size(),"Musica che resta","Album Desconocido","M"));
        items.add(new SongItem(items.size(),"Vicinissimo","Album Desconocido","V"));
        items.add(new SongItem(items.size(),"O'sole mio","Album Desconocido","O"));
    }
}
