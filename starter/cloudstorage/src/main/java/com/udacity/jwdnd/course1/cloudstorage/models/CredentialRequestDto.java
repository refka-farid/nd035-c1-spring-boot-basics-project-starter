package com.udacity.jwdnd.course1.cloudstorage.models;

import com.udacity.jwdnd.course1.cloudstorage.entities.Credential;

import java.util.Objects;

public class CredentialRequestDto {
    Integer credentialId;
    String url;
    String username;
    String password;

    public CredentialRequestDto() {
    }

    public CredentialRequestDto(Integer credentialId, String url, String userName, String encryptedPassword) {
        this.credentialId = credentialId;
        this.url = url;
        this.username = userName;
        this.password = encryptedPassword;
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

    public static CredentialRequestDto fromCredential(Credential credential) {
        return new CredentialRequestDto(credential.getCredentialId(), credential.getUrl(), credential.getUserName(), credential.getPassword());
    }

    public Credential toCredential() {
        return new Credential(credentialId, url, username, "", password, null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CredentialRequestDto that = (CredentialRequestDto) o;
        return credentialId.equals(that.credentialId) && url.equals(that.url) && username.equals(that.username) && password.equals(that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(credentialId, url, username, password);
    }
}
