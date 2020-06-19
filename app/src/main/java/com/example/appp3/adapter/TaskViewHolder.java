package com.example.appp3.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appp3.R;

public class TaskViewHolder extends RecyclerView.ViewHolder {

    public LinearLayout parentLinearLayout;
    public TextView nameTextView;
    public TextView detailsTextView;

    public TaskViewHolder(@NonNull View itemView) {
        super(itemView);
        parentLinearLayout = itemView.findViewById(R.id.parentLinearLayout);
        nameTextView = itemView.findViewById(R.id.songNameView);
        detailsTextView = itemView.findViewById(R.id.songArtistView);
    }
}
