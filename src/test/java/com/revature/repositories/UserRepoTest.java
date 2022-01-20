package com.revature.repositories;

import com.revature.util.LinkedList;
import com.revature.models.User;

public interface UserRepoTest {
    public void addUser(User u);
    public User getUser(int id);
    public User findUser(String name);
    public LinkedList<User> getAllUsers();
    public void updateUser(User change);
    public void deleteUser(int id);
}
