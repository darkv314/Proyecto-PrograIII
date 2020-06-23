package com.example.appp3.model;

public class FileModel {
    private long id;
    private int name;
    private boolean finished;
    private int image;

    public FileModel(long id, int name, int image) {
        this.id = id;
        this.name = name;
        this.finished = false;
        this.image = image;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

}

