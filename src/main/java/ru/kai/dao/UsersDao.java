package ru.kai.dao;

import ru.kai.models.User;

import java.util.List;
import java.util.Optional;

public interface UsersDao extends CrudDao<User> {
    Optional<User> findUserByName(String name);
    boolean isUserExists(User user);
}
