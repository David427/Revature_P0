package com.revature.repositories;

import com.revature.models.User;
import com.revature.util.LinkedList;

public interface UserRepo {
    public User addUser(User u);
    public User getUser(int id);
    public LinkedList<User> getAllUsers();
    public User updateUser(User change);
    public User deleteUser(int id);
}
