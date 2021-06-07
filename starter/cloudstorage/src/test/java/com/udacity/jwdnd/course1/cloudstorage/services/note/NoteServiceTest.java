package com.udacity.jwdnd.course1.cloudstorage.services.note;

import com.udacity.jwdnd.course1.cloudstorage.entities.Note;
import com.udacity.jwdnd.course1.cloudstorage.entities.User;
import com.udacity.jwdnd.course1.cloudstorage.repositories.NoteRepository;
import com.udacity.jwdnd.course1.cloudstorage.services.signup.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class NoteServiceTest {

    private NoteService sut;
    private NoteRepository noteRepositoryMock;
    private UserService userServiceMock;

    @BeforeEach
    void setUp() {
        noteRepositoryMock = mock(NoteRepository.class);
        userServiceMock = mock(UserService.class);
        sut = new NoteService(noteRepositoryMock, userServiceMock);
    }

    @Test
    void getAllAuthenticatedUserNoteTest() {
        when(userServiceMock.getAuthenticatedUser()).thenReturn(
                new User(1, "SALAH", "HIxi7PbCRU9uIyET6sdGEg==", "8H7jlDi3a2iPiu9ZI1+krA==", "Salah", "Yousef")
        );
        sut.getAllAuthenticatedUserNote();
        verify(userServiceMock).getAuthenticatedUser();
        verify(noteRepositoryMock).getAllByUserId(1);
    }

    @Test
    void deleteNoteByUserNameAndUserIdTest() {
        when(userServiceMock.getAuthenticatedUser()).thenReturn(
                new User(1, "SALAH", "HIxi7PbCRU9uIyET6sdGEg==", "8H7jlDi3a2iPiu9ZI1+krA==", "Salah", "Yousef")
        );
        sut.deleteNoteByUserNameAndUserId("myNote");
        verify(userServiceMock).getAuthenticatedUser();
        verify(noteRepositoryMock).deleteNoteByNoteTitleAndUserId(1, "myNote");
    }

    @Test
    void getNoteByNoteIdTest() {
        sut.getNoteByNoteId(10);
        verify(noteRepositoryMock).getByNoteId(10);
    }

    @Test
    void getNoteByNoteTitleAndUserIdTest() {
        when(userServiceMock.getAuthenticatedUser()).thenReturn(
                new User(1, "SALAH", "HIxi7PbCRU9uIyET6sdGEg==", "8H7jlDi3a2iPiu9ZI1+krA==", "Salah", "Yousef")
        );
        when(noteRepositoryMock.getByNoteId(100)).thenReturn(
                new Note(100, "mySecondNote", "mySecondNote MyNoteDescription", 1)
        );
        sut.getNoteByNoteTitleAndUserId(100);
        verify(userServiceMock).getAuthenticatedUser();
        verify(noteRepositoryMock).getByNoteId(100);
        verify(noteRepositoryMock).getNoteByNoteTileAndUserId(1, "mySecondNote");
    }

    @Test
    void isNoteTitleAlreadyExistTest() {
        when(userServiceMock.getAuthenticatedUser()).thenReturn(
                new User(1, "SALAH", "HIxi7PbCRU9uIyET6sdGEg==", "8H7jlDi3a2iPiu9ZI1+krA==", "Salah", "Yousef")
        );
        when(noteRepositoryMock.getNoteByNoteTileAndUserId(1, "mySecondNote")).thenReturn(
                new Note(100, "mySecondNote", "mySecondNote MyNoteDescription", 1)
        );
        sut.isNoteTitleAlreadyExist("mySecondNote");
    }

    @Test
    void addNoteFileTest() {
        when(userServiceMock.getAuthenticatedUser()).thenReturn(
                new User(1, "SALAH", "HIxi7PbCRU9uIyET6sdGEg==", "8H7jlDi3a2iPiu9ZI1+krA==", "Salah", "Yousef")
        );
        var note = new Note(100, "mySecondNote", "mySecondNote MyNoteDescription", 1);
        sut.addNoteFile(note);
        verify(userServiceMock, times(2)).getAuthenticatedUser();
        verify(noteRepositoryMock).add(argThat(argument ->
                argument instanceof Note
        ));
    }
}
