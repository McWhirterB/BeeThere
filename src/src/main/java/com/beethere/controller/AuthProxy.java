package com.beethere.controller;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.beethere.model.Employee;
import com.beethere.model.TokenRequest;

@FeignClient(name = "auth", url = "http://172.16.0.51:8080/auth_service/api/auth/")
public interface AuthProxy {
	@PostMapping(value = "/verify", consumes = MediaType.APPLICATION_JSON_VALUE)
	Employee verifyEmployee(@RequestBody TokenRequest token);
	//String verifyEmployee(@RequestBody TokenRequest token);
}
