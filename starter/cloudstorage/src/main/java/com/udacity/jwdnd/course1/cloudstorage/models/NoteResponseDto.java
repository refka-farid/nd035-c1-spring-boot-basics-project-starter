package com.udacity.jwdnd.course1.cloudstorage.models;

import com.udacity.jwdnd.course1.cloudstorage.entities.Note;

import java.util.Objects;

public class NoteResponseDto {

    private Integer noteId;
    private String noteTitle;
    private String noteDescription;
    private Integer userId;

    public NoteResponseDto(Integer noteId, String noteTitle, String noteDescription, Integer userId) {
        this.noteId = noteId;
        this.noteTitle = noteTitle;
        this.noteDescription = noteDescription;
        this.userId = userId;
    }

    public Integer getNoteId() {
        return noteId;
    }

    public void setNoteId(Integer noteId) {
        this.noteId = noteId;
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public static NoteResponseDto fromNote(Note note) {
        return new NoteResponseDto(note.getNoteId(), note.getNoteTitle(), note.getNoteDescription(), note.getUserId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NoteResponseDto that = (NoteResponseDto) o;
        return noteTitle.equals(that.noteTitle) && noteDescription.equals(that.noteDescription) && userId.equals(that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(noteId, noteTitle, noteDescription, userId);
    }
}
