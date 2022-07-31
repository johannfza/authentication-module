package io.treedays.authentication;

import io.treedays.authentication.domain.Role;
import io.treedays.authentication.domain.User;
import io.treedays.authentication.service.UserService;
import io.treedays.authentication.service.exceptions.UserNotFoundException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@SpringBootApplication
public class AuthenticationApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthenticationApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	CommandLineRunner run(UserService userService) {
		return args -> {
			userService.saveRole(new Role(null, "USER"));
			userService.saveRole(new Role(null, "ADMIN"));

			List<User> users = new ArrayList<>();
			users.add(new User(null, "John", "john@example.com", "1234", new ArrayList<>()));
			users.add(new User(null, "Jim", "jim@example.com", "1234", new ArrayList<>()));
			users.add(new User(null, "Will", "will@example.com", "1234", new ArrayList<>()));
			users.add(new User(null, "Jane", "jane@example.com", "1234", new ArrayList<>()));

			users.forEach( user -> {
					userService.saveUser(user);
				try {
					userService.addRoleToUser(user.getUsername(), "USER");
				} catch (UserNotFoundException e) {
					throw new RuntimeException(e);
				}
			});
		};
	}

}
