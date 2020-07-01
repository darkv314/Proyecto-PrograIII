package com.example.appp3.repository.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.appp3.model.FilesModel;

import java.util.List;

@Dao
public interface FilesDao
{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(FilesModel file);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    void updateEntry(FilesModel file);

    @Query("SELECT * FROM files ORDER BY id ASC")
    LiveData<List<FilesModel>> getAll();

    @Delete
    void deleteFile(FilesModel file);
}
