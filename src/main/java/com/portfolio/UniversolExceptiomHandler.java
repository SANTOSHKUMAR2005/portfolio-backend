package com.portfolio;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class UniversolExceptiomHandler {
    @ExceptionHandler(Exception.class)
	public ResponseEntity<?> defaultExceptionHandler(Exception e, HttpServletRequest request){
    	System.out.println("===================kuch to galat ho raha hai ==========================");
		String url=request.getRequestURI();
		Map<?,?> map=Map.of("message", e.getMessage() , "path", url );
		e.printStackTrace();
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(map);
	}
}
