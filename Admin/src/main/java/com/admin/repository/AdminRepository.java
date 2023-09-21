package com.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.admin.entity.AdminDetails;

@Repository
public interface AdminRepository extends JpaRepository<AdminDetails, Long>{
		AdminDetails findByUsername(String username);
}
