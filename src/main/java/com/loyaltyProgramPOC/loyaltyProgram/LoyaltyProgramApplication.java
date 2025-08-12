package com.loyaltyProgramPOC.loyaltyProgram;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class LoyaltyProgramApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoyaltyProgramApplication.class, args);
	}

}
