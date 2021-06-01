package com.udacity.jwdnd.course1.cloudstorage.util;

import java.net.URL;

public class FileResourceHelper {
    public URL getFilePath(String fileName) {
        return this.getClass().getResource(fileName);
    }
}
