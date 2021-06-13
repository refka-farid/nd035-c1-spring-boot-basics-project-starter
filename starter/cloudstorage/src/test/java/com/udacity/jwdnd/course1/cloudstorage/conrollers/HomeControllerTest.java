package com.udacity.jwdnd.course1.cloudstorage.conrollers;

import com.udacity.jwdnd.course1.cloudstorage.models.CredentialResponseDto;
import com.udacity.jwdnd.course1.cloudstorage.models.FileResponseDto;
import com.udacity.jwdnd.course1.cloudstorage.models.NoteResponseDto;
import com.udacity.jwdnd.course1.cloudstorage.models.UploadFileResponseDto;
import com.udacity.jwdnd.course1.cloudstorage.services.credential.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.file.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.note.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.util.CredentialFactory;
import com.udacity.jwdnd.course1.cloudstorage.util.FileFactory;
import com.udacity.jwdnd.course1.cloudstorage.util.NoteFactory;
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
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(profiles = {"dev"})
class HomeControllerTest {

    @Inject
    private MockMvc mockMvc;

    @MockBean
    private FileService fileServiceMock;

    @MockBean
    private NoteService noteServiceMock;

    @MockBean
    private CredentialService credentialServiceMock;

    @WithMockUser(username = "ali")
    @Test
    void getTest() throws Exception {
        given(fileServiceMock.getAllAuthenticatedUserFiles()).willReturn(FileFactory.createFileList());
        var list = FileFactory.createFileList();
        var fileResponseDtoList = list.stream().map(FileResponseDto::fromFile).collect(Collectors.toList());

        given(noteServiceMock.getAllAuthenticatedUserNote()).willReturn(NoteFactory.createNoteList());
        var list2 = NoteFactory.createNoteList();
        var noteResponseDtoList = list2.stream().map(NoteResponseDto::fromNote).collect(Collectors.toList());

        given(credentialServiceMock.getAllAuthenticatedUserCredential()).willReturn(CredentialFactory.createListOfCredential());
        var list3 = CredentialFactory.createListOfCredential();
        var credentialResponseDtoList = list3.stream().map(credential -> CredentialResponseDto.fromCredential(credential, "azerty", null)).collect(Collectors.toList());

        mockMvc.perform(get("/home"))
                .andExpect(model().attribute("noteResponseDtoList", is(noteResponseDtoList)))
                .andExpect(model().attribute("fileResponseDtoList", is(fileResponseDtoList)))
                .andExpect(model().attribute("uploadFileResponseDto", is(new UploadFileResponseDto(false))))
                .andExpect(model().attribute("credentialResponseDtoList", is(credentialResponseDtoList)))
                .andExpect(view().name("home"))
                .andExpect(content().string(containsString("<div class=\"col-sm-2\">\n" +
                        "                                <label for=\"fileUpload\">Upload a New File:</label>\n" +
                        "                            </div>")))
                .andExpect(content().string(containsString("<div class=\"form-group\">\n" +
                        "                                        <label for=\"note-description\" class=\"col-form-label\">Description</label>")))
                .andExpect(content().string(containsString("<div class=\"form-group\">\n" +
                        "                                        <label for=\"note-title\" class=\"col-form-label\">URL</label>")))
                .andExpect(status().isOk());
    }

    @WithMockUser(username = "john")
    @Test
    void getBaseUrlHomeAuthenticatedTest() throws Exception {
        given(fileServiceMock.getAllAuthenticatedUserFiles()).willReturn(FileFactory.createFileList());
        var list = FileFactory.createFileList();
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
                .param("id", "my_File_UzerZ_1.txt"))//todo
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


