package com.teenthofabud.codingchallenge.ecommerce;

import com.teenthofabud.codingchallenge.ecommerce.user.UserEntity;
import com.teenthofabud.codingchallenge.ecommerce.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class ShoppingListManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShoppingListManagerApplication.class, args);
	}

}