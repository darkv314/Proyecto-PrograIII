package com.example.appp3.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.appp3.model.FoldersModel;
import com.example.appp3.repository.db.FoldersDB;

import java.util.List;

public class FoldersRepository
{
    private FoldersDB db;

    public FoldersRepository(Application application)
    {
        db = FoldersDB.getDatabase(application);
    }

    public void insert(final FoldersModel foldersModel)
    {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                db.foldersDao().insert(foldersModel);
            }
        });
        thread.start();
    }

    public void updateEntry(final FoldersModel fileModel)
    {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                db.foldersDao().updateEntry(fileModel);
            }
        });
        thread.start();
    }

    public LiveData<List<FoldersModel>> getAll()
    {
        return db.foldersDao().getAll();
    }

}
