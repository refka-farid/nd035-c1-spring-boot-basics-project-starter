package com.udacity.jwdnd.course1.cloudstorage.conrollers;

import com.udacity.jwdnd.course1.cloudstorage.conrollers.util.HomeAttributesModel;
import com.udacity.jwdnd.course1.cloudstorage.models.NoteRequestDto;
import com.udacity.jwdnd.course1.cloudstorage.services.credential.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.file.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.file.FileTypeLoader;
import com.udacity.jwdnd.course1.cloudstorage.services.note.NoteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/home")
public class NoteController {
    private final NoteService noteService;
    private final HomeAttributesModel homeAttributesModel;

    public NoteController(FileTypeLoader fileTypeLoader, FileService fileService, NoteService noteService, CredentialService credentialService) {
        this.noteService = noteService;
        homeAttributesModel = new HomeAttributesModel(fileTypeLoader,fileService,noteService,credentialService);
    }

    @PostMapping("/note/addOrEdit")
    public String addOrEditNote(@ModelAttribute NoteRequestDto noteRequest, Model model) {
        boolean hasNoteId = noteRequest.getNoteId() != null;
        if (hasNoteId) {
            editNote(noteRequest);
        } else {
            addNote(noteRequest);
        }
        homeAttributesModel.addAllAttributesModel(model);
        return "home";
    }

    private void addNote(NoteRequestDto noteRequest) {
        noteService.addNote(noteRequest.toNote());
    }

    private void editNote(NoteRequestDto noteRequest) {
        noteService.update(noteRequest.toNote());
    }

    @GetMapping("/note/delete/{id}")
    public String getHomeNoteDelete(Model model, @PathVariable("id") Integer id) {
        var note = noteService.getNoteByNoteId(id);
        noteService.deleteNoteByNoteIdAndUserId(note.getNoteId());
        homeAttributesModel.addAllAttributesModel(model);
        return "home";
    }

}
