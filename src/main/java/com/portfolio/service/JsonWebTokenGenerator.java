package com.portfolio.service;

import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JsonWebTokenGenerator {
	
	   @Value("${Json_secure_key}")
       private String supper_key;
       public String generateToken(String username , String role) {
    	   
    	   Map<String,  String> claim=Map.of("username",username, "role",role);
    	   Long expiration_duration=(long)24*60*60*1000;
    	   
    	   String token=Jwts.builder()
    			   .claims(claim)
    			   .subject(username)
    			   .issuedAt(new Date(System.currentTimeMillis()))
    			   .expiration(new Date(System.currentTimeMillis()+expiration_duration))
    			   .signWith(generateSecretKey(), Jwts.SIG.HS256)
    			   .compact();
    			   
    			   return token;
    			   
       }
       
       private SecretKey generateSecretKey() {
    	   SecretKey secretKey=Keys.hmacShaKeyFor(supper_key.getBytes());
    	   return secretKey;
       }
}
