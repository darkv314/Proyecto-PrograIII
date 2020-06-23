package com.example.appp3.adapter;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appp3.R;

public class SongViewHolder extends RecyclerView.ViewHolder {
    public LinearLayout parentLinearLayout;
    public FrameLayout colorBackground;
    public TextView firstLetterTextView;
    public TextView nameSongTextView;
    public TextView albumTextView;

    public SongViewHolder(@NonNull View itemView) {
        super(itemView);
        parentLinearLayout = itemView.findViewById(R.id.parentLinearLayout);
        nameSongTextView = itemView.findViewById(R.id.nameSongTextView);
        albumTextView = itemView.findViewById(R.id.albumTextView);
        colorBackground = itemView.findViewById(R.id.colorBackgroundLetter);
        firstLetterTextView = itemView.findViewById(R.id.firstLetterTextView);
    }
}
