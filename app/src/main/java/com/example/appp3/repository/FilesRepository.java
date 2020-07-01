package com.example.appp3.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.appp3.model.FilesModel;
import com.example.appp3.model.FoldersModel;
import com.example.appp3.repository.db.FilesDB;
import com.example.appp3.repository.db.FoldersDB;

import java.io.File;
import java.util.List;

public class FilesRepository
{
    private FilesDB db;

    public FilesRepository(Application application)
    {
        db = FilesDB.getDatabase(application);
    }

    public void insert(final FilesModel filesModel)
    {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                db.filesDao().insert(filesModel);
            }
        });
        thread.start();
    }

    public void updateEntry(final FilesModel fileModel)
    {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                db.filesDao().updateEntry(fileModel);
            }
        });
        thread.start();
    }

    public LiveData<List<FilesModel>> getAll()
    {
        return db.filesDao().getAll();
    }
}
