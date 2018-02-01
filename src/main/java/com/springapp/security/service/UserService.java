package com.springapp.security.service;

import com.springapp.security.model.User;
import com.springapp.security.util.exception.NotFoundException;

import java.util.List;

/**
 * Created by 123 on 21.01.2018.
 */
public interface UserService {

    User update(User user);

    User create(User user);

    void deleteUser(int id) throws NotFoundException;

    User get(int id)throws NotFoundException;

    List<User> getAll();

    User getUserByName(String name)throws NotFoundException;


}
