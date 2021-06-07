package com.udacity.jwdnd.course1.cloudstorage.repositories;

import com.udacity.jwdnd.course1.cloudstorage.entities.Note;
import com.udacity.jwdnd.course1.cloudstorage.entities.User;
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

    private NoteRepository sut;

    private Note myTestedNote;
    private User user;

    @BeforeEach
    void setUp() {
        sut = new NoteRepository(mapperNote);
        user = new User(null, "lucie", "HIxi7PbCRU9uIyET6sdGEg==", "8H7jlDi3a2iPiu9ZI1+krA==", "lucie", "Babier");
        mapperUser.addUser(user);
        myTestedNote = new Note(null, "myFirstNote", "MyNoteDescription", user.getUserId());
    }

    @AfterEach
    void tearDown() {
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
    void delete1Test() {
        mapperNote.add(myTestedNote);
        var result = sut.delete1(myTestedNote);
        assertThat(result).isTrue();
    }

    @Test
    void delete2Test() {
        mapperNote.add(myTestedNote);
        var result = sut.delete2(myTestedNote);
        assertThat(result).isTrue();
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
        var result = sut.deleteNoteByNoteTitleAndUserId(storedUser.getUserId(), "myFirstNote");
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
    void updateOrNullTest() {
        var myTestedNote2 = new Note(null, "myFirstNote", "MyNoteDescription2_modified", user.getUserId());
        mapperNote.add(myTestedNote);
        var result = sut.updateOrNull(myTestedNote2);
        assertThat(result.getNoteDescription()).isEqualTo("MyNoteDescription2_modified");
    }

    @Test
    void addOrUpdateTest() {
        var myTestedNote2 = new Note(null, "myFirstNote", "myFirstNote MyNoteDescription2_modified", user.getUserId());
        var myTestedNote3 = new Note(null, "mySecondNote", "mySecondNote MyNoteDescription", user.getUserId());
        mapperNote.add(myTestedNote);
        var result1 = sut.addOrUpdate(myTestedNote2);
        var result2 = sut.addOrUpdate(myTestedNote3);
        var list = sut.getAllByUserId(user.getUserId());
        assertThat(result1.getNoteDescription()).isEqualTo("myFirstNote MyNoteDescription2_modified");
        assertThat(result2.getNoteDescription()).isEqualTo("mySecondNote MyNoteDescription");
        assertThat(list.size()).isEqualTo(2);
    }
}