package com.jet.peoplemanagement;

import com.jet.peoplemanagement.fileloader.StorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
@ComponentScan("com.jet")
public class PeopleManagementApplication {
	public static void main(String[] args) {
		SpringApplication.run(PeopleManagementApplication.class, args);
	}
}
