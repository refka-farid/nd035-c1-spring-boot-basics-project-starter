package com.udacity.jwdnd.course1.cloudstorage.models;

import java.util.Objects;

public class UploadFileResponseDto {

    private boolean isFileNameExist = false;

    public UploadFileResponseDto(boolean isFileNameExist) {
        this.isFileNameExist = isFileNameExist;
    }

    public boolean isFileNameExist() {
        return isFileNameExist;
    }

    public void setFileNameExist(boolean fileNameExist) {
        isFileNameExist = fileNameExist;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UploadFileResponseDto that = (UploadFileResponseDto) o;
        return isFileNameExist == that.isFileNameExist;
    }

    @Override
    public int hashCode() {
        return Objects.hash(isFileNameExist);
    }

    @Override
    public String toString() {
        return "UploadFileResponseDto{" +
                "isFileNameExist=" + isFileNameExist +
                '}';
    }
}
