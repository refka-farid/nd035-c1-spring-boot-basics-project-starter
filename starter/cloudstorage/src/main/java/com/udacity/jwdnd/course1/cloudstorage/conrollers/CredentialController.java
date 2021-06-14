package com.udacity.jwdnd.course1.cloudstorage.conrollers;

import com.udacity.jwdnd.course1.cloudstorage.conrollers.util.HomeAttributesModel;
import com.udacity.jwdnd.course1.cloudstorage.models.CredentialRequestDto;
import com.udacity.jwdnd.course1.cloudstorage.services.credential.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.file.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.file.FileTypeLoader;
import com.udacity.jwdnd.course1.cloudstorage.services.note.NoteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/home")
public class CredentialController {

    private final CredentialService credentialService;
    private final HomeAttributesModel homeAttributesModel;

    public CredentialController(FileTypeLoader fileTypeLoader, FileService fileService, NoteService noteService, CredentialService credentialService) {
        this.credentialService = credentialService;
        homeAttributesModel = new HomeAttributesModel(fileTypeLoader,fileService,noteService,credentialService);

    }

    @PostMapping("/credential/addOrEdit")
    public String addOrEditCredential(@ModelAttribute CredentialRequestDto credentialRequest, Model model) {
        boolean hasCredentialId = credentialRequest.getCredentialId() != null;
        if (hasCredentialId) {
            editCredential(credentialRequest);
        } else {
            addCredential(credentialRequest);
        }
        homeAttributesModel.addAllAttributesModel(model);
        return "home";
    }

    private void addCredential(CredentialRequestDto credentialRequest) {
        credentialService.add(credentialRequest.toCredential());
    }

    private void editCredential(CredentialRequestDto credentialRequest) {
        credentialService.update(credentialRequest.toCredential());
    }

    @GetMapping("/credential/delete/{id}")
    public String getHomeCredentialDelete(Model model, @PathVariable("id") Integer id) {
        var credential = credentialService.getByCredentialId(id);
        credentialService.deleteByCredentialIdAndUserId(credential.getCredentialId());
        homeAttributesModel.addAllAttributesModel(model);
        return "home";
    }
}
