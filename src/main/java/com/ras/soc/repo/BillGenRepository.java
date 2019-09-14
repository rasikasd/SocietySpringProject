package com.ras.soc.repo;

import java.util.Date;
import java.time.Instant;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ras.soc.entity.BillGen;



public interface BillGenRepository extends JpaRepository<BillGen, Integer>{

	@Query("select max(bg.billgenend) from BillGen bg")
	public Date findMaxDate();
	
}
