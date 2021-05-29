package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM USERS WHERE username = #{userName}")
    User getUserByUserName(String username);

    @Insert("INSERT INTO USERS (username, salt, hashedpassword, firstname, lastname) VALUES(#{userName}, #{salt}, #{hashedPassword}, #{firstName}, #{lastName})")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    int addUser(User user);

    @Delete("DELETE FROM USERS WHERE username = #{userName}")
    int deleteUserByUserName(String userName);

    @Delete("DELETE FROM USERS WHERE username = #{userName}")
    boolean deleteUserByUserName2(String userName);

    @Delete("DELETE FROM USERS")
    boolean deleteAll();

    @Update("UPDATE USERS SET username =#{userName}, salt =#{salt}, hashedpassword =#{hashedPassword}, firstname =#{firstName}, lastname =#{lastName}  WHERE username =#{userName}")
    boolean updateUser(User user);
}
