package com.portfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.portfolio.model.RunningText;

@Repository
public interface RunningTextRepo extends JpaRepository<RunningText, Integer> {

}
