package com.revature.services;

import com.revature.models.User;
import com.revature.util.LinkedList;

public interface UserService {

    //Trivial methods.
    public void addUser(User u);
    public User getUser(int id);
    public LinkedList<User> getAllUsers();
    public User findUser(String name);
    public void updateUser(User change);
    public void deleteUser(int id);

    //Complex methods.

}
