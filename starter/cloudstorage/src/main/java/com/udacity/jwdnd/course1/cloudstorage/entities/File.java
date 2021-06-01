package com.udacity.jwdnd.course1.cloudstorage.entities;

import java.util.Arrays;
import java.util.Objects;

public class File {

    private Integer fileId;
    private String fileName;
    private String contentType;
    private String fileSize;
    private byte[] fileData;
    private Integer userId;

    public File(Integer fileId, String fileName, String contentType, String fileSize, byte[] fileData, Integer userId) {
        this.fileId = fileId;
        this.fileName = fileName;
        this.contentType = contentType;
        this.fileSize = fileSize;
        this.fileData = fileData;
        this.userId = userId;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public byte[] getFileData() {
        return fileData;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        File file = (File) o;
        return Objects.equals(fileId, file.fileId) && Objects.equals(fileName, file.fileName) && Objects.equals(contentType, file.contentType) && Objects.equals(fileSize, file.fileSize) && Arrays.equals(fileData, file.fileData) && Objects.equals(userId, file.userId);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(fileId, fileName, contentType, fileSize, userId);
        result = 31 * result + Arrays.hashCode(fileData);
        return result;
    }

    public static File from(String fileName, String contentType, String fileSize, byte[] fileData) {
        return new File(null, fileName, contentType, fileSize, fileData, null);
    }
}
