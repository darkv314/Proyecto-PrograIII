package com.example.appp3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.appp3.model.FilesV;
import com.example.appp3.utils.Constants;
import com.google.gson.Gson;

public class CreationFile extends AppCompatActivity {
    private EditText name;
    private EditText path;
    private Button send;
    private Button clean;
    long cont = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation_file);
        initViews();
        addEvents();
    }

    private void initViews() {
        name = findViewById(R.id.putName);
        path = findViewById(R.id.pathFile);
        send = findViewById(R.id.sendButton);
        clean = findViewById(R.id.cleanButton);
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

                FilesV file = new FilesV(cont++, nameFile, "20MB", "30/06/2020", R.drawable.ic_collections_black_24dp, pathFile);
                Intent seeingFiles = new Intent(CreationFile.this, SeeingFilesActivity.class);
                String fileString = new Gson().toJson(file);
                seeingFiles.putExtra(Constants.INTENT_FILE_CREATION, fileString);
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
