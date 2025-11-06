package com.beethere.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.beethere.config.Config;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.beethere.controller")
@ComponentScan(basePackages = "com.beethere")
@EnableJpaRepositories(basePackages = "com.beethere.repository")
@EntityScan(basePackages = "com.beethere.model")
public class AppApplication {

	public static void main(String[] args) throws IOException {

		System.out.println("Loading configuration");

		FileInputStream configFile;
		try {
			configFile = new FileInputStream(new File("config.yaml"));
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException("Failed to load config file");
		}
		Config.initializeFromFile(configFile);

		try {
			configFile.close();
		} catch (IOException e) {
			throw new IOException("Failed to close config file");
		}

		Config config = Config.getInstance(); // Load config first to ensure it fails early if there's a config issue
		
		URI configSourceUri = new File(config.getLogConfigPath()).toURI();
		Configurator.reconfigure(configSourceUri);
		
		Logger standardLogger = LogManager.getLogger("Application");
		Logger securityLogger = LogManager.getLogger("Security");

		standardLogger.info("Starting Spring Application");
		securityLogger.info("Starting Spring Application");
		SpringApplication.run(AppApplication.class, args);
	}

}