    @WithMockUser(username = "z")
    @Test
    void addNoteTest() throws Exception {
        given(noteServiceMock.getNoteByNoteId(100)).willReturn(NoteFactory.createNoteWithoutNoteId());

        given(fileServiceMock.getAllAuthenticatedUserFiles()).willReturn(FileFactory.createFileList());
        var list = FileFactory.createFileList();
        var fileResponseDtoList = list.stream().map(FileResponseDto::fromFile).collect(Collectors.toList());

        given(noteServiceMock.getAllAuthenticatedUserNote()).willReturn(NoteFactory.createNoteList());
        var list2 = NoteFactory.createNoteList();
        var note = NoteFactory.createNoteWithoutNoteId();
        var noteResponseDtoList = list2.stream().map(NoteResponseDto::fromNote).collect(Collectors.toList());

        mockMvc.perform(post("/home/note/addOrEdit")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("noteId", (String) null)
                .param("noteTitle", note.getNoteTitle())
                .param("noteDescription", note.getNoteDescription())
        )
                .andExpect(model().attribute("noteResponseDtoList", is(noteResponseDtoList)))
                .andExpect(model().attribute("fileResponseDtoList", is(fileResponseDtoList)))
                .andExpect(model().attribute("uploadFileResponseDto", is(new UploadFileResponseDto(false))))
                .andExpect(status().isOk());
        verify(noteServiceMock).addNote(argThat(argument ->
                argument.getNoteTitle().equals(note.getNoteTitle()) &&
                        argument.getNoteDescription().equals(note.getNoteDescription())
        ));
        verify(noteServiceMock).getAllAuthenticatedUserNote();

    }

    @WithMockUser(username = "z")
    @Test
    void editNoteTest() throws Exception {
        given(noteServiceMock.getNoteByNoteId(100)).willReturn(NoteFactory.createNoteWithNoteId());

        given(fileServiceMock.getAllAuthenticatedUserFiles()).willReturn(FileFactory.createFileList());
        var list = FileFactory.createFileList();
        var fileResponseDtoList = list.stream().map(FileResponseDto::fromFile).collect(Collectors.toList());

        given(noteServiceMock.getAllAuthenticatedUserNote()).willReturn(NoteFactory.createNoteList());
        var list2 = NoteFactory.createNoteList();
        var note = NoteFactory.createNoteWithNoteId();
        var noteResponseDtoList = list2.stream().map(NoteResponseDto::fromNote).collect(Collectors.toList());


        mockMvc.perform(post("/home/note/addOrEdit")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("noteId", "100")
                .param("noteTitle", note.getNoteTitle())
                .param("noteDescription", note.getNoteDescription())
        )
                .andExpect(model().attribute("noteResponseDtoList", is(noteResponseDtoList)))
                .andExpect(model().attribute("fileResponseDtoList", is(fileResponseDtoList)))
                .andExpect(model().attribute("uploadFileResponseDto", is(new UploadFileResponseDto(false))))
                .andExpect(status().isOk());
        verify(noteServiceMock).update(argThat(argument -> argument.getNoteId().equals(note.getNoteId())));
    }

    @WithMockUser(username = "z")
    @Test
    void deleteNoteTest() throws Exception {
        given(noteServiceMock.getNoteByNoteId(100)).willReturn(NoteFactory.createNoteWithNoteId());
        given(noteServiceMock.deleteNoteByNoteIdAndUserId(100)).willReturn(true);

        mockMvc.perform(get("/home/note/delete/100")
                .param("id", "100"))
                .andExpect(status().isOk());
        verify(noteServiceMock).getNoteByNoteId(100);
        verify(noteServiceMock).deleteNoteByNoteIdAndUserId(100);
    }

