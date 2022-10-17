package com.krungsri;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * The main application class for the Swagger demo project.
 */
@SpringBootApplication
@ComponentScan
public class RegistrationApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(RegistrationApplication.class, args);
	}
}