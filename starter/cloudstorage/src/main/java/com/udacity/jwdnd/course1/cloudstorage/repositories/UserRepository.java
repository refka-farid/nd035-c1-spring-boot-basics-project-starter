package com.udacity.jwdnd.course1.cloudstorage.repositories;

import com.udacity.jwdnd.course1.cloudstorage.mappers.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Stream;

@Repository
public class UserRepository {
    // TODO: 30/05/2021 Use logging
    private final UserMapper mapper;

    public UserRepository(UserMapper mapper) {
        this.mapper = mapper;
    }

    public boolean add(User user) {
        int id = mapper.addUser(user);
        return id > 0;
    }

    public boolean addAll(List<User> userList) {
        Stream<Integer> integerStream = userList.stream().map(mapper::addUser);
        boolean allMatch = integerStream.allMatch(this::isPositive);
        return allMatch;
    }

    private boolean isPositive(int x) {
        return x > 0;
    }

    public boolean delete1(User user) {
        boolean isDeleted = mapper.deleteUserByUserName2(user.getUserName());
        return isDeleted;
    }

    public boolean delete2(User user) {
        int rows = mapper.deleteUserByUserName(user.getUserName());
        return rows > 0;
    }

    public boolean deleteAll() {
        boolean allDeleted = mapper.deleteAll();
        return allDeleted;
    }

    public User updateOrNull(User user) {
        boolean isUpdated = mapper.updateUser(user);
        if (isUpdated) {
            return mapper.getUserByUserName(user.getUserName());
        } else {
            return null;
        }
    }

    public User addOrUpdate(User user) {
        User storedUser = mapper.getUserByUserName(user.getUserName());
        if (storedUser == null) {
            mapper.addUser(user);
        } else {
            mapper.updateUser(user);
        }
        return mapper.getUserByUserName(user.getUserName());
    }
}
