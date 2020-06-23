package com.example.appp3.repository;

import com.example.appp3.model.User;

import java.util.ArrayList;
import java.util.List;


public class UserRepository {
    private static UserRepository instance;
    private List<User> users = new ArrayList<>();

    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }

    private UserRepository() {
    }

    public boolean login(int numero) {
        for (User user : users) {
            if (Integer.parseInt(user.getNumber_of_times()) == numero) {
                return true;
            }
        }
        return false;
    }

    public void register(User user) {
        users.add(user);
    }

}
