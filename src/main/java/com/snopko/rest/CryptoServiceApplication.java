package com.snopko.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CryptoServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CryptoServiceApplication.class, args);
	}

}
