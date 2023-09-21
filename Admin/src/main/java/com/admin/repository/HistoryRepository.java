package com.admin.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.admin.entity.HistoryOfBookings;

public interface HistoryRepository extends JpaRepository<HistoryOfBookings, Long>{
		
	@Transactional
    @Modifying
    @Query(value = "ALTER TABLE history_of_bookings AUTO_INCREMENT = 1", nativeQuery = true)
    void resetAutoIncrementValue();
}
