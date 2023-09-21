package com.admin.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.admin.entity.WasherOverview;

public interface WasherOverviewRepository extends JpaRepository<WasherOverview, Long>{

	@Transactional
    @Modifying
    @Query(value = "ALTER TABLE washer_overview AUTO_INCREMENT = 1", nativeQuery = true)
    void resetAutoIncrementValue();
}
