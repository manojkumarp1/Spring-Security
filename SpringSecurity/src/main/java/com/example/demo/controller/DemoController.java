package com.example.demo.controller;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Security.JwtToken;
import com.example.demo.entity.User;
import com.example.demo.repositories.UserRepo;

@RestController
@RequestMapping("/api/auth/")
public class DemoController {
	@Autowired
	UserRepo urepo;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtToken jwtToken;
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@GetMapping("/")
	public ResponseEntity<String> sayHello()
	{
		return ResponseEntity.ok("hello from secured ");
	}
	
	@PostMapping("/register")
	public ResponseEntity<Object> createUser(@RequestBody User u)
	{
		u.setPassword(passwordEncoder.encode(u.getPassword()));
		return ResponseEntity.ok().body(urepo.save(u));
	}
	
	@PostMapping("/login")
	public ResponseEntity<Object> Login(@RequestBody User u)
	{
		Authentication authenctication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(u.getEmail(),u.getPassword())
				);
		
		SecurityContextHolder.getContext().setAuthentication(authenctication);
		
		String token = jwtToken.generateToken(authenctication);
		return ResponseEntity.ok().body(
			Map.of("token",token,"tokentype","Bearer"));
	}
	@GetMapping("/demo")
	public ResponseEntity<String> say()
	{
		return ResponseEntity.ok("done");
	}
	
	

}

