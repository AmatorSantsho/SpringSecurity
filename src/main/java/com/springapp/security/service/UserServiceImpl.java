package com.springapp.security.service;

import com.springapp.security.model.User;
import com.springapp.security.repository.UserRepository;
import com.springapp.security.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import static com.springapp.security.util.Checking.checkNotFound;

/**
 * Created by 123 on 21.01.2018.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
private UserRepository repository;

    @Override
    public User update(User user) {
        return repository.save(user);
    }

    @Override
    public User create(User user) {
        //assert user-argument to null
        Assert.notNull(user,"User must be not null");
        return repository.save(user);
    }

    @Override
    public void deleteUser(int id) throws NotFoundException{
        //assert that user exist in repository
        checkNotFound(repository.deleteUser(id), "id=" + String.valueOf(id));
    }

    @Override
    public User get(int id) {
        //assert that user exist in repository
        return  checkNotFound(repository.get(id),"id=" + String.valueOf(id));
    }

    @Override
    public List<User> getAll() {
        return repository.getAll();
    }

    @Override
    public User getUserByEmail(String email) {
        //assert email-argument to null
        Assert.notNull(email,"Email must be not null");
        return  checkNotFound(repository.getUserByEmail(email),"email"+ email);
    }
}
