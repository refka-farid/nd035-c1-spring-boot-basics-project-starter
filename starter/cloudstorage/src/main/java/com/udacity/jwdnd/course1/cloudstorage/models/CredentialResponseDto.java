package com.udacity.jwdnd.course1.cloudstorage.models;

import com.udacity.jwdnd.course1.cloudstorage.entities.Credential;

import java.util.Objects;

public class CredentialResponseDto {
    Integer credentialId;
    String url;
    String userName;
    String encryptedPassword;
    String unencryptedPassword;
    Integer userId;

    public CredentialResponseDto() {
    }

    public CredentialResponseDto(Integer credentialId, String url, String userName, String encryptedPassword, String unencryptedPassword, Integer userId) {
        this.credentialId = credentialId;
        this.url = url;
        this.userName = userName;
        this.encryptedPassword = encryptedPassword;
        this.unencryptedPassword = unencryptedPassword;
        this.userId = userId;
    }

    public String getUnencryptedPassword() {
        return unencryptedPassword;
    }

    public void setUnencryptedPassword(String unencryptedPassword) {
        this.unencryptedPassword = unencryptedPassword;
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

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public Integer getCredentialId() {
        return credentialId;
    }

    public void setCredentialId(Integer credentialId) {
        this.credentialId = credentialId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public static CredentialResponseDto fromCredential(Credential credential, String encryptedPassword, String unencryptedPassword) {
        return new CredentialResponseDto(credential.getCredentialId(), credential.getUrl(), credential.getUserName(), encryptedPassword, unencryptedPassword, credential.getUserId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CredentialResponseDto that = (CredentialResponseDto) o;
        return url.equals(that.url) && userName.equals(that.userName) && encryptedPassword.equals(that.encryptedPassword) && userId.equals(that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(credentialId, url, userName, encryptedPassword, unencryptedPassword, userId);
    }

    @Override
    public String toString() {
        return "CredentialResponseDto{" +
                "credentialId=" + credentialId +
                ", url='" + url + '\'' +
                ", userName='" + userName + '\'' +
                ", encryptedPassword='" + encryptedPassword + '\'' +
                ", unencryptedPassword='" + unencryptedPassword + '\'' +
                ", userId=" + userId +
                '}';
    }
}
