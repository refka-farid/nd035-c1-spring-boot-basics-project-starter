package com.udacity.jwdnd.course1.cloudstorage.util;

import java.io.File;
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

    public static boolean isFileDownloaded(String downloadPath, String fileName) {
        File dir = new File(downloadPath);
        File[] dirContents = dir.listFiles();

        for (int i = 0; i < dirContents.length; i++) {
            if (dirContents[i].getName().startsWith(fileName)) {
                // File has been found, it can now be deleted:
                dirContents[i].delete();
                return true;
            }
        }
        return false;
    }
}
