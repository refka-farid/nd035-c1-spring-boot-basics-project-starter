package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.entities.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {

    @Select("SELECT * FROM CREDENTIALS WHERE url = #{url} LIMIT 1")
    Credential getByUrl(String url);

    @Select("SELECT * FROM CREDENTIALS WHERE credentialid = #{credentialId}")
    Credential getByCredentialId(int credentialId);

    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userId} AND url = #{url}")
    List<Credential> getByUrlAndUserId(Integer userId, String url);

    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userId} ")
    List<Credential> getAll(int userId);

    @Insert("INSERT INTO CREDENTIALS ( credentialid ,url, username , key , password , userid) "
            + " VALUES ( #{credentialId},#{url}, #{userName}, #{key},  #{password}, #{userId} ) ")
    @Options(useGeneratedKeys = true, keyColumn = "credentialid", keyProperty = "credentialId")
    int add(Credential credential);

    @Update("UPDATE CREDENTIALS SET url =#{url}, username =#{userName}, key =#{key}, password =#{password}, userid =#{userId} WHERE credentialid =#{credentialId}")
    boolean updateCredential(Credential credential);

    @Delete("DELETE FROM CREDENTIALS WHERE url = #{url}")
    int deleteByUrl(String url);

    @Delete("DELETE FROM CREDENTIALS WHERE url = #{url}")
    boolean deleteByUrl2(String url);

    @Delete("DELETE FROM CREDENTIALS")
    boolean deleteAll();

    @Delete("DELETE FROM CREDENTIALS WHERE userid = #{userId} AND credentialid = #{credentialId}")
    boolean deleteByCredentialIdAndUserId(int userId, int credentialId);

}
