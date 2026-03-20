package com.bulent.integration.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableScheduling
public class OracleEbsRestIntegrationDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(OracleEbsRestIntegrationDemoApplication.class, args);
	}

}
