package com.ras.soc.repo;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ras.soc.entity.Outstanding;

public interface OutstandingRepository extends JpaRepository<Outstanding, Integer>{

	Outstanding findByOwner_Id(Integer ownerId);

}
