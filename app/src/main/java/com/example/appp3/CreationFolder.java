package com.example.appp3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.appp3.model.FileModel;
import com.example.appp3.utils.Constants;
import com.google.gson.Gson;

public class CreationFolder extends AppCompatActivity {

    private EditText name;
    private EditText path;
    private Button send;
    private Button clean;
    long cont = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation_folder);
        initViews();
        addEvents();
    }

    private void initViews() {
        name = findViewById(R.id.nameFolder);
        path = findViewById(R.id.pathFolder);
        send = findViewById(R.id.createButton);
        clean = findViewById(R.id.limpButton);
    }

    private void addEvents() {
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String nameFile = name.getText().toString().trim();
                String pathFile =  path.getText().toString().trim();
                if(nameFile.isEmpty()) {
                    name.setError(getString(R.string.emptyError));
                    return;
                }
                if(pathFile.isEmpty()) {
                    path.setError(getString(R.string.emptyError));
                    return;
                }

                FileModel file = new FileModel(cont++, nameFile, R.drawable.ic_player_playslist_play, pathFile);
                Intent seeingFiles = new Intent(CreationFolder.this, HiddenFilesActivity.class);
                String fileString = new Gson().toJson(file);
                seeingFiles.putExtra(Constants.INTENT_FOLDER_CREATION, fileString);
                startActivity(seeingFiles);
            }
        });
        clean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name.setText("");
                path.setText("");
            }
        });
    }
}
