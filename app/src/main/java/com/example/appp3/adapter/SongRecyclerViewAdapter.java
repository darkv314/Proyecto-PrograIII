package com.example.appp3.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appp3.AllSongsTask;
import com.example.appp3.MusicFiles;
import com.example.appp3.PlayerActivity;
import com.example.appp3.callback.SongClickCallBack;
import com.example.appp3.model.SongItem;
import com.example.appp3.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SongRecyclerViewAdapter extends RecyclerView.Adapter<SongViewHolder> {

    private Context context;
    private ArrayList<MusicFiles> items;
    private LayoutInflater inflater;

    private SongClickCallBack callback;

    public SongRecyclerViewAdapter(Context context, ArrayList<MusicFiles> items) {
        this.context = context;
        this.items = items;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = this.inflater.inflate(R.layout.song_item_layout,null);
        return new SongViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull SongViewHolder holder, final int position) {
        final MusicFiles song = this.items.get(position);
        holder.nameSongTextView.setText(song.getTitle());
        holder.albumTextView.setText(song.getArtist());
        holder.firstLetterTextView.setText(Character.toString(song.getTitle().charAt(0)));
        Random random = new Random();
        int color = Color.argb(255, random.nextInt(256), random.nextInt(256),random.nextInt(256));
        holder.colorBackground.setBackgroundColor(color);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                    //callback.onSongClick(song);
                    Intent intent = new Intent(context, PlayerActivity.class);
                    intent.putExtra("position", position);
                    context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }


    public void setCallback(SongClickCallBack callback) {
        this.callback = callback;
    }

}

