package com.example.demo;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Test {

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(14);
        String result = encoder.encode("myPassword");
        long now = System.currentTimeMillis();
        System.out.println(encoder.matches("myPassword", result));
        System.out.println(System.currentTimeMillis() - now);
    }

}
