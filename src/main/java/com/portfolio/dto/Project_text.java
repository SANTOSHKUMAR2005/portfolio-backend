package com.portfolio.dto;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class Project_text {
	
    private String title;

    private String description;

    private String githubUrl;
    private String liveLink;
    private List<String> techstack;
}
