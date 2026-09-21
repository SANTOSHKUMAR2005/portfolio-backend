package com.portfolio.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.portfolio.model.Resume;
import com.portfolio.model.RunningText;
import com.portfolio.repository.ResumeRepo;
import com.portfolio.service.Services;

@RestController
@CrossOrigin(origins = "*")
public class GeneralController {
	
	@Autowired 
	Services services;
	
	@Autowired
	ResumeRepo resumeRepo;
	
	@GetMapping("/get/resumeURL")
	public ResponseEntity<?> getResumeURL(){
		
		Optional<Resume> resume = resumeRepo.findById(1);
		String resumeURL=null;
		if(resume.isPresent()) {
		    resumeURL = resume.get().getResume_url();
		}
		return ResponseEntity.status(HttpStatus.OK).body(Map.of("resumeURL" , resumeURL));
	}
  
	@PostMapping(value =  "/upload/resume" , consumes = "multipart/form-data")
 	public ResponseEntity<?> updateResume(@RequestPart("resume") MultipartFile file) {
		
		
		 String[] response = services.updateResume(file);
		 
		 if(!response[0].equals("")) {
			 return ResponseEntity.status(HttpStatus.OK).body(Map.of("resumeURL" , response[0]));
		 }
		 
		 return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message" , response[1]));
		
	}
	
	
	
	
	
	
	
	@GetMapping("/get/runningText")
	public ResponseEntity<?> addRunningText(){
		
		List<RunningText> allRunningText = services.findAllRunningText();
		return ResponseEntity.status(HttpStatus.OK).body(Map.of("RunningText", allRunningText));
	}
	
	
	@PostMapping("/upload/runningText")
	public ResponseEntity<?> addRunningText(@RequestBody List<String> texts){
		
		System.out.println(texts);
		List<RunningText> savedRunningText = services.saveRunningText(texts);
		return ResponseEntity.status(HttpStatus.OK).body(Map.of("updatedText", savedRunningText));
	}
	
	@DeleteMapping("/delete/runningText/{id}")
	public ResponseEntity<?> addRunningText(@RequestBody int id){
		
		String message = services.deleteRunnigText(id);
		if(message.equals("deleted"))
		return ResponseEntity.status(HttpStatus.OK).body(Map.of("message" , "Text deleted successfully"));
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message" , "Text not found for provided Id."));	
	}
}
