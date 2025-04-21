package com.example.keymanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
public class KeyManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(KeyManagerApplication.class, args);
	}

}
