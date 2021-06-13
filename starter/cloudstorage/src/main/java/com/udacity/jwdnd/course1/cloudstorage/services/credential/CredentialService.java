package com.udacity.jwdnd.course1.cloudstorage.services.credential;

import com.udacity.jwdnd.course1.cloudstorage.entities.Credential;
import com.udacity.jwdnd.course1.cloudstorage.entities.User;
import com.udacity.jwdnd.course1.cloudstorage.repositories.CredentialRepository;
import com.udacity.jwdnd.course1.cloudstorage.services.signup.UserService;
import com.udacity.jwdnd.course1.cloudstorage.services.utilsecurity.EncryptionService;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.udacity.jwdnd.course1.cloudstorage.services.utilsecurity.RandomKey.getBase64EncodedKey;

@Service
public class CredentialService {
    private final UserService userService;
    private final CredentialRepository credentialRepository;
    private final EncryptionService encryptionService;

    public CredentialService(UserService userService, CredentialRepository credentialRepository, EncryptionService encryptionService) {
        this.userService = userService;
        this.credentialRepository = credentialRepository;
        this.encryptionService = encryptionService;
    }

    public String getUnencryptedCredentialPassword(String data, String key) {
        return encryptionService.decryptValue(data, key);
    }

    public String getEncryptedCredentialPassword(String data, String key) {
        return encryptionService.encryptValue(data, key);
    }

    private User getAuthenticatedUser() {
        return userService.getAuthenticatedUser();
    }

    public void add(Credential credential) {
        var key = getBase64EncodedKey();
        var encryptedPassword = getEncryptedCredentialPassword(credential.getPassword(), key);
        var userId = getAuthenticatedUser().getUserId();
        var newCredential1 = new Credential(null, credential.getUrl(), credential.getUserName(), key, encryptedPassword, userId);
        credentialRepository.add(newCredential1);
    }

    public void update(Credential credential) {
        var credentialToUpdate = credentialRepository.getByCredentialId(credential.getCredentialId());
        credentialToUpdate.setUrl(credential.getUrl());
        credentialToUpdate.setUserName(credential.getUserName());
        credentialToUpdate.setUserId(userService.getAuthenticatedUser().getUserId());
        if (!credential.getPassword().equals(getUnencryptedCredentialPassword(credentialToUpdate.getPassword(), credentialToUpdate.getKey()))) {
            credentialToUpdate.setPassword(getEncryptedCredentialPassword(credential.getPassword(), credentialToUpdate.getKey()));
        }
        credentialRepository.updateCredential(credentialToUpdate);
    }

    public boolean deleteByCredentialIdAndUserId(int credentialId) {
        var user = userService.getAuthenticatedUser();
        return credentialRepository.deleteByCredentialIdAndUserId(user.getUserId(), credentialId);
    }

    public List<Credential> getAllAuthenticatedUserCredential() {
        var user = getAuthenticatedUser();
        return credentialRepository.getAll(user.getUserId());
    }

    public Credential getByCredentialId(Integer id) {
        return credentialRepository.getByCredentialId(id);
    }
}
