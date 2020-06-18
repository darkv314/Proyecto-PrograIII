package com.example.appp3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.appp3.adapter.ListFilesAdapter;
import com.example.appp3.model.FileModel;

import java.util.ArrayList;
import java.util.List;

public class HiddenFilesActivity extends AppCompatActivity {

    private Context context;
    private List<FileModel> items = new ArrayList<>();

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
                items.add(new FileModel(items.size(), "Archivo Nuevo",R.drawable.file_icon));
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void fillQuarantineTasks() {
        items.add(new FileModel(items.size(), "Archivos",R.drawable.file_icon));
        items.add(new FileModel(items.size(), "Imagenes",R.drawable.image_icon));
    }
}

