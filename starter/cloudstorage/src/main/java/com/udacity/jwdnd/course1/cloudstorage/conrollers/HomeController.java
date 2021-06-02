package com.udacity.jwdnd.course1.cloudstorage.conrollers;

import com.udacity.jwdnd.course1.cloudstorage.entities.File;
import com.udacity.jwdnd.course1.cloudstorage.models.FileResponseDto;
import com.udacity.jwdnd.course1.cloudstorage.models.UploadFileResponseDto;
import com.udacity.jwdnd.course1.cloudstorage.services.file.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.file.FileTypeLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/home")
public class HomeController {
    private final Logger logger = LoggerFactory.getLogger(HomeController.class);

    private final FileTypeLoader fileTypeLoader;
    private final FileService fileService;

    public HomeController(FileTypeLoader fileTypeLoader, FileService fileService) {
        this.fileTypeLoader = fileTypeLoader;
        this.fileService = fileService;
    }

    @GetMapping()
    public String getHomePage(Model model) {
        List<File> allAuthenticatedUserFiles = fileService.getAllAuthenticatedUserFiles();
        final var fileResponseDtoList = allAuthenticatedUserFiles
                .stream()
                .map(FileResponseDto::fromFile)
                .collect(Collectors.toList());
        var uploadFileResponseDto = new UploadFileResponseDto(false);
        model.addAttribute("fileResponseDtoList", fileResponseDtoList);
        model.addAttribute("uploadFileResponseDto", uploadFileResponseDto);
        return "home";
    }

    @GetMapping("/file/view/{id}")
    public ResponseEntity<ByteArrayResource> getHomeFileView(Model model, @PathVariable("id") Integer id) {
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
        List<File> allAuthenticatedUserFiles = fileService.getAllAuthenticatedUserFiles();
        final var fileResponseDtoList = allAuthenticatedUserFiles
                .stream()
                .map(FileResponseDto::fromFile)
                .collect(Collectors.toList());
        var uploadFileResponseDto = new UploadFileResponseDto(false);
        model.addAttribute("uploadFileResponseDto", uploadFileResponseDto);
        model.addAttribute("fileResponseDtoList", fileResponseDtoList);
        logger.trace(" file deleted with id : " + id);
        return "home";
    }

    @PostMapping("/file/upload")
    public String handleFileUpload(@RequestParam("fileUpload") MultipartFile file, Model model) {
        var uploadedFileNameFile = file.getOriginalFilename();
        var isFileNameExist = fileService.isFileNameAlreadyExist(uploadedFileNameFile);
        var uploadFileResponseDto = new UploadFileResponseDto(isFileNameExist);
        fileService.addFile(file);
        List<File> allAuthenticatedUserFiles = fileService.getAllAuthenticatedUserFiles();
        final var fileResponseDtoList = allAuthenticatedUserFiles
                .stream()
                .map(FileResponseDto::fromFile)
                .collect(Collectors.toList());
        model.addAttribute("fileResponseDtoList", fileResponseDtoList);
        model.addAttribute("uploadFileResponseDto", uploadFileResponseDto);
        return "home";
    }
}
