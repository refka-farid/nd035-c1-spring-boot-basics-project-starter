package com.udacity.jwdnd.course1.cloudstorage.services.note;

import com.udacity.jwdnd.course1.cloudstorage.entities.Note;
import com.udacity.jwdnd.course1.cloudstorage.repositories.NoteRepository;
import com.udacity.jwdnd.course1.cloudstorage.services.signup.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private final UserService userService;
    private final NoteRepository noteRepository;

    public NoteService(UserService userService, NoteRepository noteRepository) {
        this.userService = userService;
        this.noteRepository = noteRepository;
    }

    public List<Note> getAllAuthenticatedUserNote() {
        var user = userService.getAuthenticatedUser();
        return noteRepository.getAllByUserId(user.getUserId());
    }

    public Note getNoteByNoteId(int noteId) {
        return noteRepository.getByNoteId(noteId);
    }

    @Deprecated
    Note getNoteByNoteTitleAndUserId(int noteId) {
        var user = userService.getAuthenticatedUser();
        var noteTitle = noteRepository.getByNoteId(noteId).getNoteTitle();
        return noteRepository.getNoteByNoteTileAndUserId(user.getUserId(), noteTitle);
    }

    public boolean addNote(Note note) {
        var user = userService.getAuthenticatedUser();
        if (noteRepository.isValidToBeAdded(note.getNoteTitle(), note.getNoteDescription(), user.getUserId())) {
            note.setUserId(user.getUserId());
            note.setNoteTitle(note.getNoteTitle());
            note.setNoteDescription(note.getNoteDescription());
            return noteRepository.add(note);
        } else {
            return false;
        }
    }

    public Note addOrUpdate(Note note) {
        return noteRepository.addOrUpdate(note);
    }

    public boolean update(Note note) {
        var user = userService.getAuthenticatedUser();
        if (noteRepository.isValidToBeEdited(note, note.getNoteTitle(), note.getNoteDescription(), user.getUserId())) {
            var noteToUpdate = getNoteByNoteId(note.getNoteId());
            noteToUpdate.setNoteTitle(note.getNoteTitle());
            noteToUpdate.setNoteDescription(note.getNoteDescription());
            noteToUpdate.setUserId(userService.getAuthenticatedUser().getUserId());
            var updatedNote = addOrUpdate(noteToUpdate);
            return updatedNote != null;
        } else {
            return false;
        }
    }

    public boolean deleteNoteByNoteIdAndUserId(int noteId) {
        var user = userService.getAuthenticatedUser();
        return noteRepository.deleteNoteByNoteTitleAndUserId(user.getUserId(), noteId);
    }

    @Deprecated
    boolean isNoteTitleAlreadyExist(String noteTitle) {
        var user = userService.getAuthenticatedUser();
        var note = noteRepository.getNoteByNoteTileAndUserId(user.getUserId(), noteTitle);
        return note != null;
    }
}
