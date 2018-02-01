package com.springapp.security.repository.JDBC;

import com.springapp.security.model.Role;
import com.springapp.security.model.User;
import com.springapp.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import javax.sql.DataSource;
import java.util.*;

/**
 * Created by 123 on 01.02.2018.
 */
@Repository
public class UserRepositoryImpl implements UserRepository {

    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert simpleJdbcInsert;
@Autowired
    public UserRepositoryImpl(DataSource dataSource, JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        simpleJdbcInsert=new SimpleJdbcInsert(dataSource).withTableName("users").usingGeneratedKeyColumns("id");
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;

    }

    @Override
    public User save(User user) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);

        if (user.isNew()) {
            Number newKey = simpleJdbcInsert.executeAndReturnKey(parameterSource);
            user.setId(newKey.intValue());
            insertRoles(user);
        } else {
            if (namedParameterJdbcTemplate.update(
                    "UPDATE users SET name=:name,  password=:password, email=:email," +
                            "registration=:registration WHERE id=:id", parameterSource) == 0) {
                return null;
            }
            // Simplest implementation.
            // More complicated : get user roles from DB and compare them with user.roles (assume that roles are changed rarely).
            // If roles are changed, calculate difference in java and delete/insert them.
            deleteRoles(user);
            insertRoles(user);
        }
        return user;
    }

    @Override
    public boolean deleteUser(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id)!=0;
    }

    @Override
    public User get(int id) {
        List<User> users=jdbcTemplate.query("SELECT * FROM users WHERE id=?", ROW_MAPPER, id);
        return setRoles(DataAccessUtils.singleResult(users));
    }

    @Override
    public List<User> getAll() {
        Map<Integer, Set<Role>> map = new HashMap<>();
        jdbcTemplate.query("SELECT * FROM user_roles", rs -> {
            map.computeIfAbsent(rs.getInt("user_id"), userId -> EnumSet.noneOf(Role.class))
                    .add(Role.valueOf(rs.getString("role")));
        });
        List<User> users = jdbcTemplate.query("SELECT * FROM users ORDER BY name, email", ROW_MAPPER);
        users.forEach(u -> u.setRoles(map.get(u.getId())));
        return users;
    }

    @Override
    public User getUserByName(String name) {
        List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE name=?", ROW_MAPPER, name);
        return setRoles(DataAccessUtils.singleResult(users));
    }
    private void insertRoles(User user){
        Set<Role> roles=user.getRoles();
        if (!CollectionUtils.isEmpty(roles)){
            jdbcTemplate.batchUpdate("INSERT INTO user_roles (user_id, role) VALUES (?,?)",roles,
                    roles.size(),
                    (ps, role) -> {
                        ps.setInt(1,user.getId());
                        ps.setString(2,role.name());
                    });
        }
    }
    private void deleteRoles (User user){
        jdbcTemplate.update("DELETE FROM user_roles WHERE user_id=?",user.getId());
           }
    private User setRoles(User user){
        if (user!=null){
            List<Role> roles = jdbcTemplate.query("SELECT role FROM user_roles WHERE user_id=?",
                    (rs, rowNum) -> Role.valueOf(rs.getString("role")),user.getId());
            user.setRoles(roles);
        }
        return user;
    }

}
