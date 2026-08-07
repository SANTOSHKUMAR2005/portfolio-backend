package com.portfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.portfolio.model.Admin;

public interface AdminRepo extends JpaRepository<Admin, String> {

}
