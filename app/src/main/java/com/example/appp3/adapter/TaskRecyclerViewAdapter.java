package com.example.appp3.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appp3.AllSongsTask;
import com.example.appp3.R;
import com.example.appp3.AllSongsClickCallback;
import com.example.appp3.AllSongsTask;

import java.util.List;

public class TaskRecyclerViewAdapter extends RecyclerView.Adapter<TaskViewHolder> {
    private Context context;
    private List<AllSongsTask> items;
    private LayoutInflater inflater;
    private AllSongsClickCallback callback;

    public TaskRecyclerViewAdapter(Context context, List<AllSongsTask> items) {
        this.context = context;
        this.items = items;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = this.inflater.inflate(R.layout.activity_allsongs, null);
        return new TaskViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        final AllSongsTask task = this.items.get(position);
        holder.nameTextView.setText(task.getSong_name());
        holder.detailsTextView.setText(task.getSong_artist());

        holder.parentLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callback != null) {
                    callback.onTaskClicked(task);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    public void setCallback(AllSongsClickCallback callback) {
        this.callback = callback;
    }
}
