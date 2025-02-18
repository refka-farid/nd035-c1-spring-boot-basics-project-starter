package com.udacity.jwdnd.course1.cloudstorage.entities;

import java.util.Objects;

public class Credential {

    Integer credentialId;
    String url;
    String userName;
    String key;
    String password;
    Integer userId;

    public Credential() {
    }

    public Credential(Integer credentialId, String url, String userName, String key, String password, Integer userId) {
        this.credentialId = credentialId;
        this.url = url;
        this.userName = userName;
        this.key = key;
        this.password = password;
        this.userId = userId;
    }

    public Integer getCredentialId() {
        return credentialId;
    }

    public void setCredentialId(Integer credentialId) {
        this.credentialId = credentialId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Credential that = (Credential) o;
        return Objects.equals(credentialId, that.credentialId) && url.equals(that.url) && userName.equals(that.userName) && key.equals(that.key) && password.equals(that.password) && userId.equals(that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(credentialId, url, userName, key, password, userId);
    }
}
