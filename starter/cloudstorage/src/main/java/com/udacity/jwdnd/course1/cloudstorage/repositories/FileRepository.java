package com.udacity.jwdnd.course1.cloudstorage.repositories;

import com.udacity.jwdnd.course1.cloudstorage.entities.File;
import com.udacity.jwdnd.course1.cloudstorage.mappers.FileMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class FileRepository {
    private final Logger logger = LoggerFactory.getLogger(FileRepository.class);

    private final FileMapper mapper;

    public FileRepository(FileMapper mapper) {
        this.mapper = mapper;
    }

    public File getOne(String fileName) {
        return mapper.getByFileName(fileName);
    }

    public boolean add(File file) {
        logger.trace("add file " + file);
        int id = mapper.add(file);
        return id > 0;
    }

    public boolean delete1(File file) {
        logger.trace("delete1 " + file);
        boolean isDeleted = mapper.deleteByFileName2(file.getFileName());
        return isDeleted;
    }

    public boolean delete2(File file) {
        logger.trace("delete2" + file);
        int rows = mapper.deleteByFileName(file.getFileName());
        return rows > 0;
    }

    public boolean deleteAll() {
        logger.trace("deleteAll");
        boolean allDeleted = mapper.deleteAll();
        return allDeleted;
    }

}
