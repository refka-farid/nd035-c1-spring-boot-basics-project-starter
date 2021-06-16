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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/home")
public class CredentialController {

    private final CredentialService credentialService;
    private final HomeAttributesModel homeAttributesModel;

    public CredentialController(FileTypeLoader fileTypeLoader, FileService fileService, NoteService noteService, CredentialService credentialService) {
        this.credentialService = credentialService;
        homeAttributesModel = new HomeAttributesModel(fileTypeLoader, fileService, noteService, credentialService);

    }

    @PostMapping("/credential/addOrEdit")
    public String addOrEditCredential(@ModelAttribute CredentialRequestDto credentialRequest, Model model, RedirectAttributes redirAttrs) {
        boolean hasCredentialId = credentialRequest.getCredentialId() != null;
        var isValidCredential = credentialService.isValidCredential(credentialRequest.toCredential(), credentialRequest.getUrl());
        if (hasCredentialId) {
            if (isValidCredential) {
                editCredential(credentialRequest);
                redirAttrs.addFlashAttribute("successCredential", "Your changes was successfully saved.");
            } else {
                redirAttrs.addFlashAttribute("errorCredential", "User already available!");
            }
        } else {
            if (isValidCredential) {
                addCredential(credentialRequest);
                redirAttrs.addFlashAttribute("successCredential", "Your New Credential was successfully added.");
            } else {
                redirAttrs.addFlashAttribute("errorCredential", "User already available!");
            }
        }
        homeAttributesModel.addAllAttributesModel(model);
        return "redirect:/home/";
    }

    private void addCredential(CredentialRequestDto credentialRequest) {
        credentialService.add(credentialRequest.toCredential());
    }

    private void editCredential(CredentialRequestDto credentialRequest) {
        credentialService.update(credentialRequest.toCredential());
    }

    @GetMapping("/credential/delete/{id}")
    public String getHomeCredentialDelete(Model model, @PathVariable("id") Integer id, RedirectAttributes redirAttrs) {
        var credential = credentialService.getByCredentialId(id);
        credentialService.deleteByCredentialIdAndUserId(credential.getCredentialId());
        redirAttrs.addFlashAttribute("successCredential", "Your credential was successfully deleted.");
        homeAttributesModel.addAllAttributesModel(model);
        return "redirect:/home/";
    }
}
