package com.example.appp3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.appp3.adapter.TaskRecyclerViewAdapter;
import com.example.appp3.AllSongsClickCallback;
import com.example.appp3.AllSongsTask;
import com.example.appp3.AllSongsTask;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity {
    public static String LOG = RecyclerViewActivity.class.getName();

    private Context context;
    private List<AllSongsTask> items = new ArrayList<>();

    private RecyclerView tasksRecyclerView;
    private TaskRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_allsongs);

        initViews();
        addEvents();
        fillAllSongs();
        fillAllSongs();
        fillAllSongs();
    }

    private void initViews() {
        tasksRecyclerView = findViewById(R.id.tasksRecyclerView);
        adapter = new TaskRecyclerViewAdapter(context, items);
        tasksRecyclerView.setAdapter(adapter);
        //tasksRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        tasksRecyclerView.setLayoutManager(new GridLayoutManager(context, 2));
        /*tasksRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL));*/
    }

    private void addEvents() {
        adapter.setCallback(new AllSongsClickCallback() {
            @Override
            public void onTaskClicked(AllSongsTask task) {
                Log.e("onTaskClicked", task.getSong_name());
                Toast.makeText(context, "Song info: " + task.getSong_name(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fillAllSongs() {
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
