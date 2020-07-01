package com.example.appp3.repository.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.appp3.model.FoldersModel;

import java.util.List;

@Dao
public interface FoldersDao
{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(FoldersModel file);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    void updateEntry(FoldersModel file);

    @Query("SELECT * FROM folders ORDER BY id ASC")
    LiveData<List<FoldersModel>> getAll();
}
