package com.example;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class FreshFood {
    public static void main(String[] args) {
		SpringApplication.run(FreshFood.class, args);
	}
    
    @Bean
    CommandLineRunner run() {
    	return arg -> {
    		System.out.println("hello Freshfood !");
    	};
    }
}
//thêm line 119&139 cartAPI, line 57 CartItemService, line 21 InCartItemService, line 21 InCartItemRes
