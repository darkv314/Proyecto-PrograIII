package com.example.appp3.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "files")
public class FilesModel {
    @PrimaryKey (autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "size")
    private String size;
    @ColumnInfo(name = "creation_date")
    private String creation;
    private int image;
    @ColumnInfo(name = "path")
    private String path;

    public FilesModel(String name, String size, String creation, int image, String path) {
        this.name = name;
        this.size = size;
        this.creation = creation;
        this.image = image;
        this.path = path;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreation() {
        return creation;
    }

    public void setCreation(String duration) {
        this.creation = creation;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String details) {
        this.creation = size;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}