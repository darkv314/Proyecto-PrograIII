package com.example.appp3.model;

public class FileModel {
    private long id;
    private String name;
    private boolean finished;
    private int image;
    private  String path;

    public FileModel(long id, String name, int image, String path) {
        this.id = id;
        this.name = name;
        this.finished = false;
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}