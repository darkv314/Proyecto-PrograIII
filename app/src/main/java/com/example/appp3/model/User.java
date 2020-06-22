package com.example.appp3.model;

public class User
{
    private String id;
    private String number_of_times;
    private String password;

    public User(String id, String number_of_times, String password)
    {
        this.id = id;
        this.number_of_times = number_of_times;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumber_of_times() {
        return number_of_times;
    }

    public void setNumber_of_times(String number_of_times) {
        this.number_of_times = number_of_times;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}