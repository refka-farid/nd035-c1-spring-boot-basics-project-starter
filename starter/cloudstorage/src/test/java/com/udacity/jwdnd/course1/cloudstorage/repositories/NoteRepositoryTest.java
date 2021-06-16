package com.udacity.jwdnd.course1.cloudstorage.repositories;

import com.udacity.jwdnd.course1.cloudstorage.entities.Note;
import com.udacity.jwdnd.course1.cloudstorage.entities.User;
import com.udacity.jwdnd.course1.cloudstorage.mappers.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mappers.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mappers.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.mappers.UserMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.inject.Inject;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles(profiles = {"dev"})
class NoteRepositoryTest {

    @Inject
    private NoteMapper mapperNote;

    @Inject
    private UserMapper mapperUser;

    @Inject
    private FileMapper mapperFile;

    @Inject
    private CredentialMapper mapperCredential;

    private NoteRepository sut;

    private Note myTestedNote;
    private Note myTestedNote2;
    private User user;

    @BeforeEach
    void setUp() {
        sut = new NoteRepository(mapperNote);
        user = new User(null, "lucie", "HIxi7PbCRU9uIyET6sdGEg==", "8H7jlDi3a2iPiu9ZI1+krA==", "lucie", "Babier");
        mapperUser.addUser(user);
        myTestedNote = new Note(null, "myFirstNote", "MyNoteDescription", user.getUserId());
        myTestedNote2 = new Note(null, "mySecondNote", "MySecondNoteDescription", user.getUserId());
    }

    @AfterEach
    void tearDown() {
        mapperFile.deleteAll();
        mapperCredential.deleteAll();
        mapperNote.deleteAll();
        mapperUser.deleteAll();
    }

    @Test
    void getByNoteTitleTest() {
        var result = sut.add(myTestedNote);
        assertThat(result).isTrue();

        var result2 = sut.getByNoteTitle("myFirstNote");
        assertThat(result2).isEqualTo(myTestedNote);
    }

    @Test
    void getAllByUserIdTest() {
        mapperNote.add(myTestedNote);
        var notes = new ArrayList<Note>();
        notes.add(myTestedNote);
        var result = sut.getAllByUserId(user.getUserId());
        assertThat(result).isEqualTo(notes);
    }

    @Test
    void deleteAllTest() {
        mapperNote.add(myTestedNote);
        var result = sut.deleteAll();
        assertThat(result).isTrue();
    }

    @Test
    void deleteNoteByNoteTitleAndUserIdTest() {
        var storedUser = mapperUser.getUserByUserName("lucie");
        mapperNote.add(myTestedNote);
        var note = mapperNote.getByNoteTitle(myTestedNote.getNoteTitle());
        var result = sut.deleteNoteByNoteTitleAndUserId(storedUser.getUserId(), note.getNoteId());
        assertThat(result).isTrue();
    }

    @Test
    void getByNoteIdTest() {
        mapperNote.add(myTestedNote);
        var result = sut.getByNoteId(myTestedNote.getNoteId());
        assertThat(result).isEqualTo(myTestedNote);
    }

    @Test
    void getNoteByNoteTileAndUserIdTest() {
        mapperNote.add(myTestedNote);
        var result = sut.getNoteByNoteTileAndUserId(user.getUserId(), myTestedNote.getNoteTitle());
        assertThat(result).isEqualTo(myTestedNote);
    }

    @Test
    void isValidForAdd1() {
        var result = sut.isValidToBeAdded("myFirstNote", "MyNoteDescription", user.getUserId());
        assertThat(result).isTrue();
    }

    @Test
    void isValidForAdd2() {
        mapperNote.add(myTestedNote);
        var result2 = sut.isValidToBeAdded("myFirstNote", "MyNoteDescription", user.getUserId());
        assertThat(result2).isFalse();
    }

    @Test
    void isValidForAdd3() {
        mapperNote.add(myTestedNote);
        var result2 = sut.isValidToBeAdded("myFirstNote3", "MyNoteDescription", user.getUserId());
        assertThat(result2).isFalse();
    }

    @Test
    void isValidForAdd4() {
        mapperNote.add(myTestedNote);
        var result2 = sut.isValidToBeAdded("myFirstNote", "MyNoteDescriptionDiff", user.getUserId());
        assertThat(result2).isFalse();
    }

    @Test
    void isValidForEdit1() {
        mapperNote.add(myTestedNote);
        var result = sut.isValidToBeEdited(myTestedNote,"myFirstNote", "MyNoteDescription", user.getUserId());
        assertThat(result).isTrue();
    }

//    myTestedNote = new Note(null, "myFirstNote", "MyNoteDescription", user.getUserId());
//    myTestedNote2 = new Note(null, "mySecondNote", "MySecondNoteDescription", user.getUserId());

    @Test
    void isValidForEdit2() {
        mapperNote.add(myTestedNote);
        mapperNote.add(myTestedNote2);
        var result = sut.isValidToBeEdited(myTestedNote2,"myEditedNote", "bla bla bla", user.getUserId());
        assertThat(result).isTrue();
    }

    @Test
    void isValidForEdit3() {
        mapperNote.add(myTestedNote);
        mapperNote.add(myTestedNote2);
        var result = sut.isValidToBeEdited(myTestedNote, myTestedNote.getNoteTitle(), "bla bla bla", user.getUserId());
        assertThat(result).isTrue();
    }

    @Test
    void isValidForEdit4() {
        mapperNote.add(myTestedNote);
        mapperNote.add(myTestedNote2);
        var result = sut.isValidToBeEdited(myTestedNote2,"bla bla", "MySecondNoteDescription", user.getUserId());
        assertThat(result).isTrue();
    }

    @Test
    void addOrUpdate2Test() {
        myTestedNote2 = new Note(100, "mySecondNote", "MySecondNoteDescription", user.getUserId());
        mapperNote.add(myTestedNote);
        var result = sut.addOrUpdate2(myTestedNote,user.getUserId());
        assertThat(result).isTrue();
    }

    @Test
    void addOrUpdate3Test() {
        myTestedNote2 = new Note(100, "mySecondNote", "MySecondNoteDescription", user.getUserId());
        var myTestedNote3 = new Note(111, "myThirdNote", "MySecondNoteDescription", user.getUserId());
        mapperNote.add(myTestedNote);
        mapperNote.add(myTestedNote2);
        var result = sut.addOrUpdate2(myTestedNote3,user.getUserId());
        assertThat(result).isFalse();
    }
}
