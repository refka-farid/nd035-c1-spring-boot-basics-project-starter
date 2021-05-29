package com.udacity.jwdnd.course1.cloudstorage.models;

public class User {
    private Integer userId;
    private String userName;
    private String salt;
    private String hashedPassword;
    private String firstName;
    private String lastName;

    public User(Integer userId, String userName, String salt, String hashedPassword, String firstName, String lastName) {
        this.userId = userId;
        this.userName = userName;
        this.salt = salt;
        this.hashedPassword = hashedPassword;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public static User from(String userName, String hashedPassword, String firstName, String lastName) {
        return new User(null, userName, null, hashedPassword, firstName, lastName);
    }
}
