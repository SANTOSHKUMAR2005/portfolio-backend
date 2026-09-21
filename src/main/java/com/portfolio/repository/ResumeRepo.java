package com.portfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.portfolio.model.Resume;

public interface ResumeRepo extends JpaRepository<Resume, Integer> {
     
}
