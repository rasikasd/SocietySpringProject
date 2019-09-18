package com.ras.soc;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ras.soc.entity.Outstanding;
import com.ras.soc.entity.Owner;
import com.ras.soc.repo.BillRepository;
import com.ras.soc.repo.OutstandingRepository;
import com.ras.soc.repo.OwnerRepository;
import com.ras.soc.repo.ReceiptRepository;

@RestController
@RequestMapping("/")
public class OutstandingController {

	private static final Logger logger = LoggerFactory.getLogger(OutstandingController.class);
	
	@Autowired
	ReceiptRepository rcptRepo;
	
	@Autowired
	OwnerRepository ownerRepo;
	
	@Autowired
	BillRepository billRepo;
	
	@Autowired
	OutstandingRepository outRepo;
	
	
	@GetMapping("/outs")
	public List<Outstanding> getListOutstandings()
	{
		List<Outstanding>outstandings = outRepo.findAll();
		
		return outstandings;
	}
	
	@GetMapping("/outs/{id}")
	public Optional<Outstanding> getOutstanding(@PathVariable Integer id)
	{
		return outRepo.findById(id);
			
	}
	
}
