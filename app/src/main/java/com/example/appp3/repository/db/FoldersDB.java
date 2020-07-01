package com.example.appp3.repository.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.appp3.model.FoldersModel;
import com.example.appp3.repository.dao.FoldersDao;

@Database(entities = {FoldersModel.class}, version = 1)
public abstract class FoldersDB extends RoomDatabase
{
    public abstract FoldersDao foldersDao();
    private static volatile FoldersDB INSTANCE;

    static public FoldersDB getDatabase(final Context context)
    {
        if(INSTANCE == null)
        {
            synchronized (FoldersDB.class)
            {
                if(INSTANCE == null)
                {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            FoldersDB.class, "folders_database.db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
