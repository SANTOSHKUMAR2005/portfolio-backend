package com.portfolio.controller;

import com.portfolio.dto.Project_text;
import com.portfolio.repository.ProjectRepository;
import com.portfolio.service.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ProjectController {
    
	@Autowired
    private Services projectServices;
	@Autowired
	private ProjectRepository projectRepository;

    // Public API
    @GetMapping("/projects/get-all-projects")
    public ResponseEntity<?> getAllProjects() {
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("data", projectRepository.findAll()));
    }

    // Admin protected routes
    @PostMapping(value="/admin/add-new-project" , consumes = "multipart/form-data")
    public ResponseEntity<?> createProject(@RequestPart("project_textdata") Project_text project_text , @RequestPart("file") MultipartFile file) {
    	
    	String msg = projectServices.saveProject(project_text, file);
    	if(msg.equals("done")) {
    		    return ResponseEntity.status(HttpStatus.OK).body(Map.of("message" ,"project saved siccessfully"));
    	}else {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message" ,msg));
    	}
    	
       
    }

    @DeleteMapping("/admin/delete-project/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable Long id) {
        String response = projectServices.DeleteProject(id);
        if(response.equals("deleted"))
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message" , "Project deleted successfully"));
        else 
        	return (ResponseEntity<?>) ResponseEntity.internalServerError().body(Map.of("message",response));
        
    }
}