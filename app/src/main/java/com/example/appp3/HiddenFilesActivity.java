package com.example.appp3;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import com.example.appp3.adapter.FilesAdapter;
import com.example.appp3.adapter.HiddenFoldersAdapter;
import com.example.appp3.adapter.ListFilesAdapter;
import com.example.appp3.model.FileModel;
import com.example.appp3.model.FilesV;
import com.example.appp3.SeeingFilesActivity;
import com.example.appp3.utils.Constants;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HiddenFilesActivity extends AppCompatActivity {

    private Context context;
    private List<FileModel> items = new ArrayList<>();
    private List<FilesV> fileItems = new ArrayList<>();
    private ImageView settingView;
    private ListView fileListView;
    private ListFilesAdapter adapter;
    private String rootPath;
    private File dir;
    private HiddenFoldersAdapter hiddenFoldersAdapter;
    private List<String> filesList;
    private File[] files;
    private int filesFoundCount;
    private Button addButton;
    private Button deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;

        setContentView(R.layout.activity_hidden_files);
        //getSupportActionBar().hide();
        initViews();
        addEvents();
    }
    private void initViews() {
        settingView = findViewById(R.id.settingsImageView);
        fileListView = findViewById(R.id.fileList);
        hiddenFoldersAdapter = new HiddenFoldersAdapter();
        fileListView.setAdapter(hiddenFoldersAdapter);
        addButton = findViewById(R.id.addbutton);
        deleteButton = findViewById(R.id.deleteButton);
    }

    private void addEvents() {
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder deleteDialog = new AlertDialog.Builder(HiddenFilesActivity.this);
                deleteDialog.setTitle("Delete");
                deleteDialog.setMessage("Are you sure you want to delete this files?");
                deleteDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for(int i = 0; i<files.length; i++)
                        {
                            if(selection[i])
                            {
                                deleteFileOrFolder(files[i]);
                                selection[i]=false;
                            }
                        }
                        files = dir.listFiles();
                        filesList.clear();
                        filesFoundCount = files.length;
                        for (int i = 0; i < filesFoundCount; i++) {
                            filesList.add(String.valueOf(files[i].getAbsolutePath()));
                        }
                        hiddenFoldersAdapter.setData(filesList);
                    }
                });
                deleteDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                deleteDialog.show();
            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                items.add(new FileModel(items.size(), R.string.newFile,R.drawable.ic_player_playslist_play));
                adapter.notifyDataSetChanged();
            }
        });
        /*fileListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent fistrMenu = new Intent(HiddenFilesActivity.this, SeeingFilesActivity.class);
                startActivity(fistrMenu);
            }
        });*/
        settingView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent change = new Intent(HiddenFilesActivity.this, Settings2Activity.class);
                startActivity(change);
            }
        });
    }

    private static final int REQUEST_PERMISSIONS = 1234;

    private static final String[] PERMISSIONS = {
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private void deleteFileOrFolder(File fileOrFolder)
    {
        if(fileOrFolder.isDirectory())
        {
            if(fileOrFolder.list().length==0)
            {
                fileOrFolder.delete();
            }
            else
            {
                String files[] = fileOrFolder.list();
                for(String temp:files)
                {
                    File fileToDelete = new File(fileOrFolder, temp);
                    deleteFileOrFolder(fileToDelete);
                }
                if(fileOrFolder.list().length==0)
                {
                    fileOrFolder.delete();
                }
            }
        }
        else
        {
            fileOrFolder.delete();
        }
    }

    private static final int PERMISSIONS_COUNT = 2;

    @SuppressLint("NewApi")
    private boolean arePermissionsDenied() {
        int p = 0;
        while (p < PERMISSIONS_COUNT) {
            if (checkSelfPermission(PERMISSIONS[p]) != PackageManager.PERMISSION_GRANTED) {
                return true;
            }
            p++;
        }
        return false;
    }

    private boolean isFileManagerInitialized = false;
    private boolean[] selection;

    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && arePermissionsDenied())
        {
            requestPermissions(PERMISSIONS, REQUEST_PERMISSIONS);
            return;
        }
        if(!isFileManagerInitialized)
        {
            rootPath = String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS));
            dir = new File(rootPath);
            files = dir.listFiles();
            //pathOutput.setText(rootPath.substring(rootPath.lastIndexOf('/')+1));
            filesFoundCount = files.length;

            filesList = new ArrayList<>();
            for (int i = 0; i < filesFoundCount; i++) {
                filesList.add(String.valueOf(files[i].getAbsolutePath()));
            }
            hiddenFoldersAdapter.setData(filesList);

            selection = new boolean[files.length];
            fileListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    selection[position] = !selection[position];
                    hiddenFoldersAdapter.setSelection(selection);
                    boolean isAtLeastOneSelected=false;
                    for (boolean b : selection) {
                        if (b) {
                            isAtLeastOneSelected = true;
                            break;
                        }
                    }
                    if (isAtLeastOneSelected)
                    {
                        findViewById(R.id.bottomBar).setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        findViewById(R.id.bottomBar).setVisibility(View.GONE);
                    }
                    return false;
                }
            });

            isFileManagerInitialized = true;
        }
    }

    @SuppressLint("NewApi")
    @Override
    public void onRequestPermissionsResult(final int requestCode, final String[] permissions, final int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSIONS && grantResults.length > 0) {
            if (arePermissionsDenied()) {
                ((ActivityManager) Objects.requireNonNull(this.getSystemService(ACTIVITY_SERVICE))).clearApplicationUserData();
                recreate();
            }
            else
            {
                onResume();
            }
        }
    }

}

