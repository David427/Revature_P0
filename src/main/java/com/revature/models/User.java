package com.revature.models;

public class User {

    private String username;
    private String password;
    private boolean hasChecking;
    private boolean hasSavings;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username, String password, boolean hasChecking, boolean hasSavings) {
        this.username = username;
        this.password = password;
        this.hasChecking = hasChecking;
        this.hasSavings = hasSavings;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isHasChecking() {
        return hasChecking;
    }

    public void setHasChecking(boolean hasChecking) {
        this.hasChecking = hasChecking;
    }

    public boolean isHasSavings() {
        return hasSavings;
    }

    public void setHasSavings(boolean hasSavings) {
        this.hasSavings = hasSavings;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", hasChecking=" + hasChecking +
                ", hasSavings=" + hasSavings +
                '}';
    }
}