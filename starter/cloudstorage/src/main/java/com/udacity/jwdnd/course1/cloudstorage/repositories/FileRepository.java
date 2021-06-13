package com.udacity.jwdnd.course1.cloudstorage.repositories;

import com.udacity.jwdnd.course1.cloudstorage.entities.File;
import com.udacity.jwdnd.course1.cloudstorage.mappers.FileMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FileRepository {
    private final Logger logger = LoggerFactory.getLogger(FileRepository.class);

    private final FileMapper mapper;

    public FileRepository(FileMapper mapper) {
        this.mapper = mapper;
    }

    public File getByFileName(String fileName) {
        return mapper.getByFileName(fileName);
    }

    public List<File> getAllByUserId(int userId) {
        return mapper.getAll(userId);
    }

    public boolean add(File file) {
        logger.trace("add file " + file);
        int id = mapper.add(file);
        return id > 0;
    }

    public boolean deleteAll() {
        logger.trace("deleteAll");
        return mapper.deleteAll();
    }

    public boolean deleteFileByFileNameAndUserId(int userId, String fileName) {
        logger.trace("deleteFileByFileNameAndUserId " + userId + fileName);
        return mapper.deleteFileByFileNameAndUserId(userId, fileName);
    }

    public File getByFileId(int fileId) {
        return mapper.getByFileId(fileId);
    }

    public File getFileByFileNameAndUserId(Integer userId, String fileName) {
        logger.trace("getFileByFileNameAndUserId " + userId + fileName);
        return mapper.getFileByFileNameAndUserId(userId, fileName);
    }
}
