package com.myprojects.smartcontactmaneger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SmartcontactmanegerApplication {
	public static final Logger logger = LoggerFactory.getLogger(SmartcontactmanegerApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SmartcontactmanegerApplication.class, args);

		logger.info("Smartcontactmaneger application started...");
	}

}
