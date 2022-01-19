package com.revature.services;

import com.revature.app.Main;
import com.revature.models.User;
import com.revature.repositories.UserRepo;
import com.revature.util.LinkedList;

import java.util.Objects;
import java.util.Scanner;

public class UserServiceImp implements UserService {

    private UserRepo userRepo;

    public UserServiceImp(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public void addUser(User u) {
        userRepo.addUser(u);
    }

    @Override
    public User getUser(int id) {
        return userRepo.getUser(id);
    }

    @Override
    public LinkedList<User> getAllUsers() {
        return userRepo.getAllUsers();
    }

    public User findUser(String name) {
        return userRepo.findUser(name);
    }

    @Override
    public void updateUser(User change) {
        userRepo.updateUser(change);
    }

    @Override
    public void deleteUser(int id) {
        userRepo.deleteUser(id);
    }

    @Override
    public void logIn() {
        Scanner input = new Scanner(System.in);
        int option;

        System.out.println("==========" +
                "\nUSER LOGIN" +
                "\n==========");
        System.out.println("Enter your username:");
        String userLogin = input.nextLine();
        User user = findUser(userLogin);

        while (user == null) {
            System.out.println("ERROR: User not found.");
            option = Main.userNotFoundMenu();

            if (option == 1) {
                System.out.println("Enter your username:");
                userLogin = input.nextLine();
                user = findUser(userLogin);
            } else {
                break;
            }
        }

        if (user != null) {
            System.out.println("Enter your password:");
            String userPassword = input.nextLine();

            while (!Objects.equals(user.getUserPassword(), userPassword)) {
                System.out.println("ERROR: Invalid password. Please try again.");
                option = Main.passwordNotFoundMenu();

                if (option == 1) {
                    System.out.println("Enter your password:");
                    userPassword = input.nextLine();
                } else {
                    break;
                }
            }

            if (Objects.equals(user.getUserPassword(), userPassword)) {
                System.out.println("Login successful! Welcome.");
                Main.loggedInUserId = user.getUserId();
                Main.loggedIn = true;
            }
        }
        input.close();
    }

    @Override
    public void register() {
        Scanner input = new Scanner(System.in);

        System.out.println("========" +
                "\nNEW USER" +
                "\n========");
        System.out.println("Enter a username:");
        String newLogin = input.nextLine();

        //Populate a list of users from the db to perform unique username checking.
        LinkedList<User> userList = getAllUsers();
        boolean userExists = userList.checkIfExists(newLogin);

        while (userExists) {
            System.out.println("ERROR: Username unavailable. Please choose a different one.");
            newLogin = input.nextLine();
            userExists = userList.checkIfExists(newLogin);
        }

        System.out.println("Thank you. Now, create a password:");
        String newPassword = input.nextLine();
        System.out.println("Password confirmation. Type your password again:");
        String newPasswordConfirm = input.nextLine();

        while (!Objects.equals(newPasswordConfirm, newPassword)) {
            System.out.println("ERROR: Passwords do not match. Try again.");
            newPasswordConfirm = input.nextLine();
        }

        User user = new User(newLogin, newPassword);
        addUser(user);
        System.out.println("Account successfully created!");

        input.close();
    }
}
