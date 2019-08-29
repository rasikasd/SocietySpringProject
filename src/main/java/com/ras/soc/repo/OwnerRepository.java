package com.ras.soc.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ras.soc.entity.Owner;

public interface OwnerRepository extends JpaRepository<Owner, Integer>
{
	
}
