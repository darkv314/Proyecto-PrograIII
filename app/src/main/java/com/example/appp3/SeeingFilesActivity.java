package com.example.appp3;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appp3.adapter.FilesAdapter;
import com.example.appp3.model.FilesV;

import java.util.ArrayList;
import java.util.List;

public class SeeingFilesActivity extends AppCompatActivity
{
    private List<FilesV> items = new ArrayList<>();
    private Context context;
    private static final int FOOTER_ID = 100;
    private Button addButton;
    private FilesAdapter adapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(render());
        //initViews();
        fillFilesV();
        fillFilesV();
        fillFilesV();
        fillFilesV();
        fillFilesV();
        addEvents();
    }

    private void fillFilesV()
    {
        items.add(new FilesV(items.size(), "File1.txt", "7MB", "17/06/2020", R.drawable.ic_collections_black_24dp));
        items.add(new FilesV(items.size(), "File2.txt", "8MB", "18/06/2020", R.drawable.ic_collections_black_24dp));
        items.add(new FilesV(items.size(), "File3.txt", "9MB", "19/06/2020", R.drawable.ic_collections_black_24dp));
        items.add(new FilesV(items.size(), "File4.txt", "10MB", "20/06/2020", R.drawable.ic_collections_black_24dp));

    }

    public View render()
    {
        RelativeLayout parentRelativeLayout = new RelativeLayout(context);
        parentRelativeLayout.setBackgroundColor(getResources().getColor(android.R.color.white));
        listView = new ListView(context);
        RelativeLayout.LayoutParams listLayoutParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        listLayoutParams.addRule(RelativeLayout.ABOVE,FOOTER_ID);
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
        addButton.setText("Add");
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
                items.add(new FilesV(items.size(), "File " + items.size(),
                        "20MB", "23/06/2020", R.drawable.ic_collections_black_24dp));
                adapter.notifyDataSetChanged();
                listView.smoothScrollToPosition(items.size());
            }
        });
    }
}