package com.udacity.jwdnd.course1.cloudstorage.conrollers.util;

import com.udacity.jwdnd.course1.cloudstorage.entities.File;
import com.udacity.jwdnd.course1.cloudstorage.models.CredentialResponseDto;
import com.udacity.jwdnd.course1.cloudstorage.models.FileResponseDto;
import com.udacity.jwdnd.course1.cloudstorage.models.NoteResponseDto;
import com.udacity.jwdnd.course1.cloudstorage.models.UploadFileResponseDto;
import com.udacity.jwdnd.course1.cloudstorage.services.credential.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.file.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.file.FileTypeLoader;
import com.udacity.jwdnd.course1.cloudstorage.services.note.NoteService;
import org.springframework.ui.Model;

import java.util.List;
import java.util.stream.Collectors;

public class HomeAttributesModel {

    private final FileTypeLoader fileTypeLoader;
    private final FileService fileService;
    private final NoteService noteService;
    private final CredentialService credentialService;

    public HomeAttributesModel(FileTypeLoader fileTypeLoader, FileService fileService, NoteService noteService, CredentialService credentialService) {
        this.fileTypeLoader = fileTypeLoader;
        this.fileService = fileService;
        this.noteService = noteService;
        this.credentialService = credentialService;
    }

    public void addAllAttributesModel(Model model) {
        final List<FileResponseDto> fileResponseDtoList = fromFileListToFileResponseDtoList();
        var uploadFileResponseDto = new UploadFileResponseDto(false);
        List<NoteResponseDto> noteResponseDtoList = fromNoteListToNoteResponseDtoList();
        final List<CredentialResponseDto> credentialResponseDtoList = fromCredentialListToCredentialResponseDto();

        model.addAttribute("uploadFileResponseDto", uploadFileResponseDto);
        model.addAttribute("fileResponseDtoList", fileResponseDtoList);
        model.addAttribute("noteResponseDtoList", noteResponseDtoList);
        model.addAttribute("credentialResponseDtoList", credentialResponseDtoList);
    }

    public List<FileResponseDto> fromFileListToFileResponseDtoList() {
        List<File> allAuthenticatedUserFiles = fileService.getAllAuthenticatedUserFiles();
        return allAuthenticatedUserFiles
                .stream()
                .map(FileResponseDto::fromFile)
                .collect(Collectors.toList());
    }

    public List<NoteResponseDto> fromNoteListToNoteResponseDtoList() {
        var storedNotesList = noteService.getAllAuthenticatedUserNote();
        return storedNotesList.stream()
                .map(NoteResponseDto::fromNote)
                .collect(Collectors.toList());
    }

    public List<CredentialResponseDto> fromCredentialListToCredentialResponseDto() {
        var storedCredentialList = credentialService.getAllAuthenticatedUserCredential();
        List<CredentialResponseDto> credentialResponseDtoList;
        credentialResponseDtoList = storedCredentialList.stream()
                .map(credential -> {
                    String encryptedCredentialPassword = credential.getPassword();
                    String unencryptedCredentialPassword = credentialService.getUnencryptedCredentialPassword(credential.getPassword(), credential.getKey());
                    return CredentialResponseDto.fromCredential(credential, encryptedCredentialPassword, unencryptedCredentialPassword);
                })
                .collect(Collectors.toList());
        return credentialResponseDtoList;
    }
}
