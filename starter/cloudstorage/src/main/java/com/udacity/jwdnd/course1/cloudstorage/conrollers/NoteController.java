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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String addOrEditNote(@ModelAttribute NoteRequestDto noteRequest, Model model, RedirectAttributes redirAttrs) {
        if (noteRequest.getNoteDescription().length()>= 1000){
            redirAttrs.addFlashAttribute("error", "Sorry, but Your Note Description was to large !");
            return "redirect:/home/";
        }
        boolean hasNoteId = noteRequest.getNoteId() != null;
        if (hasNoteId) {
            editNote(noteRequest);
            redirAttrs.addFlashAttribute("success", "Your changes was successfully saved.");
        } else {
            addNote(noteRequest);
            redirAttrs.addFlashAttribute("success", "Your New Note was successfully added.");
        }
        homeAttributesModel.addAllAttributesModel(model);
        return "redirect:/home/";
    }

    private boolean addNote(NoteRequestDto noteRequest) {
       return  noteService.addNote(noteRequest.toNote());
    }

    private boolean editNote(NoteRequestDto noteRequest) {
        return noteService.update(noteRequest.toNote());
    }

    @GetMapping("/note/delete/{id}")
    public String getHomeNoteDelete(Model model, @PathVariable("id") Integer id, RedirectAttributes redirAttrs) {
        var note = noteService.getNoteByNoteId(id);
        noteService.deleteNoteByNoteIdAndUserId(note.getNoteId());
        homeAttributesModel.addAllAttributesModel(model);
        redirAttrs.addFlashAttribute("success", "Your Note was successfully deleted.");
        return "redirect:/home/";
    }

}
