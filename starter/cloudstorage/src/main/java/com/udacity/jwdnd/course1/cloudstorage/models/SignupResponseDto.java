package com.udacity.jwdnd.course1.cloudstorage.models;

import java.util.Objects;

public class SignupResponseDto {
    private boolean isSuccessSignup;
    private String errorSignup;

    public SignupResponseDto() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SignupResponseDto that = (SignupResponseDto) o;
        boolean sameSuccessSignup = isSuccessSignup == that.isSuccessSignup;
        boolean sameErrorSignup = Objects.equals(errorSignup, that.errorSignup);
        return sameSuccessSignup && sameErrorSignup;
    }

    @Override
    public String toString() {
        return "SignupResponseModel{" +
                "isSuccessSignup=" + isSuccessSignup +
                ", errorSignup='" + errorSignup + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(isSuccessSignup, errorSignup);
    }
}
