package org.health.imperialhealthapp;

import lombok.extern.slf4j.Slf4j;
import org.health.imperialhealthapp.models.domain.Role;
import org.health.imperialhealthapp.models.domain.User;
import org.health.imperialhealthapp.repositories.MeasurementTypeRepository;
import org.health.imperialhealthapp.repositories.RoleRepository;
import org.health.imperialhealthapp.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Set;
import java.util.UUID;

import static org.health.imperialhealthapp.util.Initializer.initializeMeasurementTypes;

@SpringBootApplication
@EnableTransactionManagement
@Slf4j
public class ImperialHealthappApplication {

	public static void main(String[] args) {
		SpringApplication.run(ImperialHealthappApplication.class, args);
	}

	String uuid = UUID.randomUUID().toString();

	@Bean
	CommandLineRunner run(
			UserRepository userRepository,
			RoleRepository roleRepository,
			PasswordEncoder bCryptPasswordEncoder,
			MeasurementTypeRepository measurementTypeRepository) {
		String encode = bCryptPasswordEncoder.encode(uuid);
		log.info("Encoded password: {}", encode);
		return args -> {
			initializeMeasurementTypes(measurementTypeRepository);
			if (roleRepository.findByRoleName("ADMIN").isPresent()) return;
			Role adminRole = roleRepository.save(
					Role.builder().roleName("ADMIN").build()
			);
			Role userRole = roleRepository.save(
					Role.builder().roleName("USER").build()
			);
			Set<Role> adminRoles = Set.of(adminRole, userRole);
			User adminUser = User.builder()
					.username("admin")
					.password(encode)
					.firstName("Admin")
					.lastName("Admin")
					.roles(adminRoles)
					.build();
			userRepository.save(adminUser);
		};
	}

}
