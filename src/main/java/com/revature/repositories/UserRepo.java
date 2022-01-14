package com.revature.repositories;

import com.revature.models.User;
import com.revature.util.LinkedList;

public interface UserRepo {
    public void addUser(User u);
    public User getUser(int id);
    public LinkedList<User> getAllUsers();
    public void updateUser(User change);
    public void deleteUser(int id);
}
