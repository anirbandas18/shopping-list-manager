package com.teenthofabud.codingchallenge.ecommerce;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class BCryptProducer {

    public static void main(String[] args) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String clearTextPassword = "password";
        System.out.println("Cleartext Password: " + clearTextPassword);
        String hashedPassword = passwordEncoder.encode(clearTextPassword);
        System.out.println("Hashed Password: " + hashedPassword);
        boolean isPasswordMatch = passwordEncoder.matches(clearTextPassword, hashedPassword);
        System.out.println("Does the password match? " + isPasswordMatch);

    }

}
