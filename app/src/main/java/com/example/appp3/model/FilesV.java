package com.example.appp3.model;

public class FilesV {
    private long id;
    private String name;
    private String size;
    private String creation;
    private int image;

    public FilesV(long id, String name, String size, String creation, int image) {
        this.id = id;
        this.name = name;
        this.size = size;
        this.creation = creation;
        this.image = image;
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
}