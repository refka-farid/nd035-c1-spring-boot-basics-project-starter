package com.udacity.jwdnd.course1.cloudstorage.repositories;

import com.udacity.jwdnd.course1.cloudstorage.entities.User;
import com.udacity.jwdnd.course1.cloudstorage.mappers.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Stream;

@Repository
public class UserRepository {
    private final Logger logger = LoggerFactory.getLogger(UserRepository.class);

    private final UserMapper mapper;

    public UserRepository(UserMapper mapper) {
        this.mapper = mapper;
    }

    public User getOneByUserName(String userName) {
        logger.trace("getOne user " + userName);
        return mapper.getUserByUserName(userName);
    }

    public boolean add(User user) {
        logger.trace("add user " + user);
        int id = mapper.addUser(user);
        return id > 0;
    }

    public boolean addAll(List<User> userList) {
        logger.trace("addAll " + userList);
        Stream<Integer> integerStream = userList.stream().map(mapper::addUser);
        boolean allMatch = integerStream.allMatch(this::isPositive);
        return allMatch;
    }

    private boolean isPositive(int x) {
        return x > 0;
    }

    public boolean delete1(User user) {
        logger.trace("delete1 " + user);
        boolean isDeleted = mapper.deleteUserByUserName2(user.getUserName());
        return isDeleted;
    }

    public boolean delete2(User user) {
        logger.trace("delete2" + user);
        int rows = mapper.deleteUserByUserName(user.getUserName());
        return rows > 0;
    }

    public boolean deleteAll() {
        logger.trace("deleteAll");
        boolean allDeleted = mapper.deleteAll();
        return allDeleted;
    }

    public User updateOrNull(User user) {
        logger.trace("updateOrNull "+user);
        boolean isUpdated = mapper.updateUser(user);
        if (isUpdated) {
            return mapper.getUserByUserName(user.getUserName());
        } else {
            return null;
        }
    }

    public User addOrUpdate(User user) {
        logger.trace("addOrUpdate "+user);
        User storedUser = mapper.getUserByUserName(user.getUserName());
        if (storedUser == null) {
            mapper.addUser(user);
        } else {
            mapper.updateUser(user);
        }
        return mapper.getUserByUserName(user.getUserName());
    }
}
