package com.udacity.jwdnd.course1.cloudstorage.repositories;

import com.udacity.jwdnd.course1.cloudstorage.entities.Note;
import com.udacity.jwdnd.course1.cloudstorage.mappers.NoteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class NoteRepository {

    private final Logger logger = LoggerFactory.getLogger(NoteRepository.class);

    private final NoteMapper mapper;

    public NoteRepository(NoteMapper mapper) {
        this.mapper = mapper;
    }

    public Note getByNoteTitle(String noteTitle) {
        return mapper.getByNoteTitle(noteTitle);
    }

    public List<Note> getAllByUserId(int userId) {
        return mapper.getAll(userId);
    }

    public boolean add(Note note) {
        logger.trace("add note " + note);
        int id = mapper.add(note);
        return id > 0;
    }

    public boolean update(Note note) {
        logger.trace("add note " + note);
       return mapper.updateNote(note);
    }

    public boolean deleteAll() {
        logger.trace("deleteAll");
        return mapper.deleteAll();
    }

    public boolean deleteNoteByNoteTitleAndUserId(int userId, int noteId) {
        logger.trace("deletenoteByNoteTitleAndUserId " + userId + noteId);
        return mapper.deleteNoteByNoteIdAndUserId(userId, noteId);
    }

    public Note getByNoteId(int noteId) {
        return mapper.getByNoteId(noteId);
    }

    public Note getNoteByNoteTileAndUserId(Integer userId, String noteTitle) {
        logger.trace("getnoteBynoteTitleAndUserId " + userId + noteTitle);
        return mapper.getNoteByNoteTitleAndUserId(userId, noteTitle);
    }

    public List<Note> getByNoteTitleAndUserId(Integer userId, String noteTitle) {
        return mapper.getAllByNoteTitle(userId, noteTitle);
    }

    public List<Note> getByNoteDescriptionAndUserId(Integer userId, String noteDescription) {
        return mapper.getAllByNoteDescription(userId, noteDescription);

    }

    public boolean isValidToBeAdded(String noteTitle, String noteDescription, int userId) {
        var allNotes = getAllByUserId(userId);
        var notesWithTitleExist = getByNoteTitleAndUserId(userId, noteTitle);
        var notesWithDescriptionExist = getByNoteDescriptionAndUserId(userId, noteDescription);
        if (allNotes.isEmpty()) {
            return true;
        } else {
            return notesWithTitleExist.isEmpty() && notesWithDescriptionExist.isEmpty();
        }
    }

    public boolean isValidToBeEdited(Note note, String noteTitle, String noteDescription, int userId) {
        var allNotes = new ArrayList<>(getAllByUserId(userId));
        var notesWithTitleExist = new ArrayList<>(getByNoteTitleAndUserId(userId, noteTitle));
        var notesWithDescriptionExist = new ArrayList<>(getByNoteDescriptionAndUserId(userId, noteDescription));
        var isValid = false;

        if (notesWithTitleExist.isEmpty() && notesWithDescriptionExist.isEmpty()) {
            isValid = true;
        } else if (allNotes.size() == 1) {
            isValid = true;
        } else if (notesWithTitleExist.isEmpty() && (!notesWithDescriptionExist.isEmpty())) {
            var wantedNoteList = new ArrayList<Note>();
            for (Note item : notesWithDescriptionExist) {
                if (item.getNoteId().equals(note.getNoteId())) {
                    wantedNoteList.add(item);
                }
            }
            notesWithDescriptionExist.removeAll(wantedNoteList);
            isValid = notesWithDescriptionExist.isEmpty();
        } else if (notesWithDescriptionExist.isEmpty() && (!notesWithTitleExist.isEmpty())) {
            var wantedNoteList = new ArrayList<Note>();
            for (Note item : notesWithTitleExist) {
                if (item.getNoteId().equals(note.getNoteId())) {
                    wantedNoteList.add(item);
                }
            }
            notesWithTitleExist.removeAll(wantedNoteList);
            isValid = notesWithTitleExist.isEmpty();
        }
        return isValid;
    }

    public boolean addOrUpdate2(Note note, int userId) {
        logger.trace("addOrUpdate " + note);
        Note storedNote = mapper.getByNoteId(note.getNoteId());
        if (storedNote == null) {
            if (isValidToBeAdded(note.getNoteTitle(), note.getNoteDescription(), userId)) {
                mapper.add(note);
                return true;
            } else {
                return false;
            }
        } else {
            if (isValidToBeEdited(note, note.getNoteTitle(), note.getNoteDescription(), userId)) {
                mapper.updateNote(note);
                return true;
            } else {
                return false;
            }
        }
    }

    public Note addOrUpdate(Note note) {
        logger.trace("addOrUpdate " + note);
        Note storedNote = mapper.getByNoteId(note.getNoteId());
        if (storedNote == null) {
            mapper.add(note);
        } else {
            mapper.updateNote(note);
        }
        return mapper.getByNoteId(note.getNoteId());
    }

}
