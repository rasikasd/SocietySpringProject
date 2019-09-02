package com.ras.soc.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ras.soc.entity.Bill;


public interface BillRepository extends JpaRepository<Bill, Integer>{

	
}
