package com.udacity.jwdnd.course1.cloudstorage.repositories;

import com.udacity.jwdnd.course1.cloudstorage.entities.Note;
import com.udacity.jwdnd.course1.cloudstorage.mappers.NoteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

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

    public boolean delete1(Note note) {
        logger.trace("delete1 " + note);
        boolean isDeleted = mapper.deleteByNoteTitle2(note.getNoteTitle());
        return isDeleted;
    }

    public boolean delete2(Note note) {
        logger.trace("delete2" + note);
        int rows = mapper.deleteByNoteTitle(note.getNoteTitle());
        return rows > 0;
    }

    public boolean deleteAll() {
        logger.trace("deleteAll");
        boolean allDeleted = mapper.deleteAll();
        return allDeleted;
    }

    public boolean deleteNoteByNoteTitleAndUserId(int userId, String noteTitle) {
        logger.trace("deletenoteByNoteTitleAndUserId " + userId + noteTitle);
        boolean IsNoteDeleted = mapper.deleteNoteByNoteTitleAndUserId(userId, noteTitle);
        return IsNoteDeleted;
    }

    public Note getByNoteId(int noteId) {
        return mapper.getByNoteId(noteId);
    }

    public Note getNoteByNoteTileAndUserId(Integer userId, String noteTitle) {
        logger.trace("getnoteBynoteTitleAndUserId " + userId + noteTitle);
        return mapper.getNoteByNoteTitleAndUserId(userId, noteTitle);
    }

    public Note updateOrNull(Note note) {
        logger.trace("updateOrNull " + note);
        boolean isUpdated = mapper.updateNote(note);
        if (isUpdated) {
            return mapper.getByNoteTitle(note.getNoteTitle());
        } else {
            return null;
        }
    }

    public Note addOrUpdate(Note note) {
        logger.trace("addOrUpdate " + note);
        Note storedNote = mapper.getByNoteTitle(note.getNoteTitle());
        if (storedNote == null) {
            mapper.add(note);
        } else {
            mapper.updateNote(note);
        }
        return mapper.getByNoteTitle(note.getNoteTitle());
    }

}
