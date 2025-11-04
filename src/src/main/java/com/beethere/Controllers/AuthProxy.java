package com.beethere.Controllers;

import com.beethere.model.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;

@FeignClient(name = "auth", url = "http://172.16.0.51:8080/auth_service/api/auth/")
public interface AuthProxy {
	@PostMapping(value = "/verify", consumes = MediaType.APPLICATION_JSON_VALUE)
	Employee verifyEmployee(@RequestBody TokenRequest token);
}
