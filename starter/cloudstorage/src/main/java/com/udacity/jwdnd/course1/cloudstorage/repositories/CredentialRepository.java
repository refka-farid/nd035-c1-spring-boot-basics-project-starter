package com.udacity.jwdnd.course1.cloudstorage.repositories;

import com.udacity.jwdnd.course1.cloudstorage.entities.Credential;
import com.udacity.jwdnd.course1.cloudstorage.mappers.CredentialMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CredentialRepository {
    private final Logger logger = LoggerFactory.getLogger(CredentialRepository.class);

    private final CredentialMapper mapper;

    public CredentialRepository(CredentialMapper mapper) {
        this.mapper = mapper;
    }

    public boolean add(Credential credential) {
        logger.trace("add credential " + credential);
        var id = mapper.add(credential);
        return id > 0;
    }

    public Credential getByUrl(String url) {
        return mapper.getByUrl(url);
    }

    public Credential getByCredentialId(int credentialId) {
        return mapper.getByCredentialId(credentialId);
    }

    public Credential getByUrlAndUserId(Integer userId, String url) {
        return mapper.getByUrlAndUserId(userId, url);
    }

    public List<Credential> getAll(int userId) {
        return mapper.getAll(userId);
    }

    public boolean updateCredential(Credential credential) {
        return mapper.updateCredential(credential);
    }

    public int deleteByUrl(String url) {
        return mapper.deleteByUrl(url);
    }

    public boolean deleteByUrl2(String url) {
        return mapper.deleteByUrl2(url);
    }

    public boolean deleteAll() {
        return mapper.deleteAll();
    }

    public boolean deleteByCredentialIdAndUserId(int userId, int credentialId) {
        return mapper.deleteByCredentialIdAndUserId(userId, credentialId);
    }


}
