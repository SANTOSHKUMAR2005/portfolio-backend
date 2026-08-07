package com.portfolio.service;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.portfolio.dto.Project_text;
import com.portfolio.model.Project;
import com.portfolio.repository.ProjectRepository;

import jakarta.transaction.Transactional;

@Service
public class ProjectServices {
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private ProjectRepository projectRepository;
	@Autowired
	private Cloudinary cloudinary;
    
	@Transactional
	public String saveProject( Project_text project_text , MultipartFile file)  {
		         Map<?,?>cloudinary_response;
				try {
					cloudinary_response = SaveImg( file);
				} catch (IOException e) {
					e.printStackTrace();
					return e.getMessage();
				}
		           
		        Project map = modelMapper.map(project_text, Project.class);
		        map.setImage_url(cloudinary_response.get("secure_url").toString());
		        map.setImg_public_id(cloudinary_response.get("public_id").toString());
		        
		        projectRepository.save(map);
		        
		        
		return "done";
	}
	
	private Map<?,?> SaveImg(MultipartFile file) throws IOException {
		  Map<?,?> uploadResults=cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("folder","My_Portfolio"));
		   return uploadResults;
	}
	
	@Transactional
	public String DeleteProject(Long id) {
		Optional<Project> optional = projectRepository.findById(id);
		 if(optional.isPresent()) {
			 Project project = (Project)optional.get();
			 String img_public_id = project.getImg_public_id();
			 try {
				Map<?,?> destroy = cloudinary.uploader().destroy(img_public_id, ObjectUtils.asMap("folder","My_Portfolio"));
				projectRepository.deleteById(id);
			} catch (IOException e) {
				return e.getMessage();
			}
		 }
		 
		 return "deleted";
	}
}
