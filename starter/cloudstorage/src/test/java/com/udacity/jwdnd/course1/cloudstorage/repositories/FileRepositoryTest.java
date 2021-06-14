package com.udacity.jwdnd.course1.cloudstorage.repositories;

import com.udacity.jwdnd.course1.cloudstorage.entities.File;
import com.udacity.jwdnd.course1.cloudstorage.entities.User;
import com.udacity.jwdnd.course1.cloudstorage.mappers.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mappers.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mappers.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.mappers.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.util.FileResourceHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.inject.Inject;
import java.io.IOException;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles(profiles = {"dev"})
class FileRepositoryTest {

    @Inject
    private NoteMapper mapperNote;

    @Inject
    private UserMapper mapperUser;

    @Inject
    private FileMapper mapperFile;

    @Inject
    private CredentialMapper mapperCredential;

    private FileRepository sut;

    private File myTestedFile;

    @BeforeEach
    void setUp() throws IOException {
        sut = new FileRepository(mapperFile);
        byte[] fileData = FileResourceHelper.getFileInByteArray("/myfile.txt");
        var fileSize = fileData.length + "";
        myTestedFile = File.from("myFile", "String", fileSize, fileData);
    }

    @AfterEach
    void tearDown() {
        mapperFile.deleteAll();
        mapperCredential.deleteAll();
        mapperNote.deleteAll();
        mapperUser.deleteAll();
    }

    @Test
    void addAndGetTest() {
        var result = sut.add(myTestedFile);
        assertThat(result).isTrue();

        var result2 = sut.getByFileName("myFile");
        assertThat(result2).isEqualTo(myTestedFile);
    }

    @Test
    void getAllByUserIdTest() {
        var user = new User(null, "kamel", "HIxi7PbCRU9uIyET6sdGEg==", "8H7jlDi3a2iPiu9ZI1+krA==", "kamel", "kamel");
        mapperUser.addUser(user);
        var storedUser = mapperUser.getUserByUserName("kamel");

        var file = new File();
        file.setUserId(storedUser.getUserId());
        file.setFileId(20);
        file.setFileName("myFile2");
        file.setContentType("text");
        file.setFileData(myTestedFile.getFileData());

        sut.add(file);
        var files = new ArrayList<File>();
        files.add(file);

        var result3 = sut.getAllByUserId(storedUser.getUserId());
        assertThat(result3).isEqualTo(files);
    }

    @Test
    void deleteAllTest() {
        sut.add(myTestedFile);
        var result = sut.deleteAll();
        assertThat(result).isTrue();
    }

    @Test
    void getAllTest() {
        sut.add(myTestedFile);
        var result = sut.deleteAll();
        assertThat(result).isTrue();
    }

    @Test
    void deleteByUserIdAndFileNameTest() {
        var user = new User(null, "kamel", "HIxi7PbCRU9uIyET6sdGEg==", "8H7jlDi3a2iPiu9ZI1+krA==", "kamel", "kamel");
        mapperUser.addUser(user);
        var storedUser = mapperUser.getUserByUserName("kamel");

        var file = new File();
        file.setUserId(storedUser.getUserId());
        file.setFileId(20);
        file.setFileName("myFile2");
        file.setContentType("text");
        file.setFileData(myTestedFile.getFileData());

        sut.add(file);
        var files = new ArrayList<File>();
        files.add(file);
        var result = sut.deleteFileByFileNameAndUserId(storedUser.getUserId(), "myFile2");
        assertThat(result).isTrue();
    }
}
