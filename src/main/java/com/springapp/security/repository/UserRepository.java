package com.springapp.security.repository;

import com.springapp.security.model.User;

import java.util.List;

/**
 * Created by 123 on 21.01.2018.
 */
public interface UserRepository {

    User save(User user);

    boolean deleteUser(int id);

    User get(int id);

    List<User> getAll();

    User getUserByName(String name);
}
