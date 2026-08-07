package com.portfolio.model;


import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

import com.portfolio.helpers.StringListConverter;

@Entity
@Table(name = "My_projects")
@Data
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT" , nullable = false)
    private String description;

    private String githubUrl;
    private String liveLink;

    @Convert(converter = StringListConverter.class)
    private List<String> techstack;
    
    private String image_url;
    
    private String img_public_id;
}