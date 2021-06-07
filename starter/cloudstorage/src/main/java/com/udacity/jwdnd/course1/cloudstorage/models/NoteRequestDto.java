package com.udacity.jwdnd.course1.cloudstorage.models;

import com.udacity.jwdnd.course1.cloudstorage.entities.Note;

import java.util.Objects;

public class NoteRequestDto {
    private Integer noteId;
    private String noteTitle;
    private String noteDescription;


    public NoteRequestDto() {
    }

    public NoteRequestDto(Integer noteId, String noteTitle, String noteDescription) {
        this.noteId = noteId;
        this.noteTitle = noteTitle;
        this.noteDescription = noteDescription;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteDescription() {
        return noteDescription;
    }

    public void setNoteDescription(String noteDescription) {
        this.noteDescription = noteDescription;
    }

    public Integer getNoteId() {
        return noteId;
    }

    public void setNoteId(Integer noteId) {
        this.noteId = noteId;
    }

    public  Note toNote(Integer userId) {
        return new Note(noteId,noteTitle, noteDescription, userId);
    }

    public Note toNote() {
        return new Note(noteId,noteTitle, noteDescription, null);
    }

    public static NoteRequestDto fromNote(Note note) {
        return new NoteRequestDto(note.getNoteId(), note.getNoteTitle(), note.getNoteDescription());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NoteRequestDto that = (NoteRequestDto) o;
        return noteId.equals(that.noteId) && noteTitle.equals(that.noteTitle) && noteDescription.equals(that.noteDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(noteId, noteTitle, noteDescription);
    }
}
