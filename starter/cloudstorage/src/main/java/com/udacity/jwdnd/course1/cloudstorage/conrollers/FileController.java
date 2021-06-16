package com.udacity.jwdnd.course1.cloudstorage.conrollers;

import com.udacity.jwdnd.course1.cloudstorage.conrollers.util.HomeAttributesModel;
import com.udacity.jwdnd.course1.cloudstorage.models.CredentialResponseDto;
import com.udacity.jwdnd.course1.cloudstorage.models.FileResponseDto;
import com.udacity.jwdnd.course1.cloudstorage.models.NoteResponseDto;
import com.udacity.jwdnd.course1.cloudstorage.services.credential.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.file.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.file.FileTypeLoader;
import com.udacity.jwdnd.course1.cloudstorage.services.note.NoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/home")
public class FileController {

    private final Logger logger = LoggerFactory.getLogger(FileController.class);

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
    public String getHomeFileDelete(Model model, @PathVariable("id") Integer id, RedirectAttributes redirAttrs) {
        var file = fileService.getFileByFileNameAndUserId(id);
        fileService.deleteFileByUserNameAndUserId(file.getFileName());
        redirAttrs.addFlashAttribute("successFile", "Your file was successfully deleted.");
        homeAttributesModel.addAllAttributesModel(model);
        return "redirect:/home/";
    }

    @PostMapping("/file/upload")
    public String handleFileUpload(@RequestParam("fileUpload") MultipartFile file, Model model, RedirectAttributes redirAttrs) {
        if (file.isEmpty()) {
            redirAttrs.addFlashAttribute("errorFile", "Please select a file!");
        } else {
            var uploadedFileNameFile = file.getOriginalFilename();
            var isFileNameExist = fileService.isFileNameAlreadyExist(uploadedFileNameFile);
            if (isFileNameExist) {
                redirAttrs.addFlashAttribute("errorFile", "The file name already exist.");
            } else {
                fileService.addFile(file);
                redirAttrs.addFlashAttribute("successFile", "Your New File was successfully added.");
            }
        }
        final List<FileResponseDto> fileResponseDtoList = homeAttributesModel.fromFileListToFileResponseDtoList();
        List<NoteResponseDto> noteResponseDtoList = homeAttributesModel.fromNoteListToNoteResponseDtoList();
        final List<CredentialResponseDto> credentialResponseDtoList = homeAttributesModel.fromCredentialListToCredentialResponseDto();

        model.addAttribute("noteResponseDtoList", noteResponseDtoList);
        model.addAttribute("credentialResponseDtoList", credentialResponseDtoList);
        model.addAttribute("fileResponseDtoList", fileResponseDtoList);
        return "redirect:/home/";
    }

    @ExceptionHandler(value = MaxUploadSizeExceededException.class)
    public String onUploadError2(HttpServletRequest request, RedirectAttributes redirAttrs) {
        logger.error("error while uploading file");
        redirAttrs.addFlashAttribute("errorFile", "Maximum upload size exceeded");
        Object errorMessageAttribute = request.getAttribute(WebUtils.ERROR_MESSAGE_ATTRIBUTE);
        logger.error(" " + errorMessageAttribute);
        return "redirect:/home/";
    }
}
