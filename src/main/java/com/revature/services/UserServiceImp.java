package com.revature.services;

import com.revature.models.User;
import com.revature.repositories.UserRepo;
import com.revature.util.LinkedList;

public class UserServiceImp implements UserService {

    private UserRepo userRepo;

    public UserServiceImp(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public void addUser(User u) {
    }

    @Override
    public User getUser(int id) {
        return userRepo.getUser(id);
    }

    @Override
    public LinkedList<User> getAllUsers() {
        return userRepo.getAllUsers();
    }

    @Override
    public void updateUser(User change) {

    }

    @Override
    public void deleteUser(int id) {

    }
}
