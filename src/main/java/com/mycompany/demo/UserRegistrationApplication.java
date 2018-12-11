package com.mycompany.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * A spring boot web application that offers a remote service interface (endpoint) to register a user.
 *
 * @author Riyaz Saiyed (saiyedriyaz@gmail.com)
 * @version 1.0, 01 Dec 2018
 */
@SpringBootApplication
public class UserRegistrationApplication {

	public static void main(String[] args) {
		
		SpringApplication.run(UserRegistrationApplication.class, args);
	}
}
