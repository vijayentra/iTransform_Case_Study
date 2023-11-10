package com.admin.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.admin.entity.AdminDetails;

@Repository
public interface AdminRepository extends JpaRepository<AdminDetails, Long>{
		Optional<AdminDetails> findByUsername(String username);
		Optional<AdminDetails> findByUsernameAndPassword(String username, String password);
}
