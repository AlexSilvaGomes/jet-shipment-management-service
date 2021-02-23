package com.jet.peoplemanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.jet")
public class PeopleManagementApplication {
	public static void main(String[] args) {
		SpringApplication.run(PeopleManagementApplication.class, args);
	}
}
