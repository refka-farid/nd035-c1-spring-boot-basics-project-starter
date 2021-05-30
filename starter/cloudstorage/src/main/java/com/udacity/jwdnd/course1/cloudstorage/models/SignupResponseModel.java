package com.udacity.jwdnd.course1.cloudstorage.models;

public class SignupResponseModel {
    private boolean isSuccessSignup;
    private String errorSignup;

    public SignupResponseModel() {
    }

    public boolean isSuccessSignup() {
        return isSuccessSignup;
    }

    public void setSuccessSignup(boolean successSignup) {
        isSuccessSignup = successSignup;
    }

    public String getErrorSignup() {
        return errorSignup;
    }

    public void setErrorSignup(String errorSignup) {
        this.errorSignup = errorSignup;
    }
}
