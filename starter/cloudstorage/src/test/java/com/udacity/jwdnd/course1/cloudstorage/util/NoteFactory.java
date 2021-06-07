package com.udacity.jwdnd.course1.cloudstorage.util;

import com.udacity.jwdnd.course1.cloudstorage.entities.Note;

import java.util.ArrayList;
import java.util.List;

public class NoteFactory {
    public static List<Note> createNoteList() {
        var note = new Note(null, "myNoteTitle", "myNoteDescription", 1);
        var list = new ArrayList<Note>();
        list.add(note);
        return list;
    }

    public static Note createNoteWithNoteId() {
        return new Note(100, "myNoteTitle", "myNoteDescription", 1);
    }

    public static Note createNoteWithoutNoteId() {
        return new Note(null, "myNoteTitle", "myNoteDescription", 1);
    }
}
