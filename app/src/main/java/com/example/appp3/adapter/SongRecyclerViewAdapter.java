package com.example.appp3.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appp3.AllSongsTask;
import com.example.appp3.callback.SongClickCallBack;
import com.example.appp3.model.SongItem;
import com.example.appp3.R;

import java.util.List;
import java.util.Random;

public class SongRecyclerViewAdapter extends RecyclerView.Adapter<SongViewHolder> {

    private Context context;
    private List<AllSongsTask> items;
    private LayoutInflater inflater;

    private SongClickCallBack callback;

    public SongRecyclerViewAdapter(Context context, List<AllSongsTask> items) {
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
    public void onBindViewHolder(@NonNull SongViewHolder holder, int position) {
        final AllSongsTask song = this.items.get(position);
        holder.nameSongTextView.setText(song.getSong_name());
        holder.albumTextView.setText(song.getSong_artist());
        holder.firstLetterTextView.setText(song.getLetter());
        Random random = new Random();
        int color = Color.argb(255, random.nextInt(256), random.nextInt(256),random.nextInt(256));
        holder.colorBackground.setBackgroundColor(color);
        holder.parentLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(callback != null){
                    callback.onSongClick(song);
                }
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

