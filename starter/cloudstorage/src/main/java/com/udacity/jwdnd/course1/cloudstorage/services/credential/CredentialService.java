package com.udacity.jwdnd.course1.cloudstorage.services.credential;

import com.udacity.jwdnd.course1.cloudstorage.entities.Credential;
import com.udacity.jwdnd.course1.cloudstorage.entities.User;
import com.udacity.jwdnd.course1.cloudstorage.repositories.CredentialRepository;
import com.udacity.jwdnd.course1.cloudstorage.services.signup.UserService;
import com.udacity.jwdnd.course1.cloudstorage.services.utilsecurity.EncryptionService;
import org.springframework.stereotype.Service;

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

    public String getUnencryptedCredentialPassword(Credential credential) {
        var storedCredential = credentialRepository.getByCredentialId(credential.getCredentialId());
        var encodedKey = storedCredential.getKey();
        var encryptPassword= encryptionService.encryptValue(credential.getPassword(),encodedKey);
        return encryptionService.decryptValue(encryptPassword, encodedKey);
    }

    public User getAuthenticatedUser() {
        return userService.getAuthenticatedUser();
    }

    public Credential getByCredentialIdAndUserId(Credential credential) {
        return credentialRepository.getByCredentialId(credential.getCredentialId());
    }

    public void addOrUpdate(Credential credential) {
        var storedCredential = credentialRepository.getByCredentialId(credential.getCredentialId());
        if (storedCredential == null) {
            var key = getBase64EncodedKey();
            var encryptedPassword = encryptionService.encryptValue(credential.getPassword(), key);
            var userId = getAuthenticatedUser().getUserId();
            var newCredential1 = new Credential(null, credential.getUrl(), credential.getUserName(), key, encryptedPassword, userId);
            credentialRepository.add(newCredential1);
        } else {
            update(credential);
        }
    }

    private void update(Credential credential) {
        var credentialToUpdate = credentialRepository.getByCredentialId(credential.getCredentialId());
        credentialToUpdate.setUrl(credential.getUrl());
        credentialToUpdate.setUserName(credential.getUserName());
        credentialToUpdate.setUserId(userService.getAuthenticatedUser().getUserId());
        credentialToUpdate.setKey(credential.getKey());
        credentialToUpdate.setPassword(encryptionService.encryptValue(credential.getPassword(), credential.getKey()));
        credentialRepository.updateCredential(credentialToUpdate);
    }

    public boolean deleteByCredentialIdAndUserId(int credentialId) {
        var user = userService.getAuthenticatedUser();
        return credentialRepository.deleteByCredentialIdAndUserId(user.getUserId(), credentialId);
    }
}
