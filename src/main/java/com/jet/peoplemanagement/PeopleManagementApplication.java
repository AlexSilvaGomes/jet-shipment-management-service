package com.jet.peoplemanagement;

import com.jet.peoplemanagement.fileloader.StorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
@CrossOrigin
@ComponentScan("com.jet")
public class PeopleManagementApplication {
	public static void main(String[] args) {
		SpringApplication.run(PeopleManagementApplication.class, args);
	}
}
