package com.udacity.jwdnd.course1.cloudstorage.conrollers;

import com.udacity.jwdnd.course1.cloudstorage.models.FileResponseDto;
import com.udacity.jwdnd.course1.cloudstorage.models.UploadFileResponseDto;
import com.udacity.jwdnd.course1.cloudstorage.services.file.FileService;
import com.udacity.jwdnd.course1.cloudstorage.util.FileFactory;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import javax.inject.Inject;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(profiles = {"dev"})
class HomeControllerTest {

    @Inject
    private MockMvc mockMvc;

    @MockBean
    private FileService fileServiceMock;

    @WithMockUser(username = "john")
    @Test
    void getTest() throws Exception {
        given(fileServiceMock.getAllAuthenticatedUserFiles()).willReturn(FileFactory.createFileList());
        var list = FileFactory.createFileList();
        var fileResponseDtoList = list.stream().map(FileResponseDto::fromFile).collect(Collectors.toList());
        mockMvc.perform(get("/home"))
                .andExpect(model().attribute("fileResponseDtoList", is(fileResponseDtoList)))
                .andExpect(model().attribute("uploadFileResponseDto", is(new UploadFileResponseDto(false))))
                .andExpect(view().name("home"))
                .andExpect(content().string(containsString("<table class=\"table table-striped\" id=\"fileTable\">\n" +
                        "                                <thead>\n" +
                        "                                    <tr>\n" +
                        "                                        <th style=\"width: 20%\" scope=\"col\"></th>\n" +
                        "                                        <th style=\"width: 80%\" scope=\"col\">File Name</th>\n" +
                        "                                    </tr>\n" +
                        "                                </thead>")))
                .andExpect(status().isOk());
    }

    @WithMockUser(username = "john")
    @Test
    void getBaseUrlHomeAuthenticatedTest() throws Exception {
        given(fileServiceMock.getAllAuthenticatedUserFiles()).willReturn(FileFactory.createFileList());
        var list = FileFactory.createFileList();
        var fileResponseDtoList = list.stream().map(FileResponseDto::fromFile).collect(Collectors.toList());
        mockMvc.perform(get("/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/home"))
        ;
    }

    @Test
    void getBaseUrlHomeNonAuthenticatedTest() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @WithMockUser(username = "z")
    @Test
    void getHomeFileViewTest() throws Exception {
        given(fileServiceMock.getFileByFileNameAndUserId(100)).willReturn(FileFactory.createFile());
        mockMvc.perform(get("/home/file/view/100")
                .contentType(MediaType.parseMediaType("text/plain"))
                .param("id", "100"))
                .andExpect(status().isOk());
    }

    @WithMockUser(username = "z")
    @Test
    void getHomeFileDeleteTest() throws Exception {
        given(fileServiceMock.getFileByFileNameAndUserId(100)).willReturn(FileFactory.createFile());
        given(fileServiceMock.deleteFileByUserNameAndUserId("100")).willReturn(true);
        given(fileServiceMock.getAllAuthenticatedUserFiles()).willReturn(FileFactory.createFileList());
        var list = FileFactory.createFileList();
        var fileResponseDtoList = list.stream().map(FileResponseDto::fromFile).collect(Collectors.toList());

        mockMvc.perform(get("/home/file/delete/100")
                .contentType(MediaType.parseMediaType("text/plain"))
                .param("id", "my_File_UzerZ_1.txt"))
                .andExpect(model().attribute("fileResponseDtoList", is(fileResponseDtoList)))
                .andExpect(model().attribute("uploadFileResponseDto", is(new UploadFileResponseDto(false))))
                .andExpect(status().isOk());
    }

    @WithMockUser(username = "z")
    @Test
    void handleFileUploadTest() throws Exception {
        byte[] fileData = FileFactory.createFile().getFileData();
        MockMultipartFile mockFile = new MockMultipartFile("fileUpload", "myfile.txt", MediaType.TEXT_PLAIN_VALUE, fileData);
        given(fileServiceMock.addFile(mockFile)).willReturn(true);
        mockMvc.perform(multipart("/home/file/upload").file(mockFile))
                .andExpect(status().isOk());
    }
}
