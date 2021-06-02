package com.udacity.jwdnd.course1.cloudstorage.services.file;

import com.udacity.jwdnd.course1.cloudstorage.entities.File;
import com.udacity.jwdnd.course1.cloudstorage.entities.User;
import com.udacity.jwdnd.course1.cloudstorage.repositories.FileRepository;
import com.udacity.jwdnd.course1.cloudstorage.services.signup.UserService;
import com.udacity.jwdnd.course1.cloudstorage.util.FileResourceHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

class FileServiceTest {

    private FileService sut;
    private FileRepository fileRepositoryMock;
    private UserService userServiceMock;

    @BeforeEach
    void setUp() {
        fileRepositoryMock = mock(FileRepository.class);
        userServiceMock = mock(UserService.class);
        sut = new FileService(fileRepositoryMock, userServiceMock);
    }

    @Test
    void addFileTest() throws IOException {
        byte[] fileData = FileResourceHelper.getFileInByteArray("/myfile.txt");
        var mockFile = new MockMultipartFile("myfile.txt", fileData);
        when(userServiceMock.getAuthenticatedUser()).thenReturn(
                new User(1, "SALAH", "HIxi7PbCRU9uIyET6sdGEg==", "8H7jlDi3a2iPiu9ZI1+krA==", "Salah", "Yousef")
        );
        sut.addFile(mockFile);
        verify(userServiceMock, times(2)).getAuthenticatedUser();
        verify(fileRepositoryMock).add(argThat(argument ->
                argument instanceof File
        ));
    }

    @Test
    void getAllAuthenticatedUserFilesTest() {
        when(userServiceMock.getAuthenticatedUser()).thenReturn(
                new User(1, "SALAH", "HIxi7PbCRU9uIyET6sdGEg==", "8H7jlDi3a2iPiu9ZI1+krA==", "Salah", "Yousef")
        );
        var result = sut.getAllAuthenticatedUserFiles();
        verify(userServiceMock).getAuthenticatedUser();
        verify(fileRepositoryMock).getAllByUserId(1);
    }

    @Test
    void deleteFileByUserNameAndUserIdTest() {
        when(userServiceMock.getAuthenticatedUser()).thenReturn(
                new User(1, "SALAH", "HIxi7PbCRU9uIyET6sdGEg==", "8H7jlDi3a2iPiu9ZI1+krA==", "Salah", "Yousef")
        );
        sut.deleteFileByUserNameAndUserId("myfile.txt");
        verify(userServiceMock).getAuthenticatedUser();
        verify(fileRepositoryMock).deleteFileByFileNameAndUserId(1, "myfile.txt");
    }

    @Test
    void getFileByFileIdTest() {
        sut.getFileByFileId(92);
        verify(fileRepositoryMock).getByFileId(92);
    }

    @Test
    void isFileNameExistTest1() {
        when(userServiceMock.getAuthenticatedUser()).thenReturn(
                new User(1, "SALAH", "HIxi7PbCRU9uIyET6sdGEg==", "8H7jlDi3a2iPiu9ZI1+krA==", "Salah", "Yousef")
        );
        sut.isFileNameAlreadyExist("myfile.txt");
        verify(userServiceMock).getAuthenticatedUser();
        verify(fileRepositoryMock).getFileByFileNameAndUserId(1, "myfile.txt");
    }

    @Test
    void isFileNameExistTest2() throws IOException {
        when(userServiceMock.getAuthenticatedUser()).thenReturn(
                new User(142, "SALAH", "HIxi7PbCRU9uIyET6sdGEg==", "8H7jlDi3a2iPiu9ZI1+krA==", "Salah", "Yousef")
        );

        byte[] fileData = FileResourceHelper.getFileInByteArray("/myfile.txt");
        var fileSize = fileData.length + "";
        var myTestedFile = File.from("myFile", "String", fileSize, fileData);

        var file = new File();
        file.setUserId(142);
        file.setFileName("my_File_UzerZ_1.txt");
        file.setContentType("text/plain");
        file.setFileData(myTestedFile.getFileData());
        when(fileRepositoryMock.getByFileId(95)).thenReturn(
                file
        );

        sut.getFileByFileNameAndUserId(95);
        verify(fileRepositoryMock).getByFileId(95);
        verify(fileRepositoryMock).getFileByFileNameAndUserId(142, "my_File_UzerZ_1.txt");
    }

}