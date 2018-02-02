package com.springapp.security.model;

import org.springframework.security.core.GrantedAuthority;

/**
 * Created by 123 on 21.01.2018.
 */
public enum Role implements GrantedAuthority {
    ROLE_USER,
    ROLE_ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
