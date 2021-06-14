package com.udacity.jwdnd.course1.cloudstorage.conrollers;

import com.udacity.jwdnd.course1.cloudstorage.conrollers.util.HomeAttributesModel;
import com.udacity.jwdnd.course1.cloudstorage.services.credential.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.file.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.file.FileTypeLoader;
import com.udacity.jwdnd.course1.cloudstorage.services.note.NoteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {
    private final HomeAttributesModel homeAttributesModel;

    public HomeController(FileTypeLoader fileTypeLoader, FileService fileService, NoteService noteService, CredentialService credentialService) {
        homeAttributesModel = new HomeAttributesModel(fileTypeLoader, fileService, noteService, credentialService);
    }

    @GetMapping()
    public String getHomePage(Model model) {
        homeAttributesModel.addAllAttributesModel(model);
        return "home";
    }
}
