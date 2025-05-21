package com.back;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.back.User.UserRepository;
import com.back.User.UserService;

@SpringBootApplication
public class BackApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackApplication.class, args);
	}
	@Bean
    CommandLineRunner initDatabase(UserService userService, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.findByUsername("testuser") == null) {
                userService.register(
                    "testuser",
                    "testpassword",
                    "testuser@example.com",
                    "1234567890"
                );
                System.out.println("Test user created: username=testuser, password=testpassword");
            } else {
                System.out.println("Test user already exists: username=testuser");
            }
        };
    }
}
