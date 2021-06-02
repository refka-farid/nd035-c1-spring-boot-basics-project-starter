package com.udacity.jwdnd.course1.cloudstorage.util;

import com.udacity.jwdnd.course1.cloudstorage.entities.File;
import com.udacity.jwdnd.course1.cloudstorage.mappers.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mappers.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.repositories.FileRepository;
import com.udacity.jwdnd.course1.cloudstorage.services.file.FileTypeLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.test.context.ActiveProfiles;

import javax.inject.Inject;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
//this is not a test class  it's just a workaround to create some fake data in order to build the controllers
@SpringBootTest
@ActiveProfiles(profiles = {"dev"})
class DataBaseFeeder {

    @Inject
    private FileMapper mapperFile;

    @Inject
    private FileTypeLoader fileTypeLoader;

    @Inject
    private UserMapper mapperUser;

    private FileRepository sut;

    private File myTestedFile;

    @BeforeEach
    void setUp() throws IOException {
        sut = new FileRepository(mapperFile);
        byte[] fileData = FileResourceHelper.getFileInByteArray("/myfile.txt");
        var fileSize = fileData.length + "";
        myTestedFile = File.from("myFile", "String", fileSize, fileData);
    }


    @Disabled
    @Test
    void addTest2() {
        var storedUser = mapperUser.getUserByUserName("z");
        var file = new File();
        file.setUserId(142);
        file.setFileName("my_File_UzerZ_1.txt");
        file.setContentType(fileTypeLoader.getFileMimeType(myTestedFile.getFileData()));
        file.setFileData(myTestedFile.getFileData());
        sut.add(file);

        var file2 = new File();
        file2.setUserId(142);
        file2.setFileName("my_File_UzerZ_2.txt");
        file2.setContentType(fileTypeLoader.getFileMimeType(myTestedFile.getFileData()));
        file2.setFileData(myTestedFile.getFileData());
        sut.add(file2);

        var storedUser2 = mapperUser.getUserByUserName("kamel");
        var file3 = new File();
        file3.setUserId(storedUser2.getUserId());
        file3.setFileName("my_File_UzerZ_2.txt");
        file3.setContentType(fileTypeLoader.getFileMimeType(myTestedFile.getFileData()));
        file3.setFileData(myTestedFile.getFileData());
        sut.add(file3);

        var file4 = new File();
        file4.setUserId(storedUser2.getUserId());
        file4.setFileName("my_File_UzerZ_4.txt");
        file4.setContentType(fileTypeLoader.getFileMimeType(myTestedFile.getFileData()));
        file4.setFileData(myTestedFile.getFileData());
        sut.add(file4);

    }

    @Disabled
    @Test
    void deleteAllTest() {
        var result = sut.deleteAll();
        assertThat(result).isTrue();
    }
}
