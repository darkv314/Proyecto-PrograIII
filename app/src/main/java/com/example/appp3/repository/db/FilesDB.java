package com.example.appp3.repository.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.appp3.model.FilesModel;
import com.example.appp3.repository.dao.FilesDao;

@Database(entities = {FilesModel.class}, version = 1)
public abstract class FilesDB extends RoomDatabase
{
    public abstract FilesDao filesDao();
    private static volatile FilesDB INSTANCE;

    static public FilesDB getDatabase(final Context context)
    {
        if(INSTANCE == null)
        {
            synchronized (FilesDB.class)
            {
                if(INSTANCE == null)
                {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            FilesDB.class, "files_database.db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
