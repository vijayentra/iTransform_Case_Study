package com.admin.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.admin.entity.CustomerOverview;

public interface CustomerOverviewRepository extends JpaRepository<CustomerOverview, Long>{
	
	@Transactional
    @Modifying
    @Query(value = "ALTER TABLE customer_overview AUTO_INCREMENT = 1", nativeQuery = true)
    void resetAutoIncrementValue();
}
