package com.udacity.jwdnd.course1.cloudstorage.services.note;

import com.udacity.jwdnd.course1.cloudstorage.entities.Note;
import com.udacity.jwdnd.course1.cloudstorage.repositories.NoteRepository;
import com.udacity.jwdnd.course1.cloudstorage.services.signup.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private final NoteRepository noteRepository;
    private final UserService userService;

    public NoteService(NoteRepository noteRepository, UserService userService) {
        this.noteRepository = noteRepository;
        this.userService = userService;
    }

    public List<Note> getAllAuthenticatedUserNote() {
        var user = userService.getAuthenticatedUser();
        return noteRepository.getAllByUserId(user.getUserId());
    }

    public boolean deleteNoteByUserNameAndUserId(String noteTitle) {
        var user = userService.getAuthenticatedUser();
        return noteRepository.deleteNoteByNoteTitleAndUserId(user.getUserId(), noteTitle);
    }

    public Note getNoteByNoteId(int noteId) {
        return noteRepository.getByNoteId(noteId);
    }

    public Note getNoteByNoteTitleAndUserId(int noteId) {
        var user = userService.getAuthenticatedUser();
        var noteTitle = noteRepository.getByNoteId(noteId).getNoteTitle();
        return noteRepository.getNoteByNoteTileAndUserId(user.getUserId(), noteTitle);
    }

    public boolean isNoteTitleAlreadyExist(String noteTitle) {
        var user = userService.getAuthenticatedUser();
        var note = noteRepository.getNoteByNoteTileAndUserId(user.getUserId(), noteTitle);
        return note != null;
    }

    public boolean addNoteFile(Note note) {
        var user = userService.getAuthenticatedUser();
        var myNote = new Note();
        myNote.setUserId(user.getUserId());
        myNote.setNoteTitle(note.getNoteTitle());
        myNote.setNoteDescription(note.getNoteDescription());
        if (!isNoteTitleAlreadyExist(note.getNoteTitle())) {
            return noteRepository.add(note);
        } else {
            return false;
        }
    }
}
