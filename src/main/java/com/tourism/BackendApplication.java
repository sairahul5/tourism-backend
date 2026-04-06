package com.tourism;

import com.tourism.model.User;
import com.tourism.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@Bean
	CommandLineRunner init(UserRepository userRepo, PasswordEncoder encoder) {
		return args -> {
			List<User> users = userRepo.findAll();
			for(User u : users) {
				if(!u.getPassword().startsWith("$2a$")) {
					u.setPassword(encoder.encode(u.getPassword()));
					userRepo.save(u);
				}
			}
		};
	}
}

