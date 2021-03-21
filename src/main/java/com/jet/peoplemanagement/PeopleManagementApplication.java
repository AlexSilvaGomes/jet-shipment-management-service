package com.jet.peoplemanagement;

import com.jet.peoplemanagement.auth.UserServiceJWT;
import com.jet.peoplemanagement.fileloader.StorageProperties;
import com.jet.peoplemanagement.user.JetUser;
import com.jet.peoplemanagement.user.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;

import static com.jet.peoplemanagement.service.ProviderService.MUDAR_123;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
@CrossOrigin
@ComponentScan("com.jet")
public class PeopleManagementApplication implements CommandLineRunner {

	public static final String ADMIN_ADMIN_COM = "admin@admin.com";

	@Autowired
	UserServiceJWT userService;

	public static void main(String[] args) {
		SpringApplication.run(PeopleManagementApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		try {
			userService.getByUsername(ADMIN_ADMIN_COM);
		} catch (UsernameNotFoundException e) {
			userService.save(new JetUser(ADMIN_ADMIN_COM, MUDAR_123, UserType.PROVIDER.getName()));
		}
	}
}
