package com.teenthofabud.codingchallenge.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ShoppingListManagerApplication {

	public static void main(String[] args) {
		System.setProperty("otel.java.global-autoconfigure.enabled", "true");
		SpringApplication.run(ShoppingListManagerApplication.class, args);
	}

}