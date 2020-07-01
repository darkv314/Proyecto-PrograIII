package com.example.appp3;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.example.appp3.adapter.FilesAdapter;
import com.example.appp3.model.FilesModel;
import com.example.appp3.model.FoldersModel;
import com.example.appp3.repository.FilesRepository;
import com.example.appp3.utils.Constants;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class SeeingFilesActivity extends AppCompatActivity
{
    private List<FilesModel> items = new ArrayList<>();
    private Context context;
    private static final int FOOTER_ID = 100;
    private Button addButton;
    private FilesAdapter adapter;
    private ListView listView;
    private FilesRepository filesRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(render());
        filesRepository = new FilesRepository(getApplication());
        receiveValues();
        addEvents();
        filesRepository.getAll().observe(this, new Observer<List<FilesModel>>() {
            @Override
            public void onChanged(List<FilesModel> filesModels) {
                Log.e("onChanged.quarantine", "" + filesModels.size());
                items = filesModels;

                adapter.setItems(filesModels);
                adapter.notifyDataSetChanged();
            }
        });
    }

//    private void fillFilesV()
//    {
//        items.add(new FilesModel("File1.txt", "7MB", "17/06/2020", R.drawable.ic_collections_black_24dp, "/storage/extSdCard"));
//        items.add(new FilesModel("File2.txt", "8MB", "18/06/2020", R.drawable.ic_collections_black_24dp, "/storage/extSdCard"));
//        items.add(new FilesModel("File3.txt", "9MB", "19/06/2020", R.drawable.ic_collections_black_24dp, "/storage/extSdCard"));
//        items.add(new FilesModel("File4.txt", "10MB", "20/06/2020", R.drawable.ic_collections_black_24dp, "/storage/extSdCard"));
//
//    }

    public View render()
    {
        RelativeLayout parentRelativeLayout = new RelativeLayout(context);
        parentRelativeLayout.setBackgroundColor(getResources().getColor(android.R.color.white));
        listView = new ListView(context);
        RelativeLayout.LayoutParams listLayoutParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        listLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        listView.setLayoutParams(listLayoutParams);
        adapter = new FilesAdapter(context, items);
        listView.setAdapter(adapter);
        parentRelativeLayout.addView(listView);

        RelativeLayout.LayoutParams buttonLayoutParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        buttonLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        addButton = new Button(context);
        addButton.setId(FOOTER_ID);
        addButton.setText(R.string.add);
        addButton.setLayoutParams(buttonLayoutParams);
        addButton.setBackgroundResource(R.color.colorAccent);
        addButton.setTextColor(getResources().getColor(android.R.color.white));
        parentRelativeLayout.addView(addButton);


        return parentRelativeLayout;
    }

    private void addEvents()
    {
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent creation = new Intent(SeeingFilesActivity.this, CreationFile.class);
                startActivity(creation);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("This is the id:", String.valueOf(id));
                FilesModel filesModel = items.get(position);
                filesModel.setNumberOfClicks(filesModel.getNumberOfClicks()+1);
                filesRepository.updateEntry(filesModel);
            }
        });
    }
    private void receiveValues() {
        Intent intent = getIntent();
        if (intent.hasExtra(Constants.INTENT_FILE_CREATION)) {
            String fileObj = intent.getStringExtra(Constants.INTENT_FILE_CREATION);
            FilesModel file = new Gson().fromJson(fileObj, FilesModel.class);
            items.add(file);
            adapter.notifyDataSetChanged();
        }
    }
}