package com.portfolio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.portfolio.model.Admin;
import com.portfolio.repository.AdminRepo;
import com.portfolio.service.JsonWebTokenGenerator;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AuthController {
	@Autowired
	private AdminRepo adminRepo;
	@Autowired
	private JsonWebTokenGenerator tokenGenerator;

	@PostMapping("/auth/admin-login")
	public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
		String email = credentials.get("email");
		String password = credentials.get("password");

		Optional optional = adminRepo.findById(email);
		System.out.println(optional);
		if (!optional.isEmpty() && optional.isPresent()) {

			Admin admin2 = (Admin) optional.get();
			String adminEmail = admin2.getEmail();
			String adminPassword = admin2.getPass();
			
			if (adminEmail.equals(email) && adminPassword.equals(password)) {
				
				String token = tokenGenerator.generateToken(adminEmail, "admin");
				return ResponseEntity.ok(Map.of("message", "Login successful", "status", "SUCCESS", "jwt_token", token));
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Invalid credentials"));
			}

		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "User Not Found"));
		}

	}
}