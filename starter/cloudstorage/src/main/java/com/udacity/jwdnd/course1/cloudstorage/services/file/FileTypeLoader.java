package com.udacity.jwdnd.course1.cloudstorage.services.file;

import org.apache.tika.Tika;
import org.springframework.stereotype.Component;

@Component
public class FileTypeLoader {

    private final Tika tika;

    public FileTypeLoader(Tika tika) {
        this.tika = tika;
    }

    public String getFileMimeType(byte[] file) {
        return tika.detect(file);
    }
}
