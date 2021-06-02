package com.udacity.jwdnd.course1.cloudstorage.services.file;

import org.apache.tika.Tika;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FileLoaderConfiguration {

    @Bean
    public Tika tika() {
        return new Tika();
    }
}
