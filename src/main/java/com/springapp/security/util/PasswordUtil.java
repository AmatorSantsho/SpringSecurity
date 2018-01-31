package com.springapp.security.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Created by 123 on 29.01.2018.
 */
public class PasswordUtil {
//    public static void main(String[] args) {
//        System.out.println(new BCryptPasswordEncoder().encode("user04"));
//    }

    public static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public static String encodePasswordToBcrypt(String stringPassword) {
        return encoder.encode(stringPassword);
    }


}
