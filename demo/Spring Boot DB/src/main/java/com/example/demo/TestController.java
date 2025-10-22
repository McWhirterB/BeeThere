package com.example.demo;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/test")
public class TestController {
	@Autowired
	private TestRepository test;
	
	@GetMapping("/")
	public @ResponseBody Iterable<Person> testApi() {
		return test.findAll();
	}	
}
