package com.portfolio.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
import com.portfolio.model.Resume;
import com.portfolio.repository.ProjectRepository;
import com.portfolio.repository.ResumeRepo;
import com.portfolio.repository.RunningTextRepo;
import com.portfolio.model.RunningText;

import jakarta.transaction.Transactional;

@Service
public class Services {
	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private Cloudinary cloudinary;

	@Autowired
	private ResumeRepo resumeRepo;
	
	@Autowired
	private RunningTextRepo runningTextRepo;



	@Transactional
	public String saveProject(Project_text project_text, MultipartFile file) {
		Map<?, ?> cloudinary_response;
		try {
			cloudinary_response = SaveImg(file);
		} catch (IOException e) {
			e.printStackTrace();
			return e.getMessage();
		}

		String secure_url = cloudinary_response.get("secure_url").toString();
		String public_id = cloudinary_response.get("public_id").toString();

		try {

			Project map = modelMapper.map(project_text, Project.class);
			map.setImage_url(secure_url);
			map.setImg_public_id(public_id);

			projectRepository.save(map);

			return "done";

		} catch (Exception e) {

			try {
				cloudinary.uploader().destroy(public_id, ObjectUtils.asMap("folder", "My_Portfolio"));
			} catch (IOException e2) {
				return e2.getMessage();
			}

			return e.getMessage();
		}

	}

	@Transactional
	public String DeleteProject(Long id) {
		Optional<Project> optional = projectRepository.findById(id);
		if (optional.isPresent()) {
			Project project = (Project) optional.get();
			String img_public_id = project.getImg_public_id();
			try {
				Map<?, ?> destroy = cloudinary.uploader().destroy(img_public_id,
						ObjectUtils.asMap("folder", "My_Portfolio"));
				projectRepository.deleteById(id);
			} catch (IOException e) {
				return e.getMessage();
			}
		}

		return "deleted";
	}

	private Map<?, ?> SaveImg(MultipartFile file) throws IOException {
		Map<?, ?> uploadResults = cloudinary.uploader().upload(file.getBytes(),
				ObjectUtils.asMap("folder", "My_Portfolio"));
		return uploadResults;
	}

	@Transactional
	public String[] updateResume(MultipartFile file) {
		String[] ans = { "", "" };

		Map<?, ?> cloudinary_response;
		try {
			cloudinary_response = SaveImg(file);
		} catch (IOException e) {
			e.printStackTrace();
			ans[1] = "Cloudinary upload failed: " + e.getMessage();
			return ans;
		}

		String secure_url = cloudinary_response.get("secure_url").toString();
		String public_id = cloudinary_response.get("public_id").toString();

		try {
			
			Optional<Resume> data = resumeRepo.findById(1);
			String old_public_id="";
			if(data.isPresent()) {
				Resume old_resume = data.get();
				old_public_id=old_resume.getResume_publi_id();
				resumeRepo.deleteAll();
			}

			Resume resume=new Resume();
			resume.setId(1);
			resume.setResume_url(secure_url);
			resume.setResume_publi_id(public_id);

			Resume save = resumeRepo.save(resume);
			ans[0] = save.getResume_url();
			
			if(!old_public_id.equals("")) {
				cloudinary.uploader().destroy(old_public_id, ObjectUtils.asMap("folder", "My_Portfolio"));
			}
			
			return ans;

		} catch (Exception e) {
			try {
				cloudinary.uploader().destroy(public_id, ObjectUtils.asMap("folder", "My_Portfolio"));
			} catch (IOException ioException) {
	            System.err.println("Critical: Failed to delete orphaned Cloudinary asset " + public_id);
	            ioException.printStackTrace();
	        }    
	        throw new RuntimeException("Database save failed. Cloudinary file rolled back.", e);
		}
	}
	
	
	public List<RunningText> findAllRunningText(){
		List<RunningText> all = runningTextRepo.findAll();
		return all;
	}
	
	
	
	public List<RunningText> saveRunningText(List<String> texts){
		
		List<RunningText> forSave=new ArrayList<>();
		
		for(String text : texts) {
			RunningText runningText=new RunningText();
			runningText.setText(text);
			forSave.add(runningText);
		}
		
		List<RunningText> saveAll = runningTextRepo.saveAll(forSave);
		
		return saveAll;
		
	}
	
	public String deleteRunnigText(int id) {
		
		Optional<RunningText> byId = runningTextRepo.findById(id);
		if (!byId.isPresent()) {
			return "Not found";
		}
		
		runningTextRepo.deleteById(id);
		
		return "deleted";
	}

}
