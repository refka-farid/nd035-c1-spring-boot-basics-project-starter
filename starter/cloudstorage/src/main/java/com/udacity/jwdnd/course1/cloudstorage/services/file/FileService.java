package com.udacity.jwdnd.course1.cloudstorage.services.file;

import com.udacity.jwdnd.course1.cloudstorage.entities.File;
import com.udacity.jwdnd.course1.cloudstorage.repositories.FileRepository;
import com.udacity.jwdnd.course1.cloudstorage.services.signup.UserService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {

    private final FileRepository fileRepository;
    private final UserService userService;

    public FileService(FileRepository fileRepository, UserService userService) {
        this.fileRepository = fileRepository;
        this.userService = userService;
    }

    public List<File> getAllAuthenticatedUserFiles() {
        var user = userService.getAuthenticatedUser();
        return fileRepository.getAllByUserId(user.getUserId());
    }

    public boolean deleteFileByUserNameAndUserId(String fileName) {
        var user = userService.getAuthenticatedUser();
        return fileRepository.deleteFileByFileNameAndUserId(user.getUserId(), fileName);
    }

    public File getFileByFileId(int fileId) {
        return fileRepository.getByFileId(fileId);
    }

    public File getFileByFileNameAndUserId(int fileId) {
        var user = userService.getAuthenticatedUser();
        var fileName = fileRepository.getByFileId(fileId).getFileName();
//        var fileName = getFileByFileId(fileId).getFileName();
        return fileRepository.getFileByFileNameAndUserId(user.getUserId(), fileName);
    }

    public boolean isFileNameAlreadyExist(String fileName) {
        var user = userService.getAuthenticatedUser();
        var file = fileRepository.getFileByFileNameAndUserId(user.getUserId(), fileName);
        return file != null;
    }

    public boolean addFile(MultipartFile file) {
        var user = userService.getAuthenticatedUser();
        var myFile = new File();
        try {
            myFile.setFileData(file.getBytes());
            myFile.setFileName(file.getOriginalFilename());
            myFile.setContentType(file.getContentType());
            myFile.setFileSize(file.getSize() + "");
            myFile.setUserId(user.getUserId());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!isFileNameAlreadyExist(file.getOriginalFilename())) {
            return fileRepository.add(myFile);
        } else {
            return false;
        }
    }
}
