package com.beethere.controller;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.beethere.model.Employee;
import com.beethere.model.TokenRequest;

@FeignClient(name = "auth", url = "http://172.16.0.51:8080/auth_service/api/auth/")
public interface AuthProxy {

	static final Logger APPLICATION_LOGGER = LogManager.getLogger("Application"); 

	@PostMapping(value = "/verify", consumes = MediaType.APPLICATION_JSON_VALUE)
	Employee verifyEmployeeFeign(@RequestBody TokenRequest token);

	default Employee verifyEmployee(TokenRequest token) {
		APPLICATION_LOGGER.info("Verifying token: {}", token);
		return verifyEmployeeFeign(token);
	}
}
