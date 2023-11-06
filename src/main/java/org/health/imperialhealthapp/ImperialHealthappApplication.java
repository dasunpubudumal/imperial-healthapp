package org.health.imperialhealthapp;

import lombok.extern.slf4j.Slf4j;
import org.health.imperialhealthapp.models.domain.Role;
import org.health.imperialhealthapp.models.domain.User;
import org.health.imperialhealthapp.repositories.*;
import org.health.imperialhealthapp.util.FakeObservationGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import static org.health.imperialhealthapp.util.Initializer.*;

@SpringBootApplication
@EnableTransactionManagement
@Slf4j
public class ImperialHealthappApplication {

	public static void main(String[] args) {
		SpringApplication.run(ImperialHealthappApplication.class, args);
	}

	@Value("${environment}")
	private String environment;

	@Bean
	@Transactional
	CommandLineRunner run(
			UserRepository userRepository,
			RoleRepository roleRepository,
			PasswordEncoder bCryptPasswordEncoder,
			MeasurementTypeRepository measurementTypeRepository,
			ObservationRepository observationRepository,
			PatientRepository patientRepository) {
		String adminPw = bCryptPasswordEncoder.encode("admin");
		String userPw = bCryptPasswordEncoder.encode("user");
		log.info("Environment: {}", environment);
		log.info("Encoded password: {}", adminPw);
		log.info("Starting application...");
		return args -> {
			initializeMeasurementTypes(measurementTypeRepository);
			intializePatients(patientRepository);
			if (Objects.nonNull(environment) && !environment.equals("dev"))
				initializeObservations(observationRepository);
			if (roleRepository.findByRoleName("ADMIN").isPresent()) return;
			Role adminRole = roleRepository.save(
					Role.builder().roleName("ADMIN").build()
			);
			Role userRole = roleRepository.save(
					Role.builder().roleName("USER").build()
			);
			Set<Role> adminRoles = Set.of(adminRole, userRole);
			Set<Role> userRoles = Set.of(userRole);
			User adminUser = User.builder()
					.username("admin")
					.password(adminPw)
					.firstName("Admin")
					.lastName("Admin")
					.roles(adminRoles)
					.build();
			User normalUser = User.builder()
					.username("user")
					.password(userPw)
					.firstName("User")
					.lastName("User")
					.roles(userRoles)
					.build();
			userRepository.saveAll(List.of(adminUser, normalUser));
		};
	}

}
