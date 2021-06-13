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
