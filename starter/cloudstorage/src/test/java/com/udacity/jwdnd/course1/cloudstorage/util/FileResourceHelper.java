package com.udacity.jwdnd.course1.cloudstorage.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class FileResourceHelper {
    private FileResourceHelper() {
    }

    public static byte[] getFileInByteArray(String fileName) throws IOException {
        URL filePath = FileResourceHelper.class.getResource(fileName);
        String pathname = Objects.requireNonNull(filePath).getFile();
        java.io.File file = new java.io.File(pathname);
        FileInputStream fileInputStream = new FileInputStream(file);
        return fileInputStream.readAllBytes();
    }
}
