package com.example.appp3.model;

public class SongItem {
    private long id;
    private String name;
    private String album;
    private String letter;

    public SongItem(long id, String name, String album) {
        this.id = id;
        this.name = name;
        this.album = album;
        this.letter = Character.toString(name.charAt(0));

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

    public void setAlbum(String album){
        this.album = album;
    }

    public String getAlbum(){
        return album;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }
}

