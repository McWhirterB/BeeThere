package com.beethere.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.beethere.config.AboutConfig;

@CrossOrigin(origins ="http://localhost:3000")
@RestController
@RequestMapping("/about")
public class AboutController {

    @Autowired
    private AboutConfig aboutConfig;

    @GetMapping({"", "/"})
	public ResponseEntity<?> getAbout() {
		return ResponseEntity.ok(aboutConfig);
	}
}