    @WithMockUser(username = "z")
    @Test
    void addCredentialTest() throws Exception {
        given(credentialServiceMock.getByCredentialId(100)).willReturn(CredentialFactory.createCredentialWithoutCredentialId());

        given(fileServiceMock.getAllAuthenticatedUserFiles()).willReturn(FileFactory.createFileList());
        var list = FileFactory.createFileList();
        var fileResponseDtoList = list.stream().map(FileResponseDto::fromFile).collect(Collectors.toList());

        given(noteServiceMock.getAllAuthenticatedUserNote()).willReturn(NoteFactory.createNoteList());
        var list2 = NoteFactory.createNoteList();
        var noteResponseDtoList = list2.stream().map(NoteResponseDto::fromNote).collect(Collectors.toList());

        given(credentialServiceMock.getAllAuthenticatedUserCredential()).willReturn(CredentialFactory.createListOfCredential());
        var list3 = CredentialFactory.createListOfCredential();
        var mCredential = CredentialFactory.createCredentialWithoutCredentialId();
        var credentialResponseDtoList = list3.stream().map(credential -> CredentialResponseDto.fromCredential(credential, "azerty", null)).collect(Collectors.toList());

        mockMvc.perform(post("/home/credential/addOrEdit")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("credentialId", (String) null)
                .param("url", mCredential.getUrl())
                .param("username", mCredential.getUserName())
                .param("password", mCredential.getPassword())
        )
                .andExpect(model().attribute("noteResponseDtoList", is(noteResponseDtoList)))
                .andExpect(model().attribute("fileResponseDtoList", is(fileResponseDtoList)))
                .andExpect(model().attribute("uploadFileResponseDto", is(new UploadFileResponseDto(false))))
                .andExpect(model().attribute("credentialResponseDtoList", is(credentialResponseDtoList)))
                .andExpect(status().isOk());
        verify(credentialServiceMock).add(argThat(argument ->
                argument.getUrl().equals(mCredential.getUrl()) &&
                        argument.getUserName().equals(mCredential.getUserName())
        ));
        verify(credentialServiceMock).getAllAuthenticatedUserCredential();

    }

    @WithMockUser(username = "z")
    @Test
    void updateCredentialTest() throws Exception {
        given(credentialServiceMock.getByCredentialId(100)).willReturn(CredentialFactory.createCredentialWithCredentialId());

        given(fileServiceMock.getAllAuthenticatedUserFiles()).willReturn(FileFactory.createFileList());
        var list = FileFactory.createFileList();
        var fileResponseDtoList = list.stream().map(FileResponseDto::fromFile).collect(Collectors.toList());

        given(noteServiceMock.getAllAuthenticatedUserNote()).willReturn(NoteFactory.createNoteList());
        var list2 = NoteFactory.createNoteList();
        var noteResponseDtoList = list2.stream().map(NoteResponseDto::fromNote).collect(Collectors.toList());

        given(credentialServiceMock.getAllAuthenticatedUserCredential()).willReturn(CredentialFactory.createListOfCredential());
        var list3 = CredentialFactory.createListOfCredential();
        var mCredential = CredentialFactory.createCredentialWithoutCredentialId();
        var credentialResponseDtoList = list3.stream().map(credential -> CredentialResponseDto.fromCredential(credential, "azerty", null)).collect(Collectors.toList());

        mockMvc.perform(post("/home/credential/addOrEdit")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("credentialId", "100")
                .param("url", mCredential.getUrl())
                .param("username", mCredential.getUserName())
                .param("password", mCredential.getPassword())
        )
                .andExpect(model().attribute("noteResponseDtoList", is(noteResponseDtoList)))
                .andExpect(model().attribute("fileResponseDtoList", is(fileResponseDtoList)))
                .andExpect(model().attribute("uploadFileResponseDto", is(new UploadFileResponseDto(false))))
                .andExpect(model().attribute("credentialResponseDtoList", is(credentialResponseDtoList)))
                .andExpect(status().isOk());
        verify(credentialServiceMock).update(argThat(argument ->
                argument.getUrl().equals(mCredential.getUrl()) &&
                        argument.getUserName().equals(mCredential.getUserName())
        ));
        verify(credentialServiceMock).getAllAuthenticatedUserCredential();

    }

    @WithMockUser(username = "z")
    @Test
    void deleteCredentialTest() throws Exception {
        given(credentialServiceMock.getByCredentialId(100)).willReturn(CredentialFactory.createCredentialWithCredentialId());
        given(credentialServiceMock.deleteByCredentialIdAndUserId(100)).willReturn(true);

        mockMvc.perform(get("/home/credential/delete/100")
                .param("id", "100"))
                .andExpect(status().isOk());
        verify(credentialServiceMock).getByCredentialId(100);
        verify(credentialServiceMock).deleteByCredentialIdAndUserId(100);
    }
}
