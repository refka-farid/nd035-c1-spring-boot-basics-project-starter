package com.udacity.jwdnd.course1.cloudstorage.conrollers;

import com.udacity.jwdnd.course1.cloudstorage.conrollers.util.HomeAttributesModel;
import com.udacity.jwdnd.course1.cloudstorage.models.CredentialResponseDto;
import com.udacity.jwdnd.course1.cloudstorage.models.FileResponseDto;
import com.udacity.jwdnd.course1.cloudstorage.models.NoteResponseDto;
import com.udacity.jwdnd.course1.cloudstorage.models.UploadFileResponseDto;
import com.udacity.jwdnd.course1.cloudstorage.services.credential.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.file.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.file.FileTypeLoader;
import com.udacity.jwdnd.course1.cloudstorage.services.note.NoteService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/home")
public class FileController {

    private final FileTypeLoader fileTypeLoader;
    private final FileService fileService;
    private final HomeAttributesModel homeAttributesModel;


    public FileController(FileTypeLoader fileTypeLoader, FileService fileService, NoteService noteService, CredentialService credentialService) {
        this.fileTypeLoader = fileTypeLoader;
        this.fileService = fileService;
        homeAttributesModel = new HomeAttributesModel(fileTypeLoader, fileService, noteService, credentialService);
    }

    @GetMapping("/file/view/{id}")
    public ResponseEntity<ByteArrayResource> getHomeFileView(@PathVariable("id") Integer id) {
        var file = fileService.getFileByFileNameAndUserId(id);
        var dataFile = file.getFileData();
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(fileTypeLoader.getFileMimeType(dataFile)))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
                .body(new ByteArrayResource(dataFile));
    }

    @GetMapping("/file/delete/{id}")
    public String getHomeFileDelete(Model model, @PathVariable("id") Integer id) {
        var file = fileService.getFileByFileNameAndUserId(id);
        fileService.deleteFileByUserNameAndUserId(file.getFileName());
        homeAttributesModel.addAllAttributesModel(model);
        return "home";
    }

    @PostMapping("/file/upload")
    public String handleFileUpload(@RequestParam("fileUpload") MultipartFile file, Model model) {
        var uploadedFileNameFile = file.getOriginalFilename();
        var isFileNameExist = fileService.isFileNameAlreadyExist(uploadedFileNameFile);
        var uploadFileResponseDto = new UploadFileResponseDto(isFileNameExist);
        fileService.addFile(file);

        final List<FileResponseDto> fileResponseDtoList = homeAttributesModel.fromFileListToFileResponseDtoList();
        List<NoteResponseDto> noteResponseDtoList = homeAttributesModel.fromNoteListToNoteResponseDtoList();
        final List<CredentialResponseDto> credentialResponseDtoList = homeAttributesModel.fromCredentialListToCredentialResponseDto();

        model.addAttribute("noteResponseDtoList", noteResponseDtoList);
        model.addAttribute("credentialResponseDtoList", credentialResponseDtoList);
        model.addAttribute("fileResponseDtoList", fileResponseDtoList);
        model.addAttribute("uploadFileResponseDto", uploadFileResponseDto);
        return "home";
    }

}
