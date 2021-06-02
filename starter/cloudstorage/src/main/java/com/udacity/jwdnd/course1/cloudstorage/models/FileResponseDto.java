package com.udacity.jwdnd.course1.cloudstorage.models;

import com.udacity.jwdnd.course1.cloudstorage.entities.File;

import java.util.Objects;

public class FileResponseDto {

    private Integer fileId;
    private String fileName;

    public FileResponseDto(Integer fileId, String fileName) {
        this.fileId = fileId;
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    @Override
    public String toString() {
        return "FileResponseDto{" +
                "fileId=" + fileId +
                ", fileName='" + fileName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileResponseDto that = (FileResponseDto) o;
        return fileId.equals(that.fileId) && fileName.equals(that.fileName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fileId, fileName);
    }

    public static FileResponseDto fromFile(File file) {
        return new FileResponseDto(file.getFileId(), file.getFileName());
    }
}
