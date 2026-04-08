package com.EasyShiftScheduler.CalEnder.Entities;

public abstract class User {
    private String username;
    private String email;
    private String password;
    private UserOperations userOperations;

    public User(String username, String email, String password, UserOperations userOperations) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.userOperations = userOperations;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserOperations getUserOperations() {
        return userOperations;
    }

    public void setUserOperations(UserOperations userOperations) {
        this.userOperations = userOperations;
    }
}
