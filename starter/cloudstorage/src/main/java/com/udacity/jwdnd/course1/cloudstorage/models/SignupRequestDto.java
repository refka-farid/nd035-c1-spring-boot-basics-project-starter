package com.udacity.jwdnd.course1.cloudstorage.models;

import com.udacity.jwdnd.course1.cloudstorage.entities.User;

public class SignupRequestDto {
    private String userName;
    private String password;
    private String firstName;
    private String lastName;

    public SignupRequestDto(String userName, String password, String firstName, String lastName) {
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = removeWhiteSpaces(userName);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public User toUser() {
        return User.from(this.userName, this.password, this.firstName, this.lastName);
    }

    private String removeWhiteSpaces(String word) {
        return word.replaceAll("\\s", "");
    }
}
