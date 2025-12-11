package com.beethere.app;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;


@SpringBootApplication
@EnableFeignClients(basePackages = "com.beethere.controller")
@ComponentScan(basePackages = "com.beethere")
@EnableJpaRepositories(basePackages = "com.beethere.repository")
@EntityScan(basePackages = "com.beethere.model")
public class AppApplication {
	public static void main(String[] args) throws IOException {
		
		Logger standardLogger = LogManager.getLogger("Application");
		Logger securityLogger = LogManager.getLogger("Security");

		standardLogger.info("Starting Spring Application");
		securityLogger.info("Starting Spring Application");
		SpringApplication.run(AppApplication.class, args);
	}

}
