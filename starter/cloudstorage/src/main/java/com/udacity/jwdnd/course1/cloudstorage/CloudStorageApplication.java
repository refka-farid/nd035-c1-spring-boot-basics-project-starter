package com.udacity.jwdnd.course1.cloudstorage;

import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.web.multipart.MultipartException;

@SpringBootApplication
public class CloudStorageApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudStorageApplication.class, args);
    }

//    @Bean
//    public WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> webServerFactoryCustomizer() {
////        return (factory) -> factory.addErrorPages(new ErrorPage(MultipartException.class, "/uploadError"));
//        return (factory) -> factory.addErrorPages(new ErrorPage(SizeLimitExceededException.class, "/uploadError"));
//    }

//    @Bean
//    public ConfigurableServletWebServerFactory webServerFactory() {
//        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
//        factory.addErrorPages(new ErrorPage(HttpStatus.UNAUTHORIZED, "/unauthenticated"));
//        return factory;
//    }
}
