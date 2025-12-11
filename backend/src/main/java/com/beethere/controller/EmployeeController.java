package com.beethere.controller;

import java.util.function.Function;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beethere.model.Employee;
import com.beethere.model.TokenRequest;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
	private AuthProxy authProxy;

    private static final Logger APPLICATION_LOGGER = LogManager.getLogger("Application");
    private static final Logger SECURITY_LOGGER = LogManager.getLogger("Security");

	public EmployeeController(AuthProxy authProxy) {
        APPLICATION_LOGGER.debug("Constructing EmployeeController");
		this.authProxy = authProxy;
	}

    private ResponseEntity<?> withAuth(String token, Function<Employee, ResponseEntity<?>> action) {
        if (token == null || token.isEmpty()) {
            SECURITY_LOGGER.error("Authentication failed: Token is missing ");
            return new ResponseEntity<>("Token Required", HttpStatus.BAD_REQUEST);
        }
        try {
            Employee employee = authProxy.verifyEmployee(new TokenRequest(token));
            return action.apply(employee);
        } catch (Exception ex) {
            ex.printStackTrace();
            SECURITY_LOGGER.error("Authentication failed: Invalid token");
            return new ResponseEntity<>("Failed to validate token", HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/employee")
    public ResponseEntity<?> getEmployeeDetails(@RequestHeader(value = "Bearer", required = false) String token) {
        return withAuth(token, employee -> {
            return new ResponseEntity<>(employee, HttpStatus.OK);
        });
    }
}