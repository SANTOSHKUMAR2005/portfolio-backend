package com.portfolio.model;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "resume_Table")
@Data
public class Resume {
	
	@Id
	@Column(nullable = false)
	private int id;
    
	@Column(nullable = false)
	private String resume_url;
	@Column(nullable = false)
	private String resume_publi_id;
	
}
