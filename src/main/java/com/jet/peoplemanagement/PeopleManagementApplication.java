package com.jet.peoplemanagement;

import com.jet.peoplemanagement.model.Provider;
import com.jet.peoplemanagement.service.ProviderService;
import com.jet.peoplemanagement.user.UserServiceJWT;
import com.jet.peoplemanagement.fileloader.StorageProperties;
import com.jet.peoplemanagement.user.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.LocalDateTime;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
@CrossOrigin
@EnableCaching
@ComponentScan("com.jet")
public class PeopleManagementApplication implements CommandLineRunner {

	public static final String ADMIN_ADMIN_COM = "admin@admin.com";

	@Autowired
	UserServiceJWT userService;

	@Autowired
	ProviderService providerService;

	public static void main(String[] args) {
		SpringApplication.run(PeopleManagementApplication.class, args);
	}

	@Override
	public void run(String... args) {
		try {
			userService.getCredentialByUsername(ADMIN_ADMIN_COM);

		} catch (UsernameNotFoundException e) {
			Provider provider = new Provider(){
				{
					setCpf("00000000000");
					setName("Administrador");
					setMobile("11971937005");
					setEmail(ADMIN_ADMIN_COM);
					setLevel("1");
					setZone("ALL");
					setType(UserType.ADMIN.getName());
					setBank("Any");
					setAccount("0000");
					setAgency("0000");
					setBirthDate(LocalDateTime.now());
					setImg("svg/delivery-boy.svg");
				}
			};
			providerService.save(provider);
		}

	}
}
