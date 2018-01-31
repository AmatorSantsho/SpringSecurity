package com.springapp.security.util;

import com.springapp.security.util.exception.NotFoundException;

/**
 * Created by 123 on 21.01.2018.
 */
public class Checking {
    public static void checkNotFound(boolean found, String id) {
        if (!found) {
            throw new NotFoundException("User with "+ id + " not found");
        }
    }

    public static <T> T checkNotFound(T user, String id) {
        checkNotFound(user!=null,id);
        return user;
    }
}
