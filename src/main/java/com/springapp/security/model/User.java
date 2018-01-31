package com.springapp.security.model;

import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * Created by 123 on 21.01.2018.
 */
public class User {
    private Integer id;
    private String name;
    private String password;
    private String email;
    private Set<Role> roles;
    private Date registration;

    public User() {
    }

    public User(Integer id, String name, String password, String email, Collection<Role>  roles, Date registration) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.registration = registration;
        setRoles(roles);
    }

    public Integer getId() {

        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", roles=" + roles +
                ", registration=" + registration +
                '}';
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = CollectionUtils.isEmpty(roles) ? Collections.emptySet() : EnumSet.copyOf(roles);
    }

    public Date getRegistration() {
        return registration;
    }

    public void setRegistration(Date registration) {
        this.registration = registration;
    }

    public boolean isNew(){
        return getId()==null;
    }
}
