package com.udacity.jwdnd.course1.cloudstorage.repositories;

import com.udacity.jwdnd.course1.cloudstorage.entities.File;
import com.udacity.jwdnd.course1.cloudstorage.mappers.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.util.FileResourceHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.inject.Inject;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles(profiles = {"dev"})
class FileRepositoryTest {

    @Inject
    private FileMapper mapper;

    private FileRepository sut;

    @BeforeEach
    void setUp() {
        sut = new FileRepository(mapper);
    }

    @AfterEach
    void tearDown() {
        mapper.deleteAll();
    }

    @Test
    void addTest() throws IOException {
        FileResourceHelper fileResourceHelper = new FileResourceHelper();
        URL filePath = fileResourceHelper.getFilePath("/myfile.txt");
        java.io.File file = new java.io.File(filePath.getFile());
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] fileData = fileInputStream.readAllBytes();
        var fileSize = file.length() + "";
        File myTestedFile = File.from("myFile", "String", fileSize, fileData);
        var result = sut.add(myTestedFile);
        assertThat(result).isTrue();

        var result2 = sut.getOne("myFile");
        assertThat(result2).isEqualTo(myTestedFile);
    }


    @Test
    void delete1Test() {
        sut.delete1(File.from("", "", "", null));
    }

    @Test
    void delete2Test() {
        sut.delete2(File.from("", "", "", null));
    }

    @Test
    void deleteAllTest() {
        sut.deleteAll();
    }
}