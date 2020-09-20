package edu.kmust.bully.user.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @Author BunnyAndOak0
 * @Description
 * @Date 2020/8/14 15:40
 **/
public class EncodeUtils {
    private static PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static String encode(String rawPassword) {
        String encodePassword = passwordEncoder.encode(rawPassword);
        encodePassword = "{bcrypt}" + encodePassword;
        return encodePassword;
    }
}
