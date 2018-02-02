package com.springapp.security.service;

import com.springapp.security.model.User;
import com.springapp.security.repository.UserRepository;
import com.springapp.security.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import static com.springapp.security.util.Checking.checkNotFound;

/**
 * Created by 123 on 21.01.2018.
 */
@Service
public class UserServiceImpl implements UserService, UserDetailsService {

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
    public User getUserByName(String name) {
        //assert email-argument to null
        Assert.notNull(name,"Name must be not null");
        return  checkNotFound(repository.getUserByName(name),"name"+ name);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=repository.getUserByName(username);
        if (user==null){
            throw new UsernameNotFoundException("User with " + username + " not found");
        }
        UserDetails userDetails=new org.springframework.security.core.userdetails.User(user.getName(),
                user.getPassword(),user.getRoles());
        return userDetails;
    }
}
