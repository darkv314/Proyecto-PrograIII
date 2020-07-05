package com.example.appp3.callback;

import com.example.appp3.AllSongsTask;
import com.example.appp3.MusicFiles;
import com.example.appp3.model.SongItem;

public interface SongClickCallBack {
    void onSongClick(MusicFiles song);
}