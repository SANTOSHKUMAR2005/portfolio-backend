package com.portfolio.controller;

import com.portfolio.model.ContactMessage;
import com.portfolio.repository.ContactMessageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/contact-form")
@CrossOrigin(origins = "*")
public class ContactController {
     
	@Autowired
    private ContactMessageRepository contactRepository;

   
    // Public API for visitors
    @PostMapping("/save-message")
    public ResponseEntity<?> saveMessage(@RequestBody ContactMessage message) {
    	ContactMessage save = contactRepository.save(message);
    	if(save!=null) {
    		return ResponseEntity.ok(Map.of("message","your message sent successfully"));
    	}else {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message" , "Something went Wrong"));
    	}
        
    }

    // Admin API to read incoming messages
    @GetMapping("/get-allmessage")
    public ResponseEntity<List<ContactMessage>> getAllMessages() {
    	
        return ResponseEntity.ok(contactRepository.findAll());
    }
    
    @DeleteMapping("/delete-message/{deletemessageId}")
    public ResponseEntity<?> deleteMessage(@PathVariable Long deletemessageId){
    	contactRepository.deleteById(deletemessageId);
    	
    	return ResponseEntity.ok(Map.of("message" , "message deleted seccessfully"));
    }
}