package com.example.appp3;

public class AllSongsTask {
    private long id;
    private String song_name;
    private String song_artist;
//    private int song;

    public AllSongsTask(long id, String song_name, String song_artist){
        this.id=id;
        this.song_name=song_name;
        this.song_artist=song_artist;
//        this.song = song;
    }

    public long getId()
    {
        return id;
    }
    public void setId(long id)
    {
        this.id=id;
    }
    public String getSong_name(){

        return song_name;
    }
    public void setSong_name(String song_name)
    {
        this.song_name=song_name;
    }
    public String getSong_artist()
    {
        return song_artist;
    }
    public void setSong_artist(String song_artist)
    {
        this.song_artist=song_artist;
    }
    public String getLetter(){
        return Character.toString(song_name.charAt(0));
    }

//    public int getSong() {
//        return song;
//    }
//
//    public void setSong(int song) {
//        this.song = song;
//    }

}
