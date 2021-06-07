package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.entities.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {

    @Select("SELECT * FROM FILES WHERE filename = #{fileName} LIMIT 1")
    File getByFileName(String fileName);

    @Select("SELECT * FROM FILES WHERE userid = #{userId} ")
    List<File> getAll(int userId);

    @Select("SELECT * FROM FILES WHERE fileid = #{fileId}")
    File getByFileId(int fileId);

    @Select("SELECT * FROM FILES WHERE userid = #{userId} AND filename = #{fileName}")
    File getFileByFileNameAndUserId(Integer userId, String fileName);

    @Insert("INSERT INTO FILES ( filename ,contenttype, filesize , filedata , userid ) "
            + " VALUES ( #{fileName},#{contentType}, #{fileSize}, #{fileData},  #{userId} ) ")
    @Options(useGeneratedKeys = true, keyColumn = "fileid", keyProperty = "fileId")
    int add(File file);

    @Delete("DELETE FROM FILES WHERE filename = #{fileName}")
    int deleteByFileName(String fileName);

    @Delete("DELETE FROM FILES WHERE filename = #{fileName}")
    boolean deleteByFileName2(String fileName);

    @Delete("DELETE FROM FILES")
    boolean deleteAll();

    @Delete("DELETE FROM FILES WHERE userid = #{userId} AND filename = #{fileName}")
    boolean deleteFileByFileNameAndUserId(int userId, String fileName);

}
