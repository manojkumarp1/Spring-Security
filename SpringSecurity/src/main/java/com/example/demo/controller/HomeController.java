package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
	
	@GetMapping("demo")
	public ResponseEntity<String> say()
	{
		return ResponseEntity.ok("done");
	}

}
