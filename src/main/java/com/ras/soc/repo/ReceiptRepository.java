package com.ras.soc.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ras.soc.entity.Receipt;

public interface ReceiptRepository extends JpaRepository<Receipt, Integer>{

	Receipt findByOwnerId(Integer id);
	
}
