package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.entities.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {

    @Select("SELECT * FROM NOTES WHERE notetitle = #{noteTitle} LIMIT 1")
    Note getByNoteTitle(String noteTitle);

    @Select("SELECT * FROM NOTES WHERE noteid = #{noteId}")
    Note getByNoteId(int noteId);

    @Select("SELECT * FROM NOTES WHERE userid = #{userId} AND notetitle = #{noteTitle}")
    Note getNoteByNoteTitleAndUserId(Integer userId, String noteTitle);

////    @Select("SELECT * FROM NOTES WHERE userid = #{userId} AND notetitle = #{noteTitle} AND notedescription = #{noteDescription}")
//    @Select("SELECT * FROM NOTES WHERE userid = #{userId} AND notetitle = #{noteTitle} OR notedescription = #{noteDescription}")
//    List<Note> getAllByNoteTitleOrNoteDescription(Integer userId, String noteTitle, String noteDescription);
//
//
//        @Select("SELECT * FROM NOTES WHERE userid = #{userId} AND notetitle = #{noteTitle} AND notedescription = #{noteDescription}")
//    List<Note> getAllByNoteTitleAndNoteDescription(Integer userId, String noteTitle, String noteDescription);



    @Select("SELECT * FROM NOTES WHERE userid = #{userId} AND notedescription = #{noteDescription} ")
    List<Note> getAllByNoteDescription(int userId,String noteDescription);

    @Select("SELECT * FROM NOTES WHERE userid = #{userId} AND notetitle = #{noteTitle}")
    List<Note> getAllByNoteTitle(int userId,String noteTitle);

    @Select("SELECT * FROM NOTES WHERE userid = #{userId} ")
    List<Note> getAll(int userId);

    @Delete("DELETE FROM NOTES")
    boolean deleteAll();

    @Delete("DELETE FROM NOTES WHERE userid = #{userId} AND noteid = #{noteId}")
    boolean deleteNoteByNoteIdAndUserId(int userId, int noteId);

    @Insert("INSERT INTO NOTES ( noteid ,notetitle, notedescription , userid ) "
            + " VALUES ( #{noteId},#{noteTitle}, #{noteDescription}, #{userId} ) ")
    @Options(useGeneratedKeys = true, keyColumn = "noteid", keyProperty = "noteId")
    int add(Note note);

    @Update("UPDATE NOTES SET notetitle =#{noteTitle}, notedescription =#{noteDescription}, userid =#{userId} WHERE noteid =#{noteId}")
    boolean updateNote(Note note);
}
