package com.example.appp3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.appp3.adapter.FilesAdapter;
import com.example.appp3.adapter.ListFilesAdapter;
import com.example.appp3.model.FileModel;
import com.example.appp3.model.FilesV;
import com.example.appp3.SeeingFilesActivity;
import com.example.appp3.utils.Constants;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class HiddenFilesActivity extends AppCompatActivity {

    private Context context;
    private List<FileModel> items = new ArrayList<>();
    private List<FilesV> fileItems = new ArrayList<>();

    private ListView fileListView;
    private ListFilesAdapter adapter;
    private Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;

        setContentView(R.layout.activity_hidden_files);
        initViews();
        addEvents();
        fillQuarantineTasks();
    }
    private void initViews() {
        fileListView = findViewById(R.id.fileList);
        adapter = new ListFilesAdapter(context, items);
        fileListView.setAdapter(adapter);
        addButton = findViewById(R.id.addbutton);
    }

    private void addEvents() {
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                items.add(new FileModel(items.size(), "Archivo Nuevo",R.drawable.ic_player_playslist_play));
                adapter.notifyDataSetChanged();
            }
        });
        fileListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent fistrMenu = new Intent(HiddenFilesActivity.this, SeeingFilesActivity.class);
                startActivity(fistrMenu);
            }
        });
    }

    private void fillQuarantineTasks() {
        items.add(new FileModel(items.size(), "Archivos",R.drawable.ic_player_playslist_play));
        items.add(new FileModel(items.size(), "Imagenes",R.drawable.ic_player_playslist_play));
    }
}

