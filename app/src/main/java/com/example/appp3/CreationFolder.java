package com.example.appp3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.appp3.model.FilesModel;
import com.example.appp3.model.FoldersModel;
import com.example.appp3.repository.FoldersRepository;
import com.example.appp3.utils.Constants;
import com.google.gson.Gson;

public class CreationFolder extends AppCompatActivity {

    private EditText name;
    private EditText path;
    private Button send;
    private Button clean;
    private FoldersRepository foldersRepository;
    long cont = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        foldersRepository = new FoldersRepository(getApplication());
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

                FoldersModel folder = new FoldersModel(nameFile, R.drawable.ic_collections_black_24dp, pathFile);
                Intent seeingFiles = new Intent(CreationFolder.this, HiddenFilesActivity.class);
                foldersRepository.insert(folder);
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
