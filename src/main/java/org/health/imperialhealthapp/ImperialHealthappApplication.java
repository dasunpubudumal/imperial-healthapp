package org.health.imperialhealthapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class ImperialHealthappApplication {

	public static void main(String[] args) {
		SpringApplication.run(ImperialHealthappApplication.class, args);
	}

}
