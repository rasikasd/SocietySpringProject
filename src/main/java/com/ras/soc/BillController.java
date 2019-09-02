package com.ras.soc;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ras.soc.entity.Bill;

import com.ras.soc.repo.BillRepository;
import com.ras.soc.util.CustomErrorType;

@RestController
@RequestMapping("/")
public class BillController {

	private static final Logger logger = LoggerFactory.getLogger(BillController.class);
	
	@Autowired
	BillRepository billRepo;
	
	@GetMapping("/allbills")
	public List<Bill> getBills()
	{
		return billRepo.findAll();
	}
	
	@GetMapping("/bill/{id}")
	public Optional<Bill> getBill(@PathVariable Integer id)
	{
		return billRepo.findById(id);
			
	}
	
	@PostMapping("/bill")
	public ResponseEntity<Bill> createBill(@RequestBody Bill bill) throws URISyntaxException {
		
		System.out.println(bill);
		if (bill.getId() != 0) {
		logger.error("unable to create bill with already exists",bill.getBillamount());
		return new ResponseEntity(new CustomErrorType("bill with id " + bill.getId() + " already exists"),HttpStatus.CONFLICT);
		} 
			Bill result = billRepo.save(bill);
			return  ResponseEntity.created(new URI("/bills/" + result.getId()))
					.body(result);
	}
	
	
	
	
}
