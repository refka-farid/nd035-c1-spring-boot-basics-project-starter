package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.entities.File;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.javassist.bytecode.ByteArray;
import org.apache.ibatis.type.BlobTypeHandler;
import org.apache.ibatis.type.ByteTypeHandler;
import org.apache.ibatis.type.JdbcType;

@Mapper
public interface FileMapper {

    @Select("SELECT * FROM FILES WHERE filename = #{fileName}")
    @Results({
            @Result(property = "fileData", column = "filedata",
                    typeHandler = BlobTypeHandler.class,
                    javaType = ByteArray.class,
                    jdbcType = JdbcType.BLOB)
    })
    File getByFileName(String fileName);

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

}
