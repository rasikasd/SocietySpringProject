package com.ras.soc;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

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
import com.ras.soc.entity.Outstanding;
import com.ras.soc.entity.Owner;
import com.ras.soc.entity.Receipt;
import com.ras.soc.repo.BillRepository;
import com.ras.soc.repo.OutstandingRepository;
import com.ras.soc.repo.OwnerRepository;
import com.ras.soc.repo.ReceiptRepository;
import com.ras.soc.util.CustomErrorType;
import com.ras.soc.util.ResourceNotFoundException;

@RestController
@RequestMapping("/")
public class ReceiptController {
	
	private static final Logger logger = LoggerFactory.getLogger(ReceiptController.class);
	
	@Autowired
	ReceiptRepository rcptRepo;
	
	@Autowired
	OwnerRepository ownerRepo;
	
	@Autowired
	BillRepository billRepo;
	
	@Autowired
	OutstandingRepository outRepo;
	
	@GetMapping("/allreceipts")
	public List<Receipt> getReceipts()
	{
		return rcptRepo.findAll();
	}
	
	@GetMapping("/receipts/{id}")
	public Optional<Receipt> getReceipt(@PathVariable Integer id)
	{
		return rcptRepo.findById(id);
			
	}
	
	@PostMapping("/receipt/{ownerId}/{billId}")
	public ResponseEntity createReceipt(@PathVariable (value = "ownerId") Integer ownerId, @PathVariable (value = "billId") Integer billId,
            @Valid @RequestBody Receipt receipt) throws URISyntaxException {
		
		System.out.println(receipt);
		if (receipt.getId() != null || ownerId == null || billId == null) {
			logger.error("unable to create receipt ",receipt.getId());
			return new ResponseEntity(new CustomErrorType("receipt with id " + receipt.getId() + " already exists"),HttpStatus.CONFLICT);
		} 
		
	
		/*
		
		Receipt rec = ownerRepo.findById(ownerId)
				.map(owner -> {
								receipt.setOwner(owner);
								return rcptRepo.save(receipt);
				})
				.orElseThrow(() -> new ResourceNotFoundException("ownerId " + ownerId + " not found"));
		*/
		ownerRepo.findById(ownerId)
		.map(owner -> {
						receipt.setOwner(owner);
						return 1;
		}).orElseThrow(() -> new ResourceNotFoundException("ownerId " + ownerId + " not found"));
		
		billRepo.findById(billId)
		.map(bill -> {
						receipt.setBill(bill);
						return 1;
		}).orElseThrow(() -> new ResourceNotFoundException("billId " + billId + " not found"));
		
		Receipt rec = rcptRepo.save(receipt);
		
		Bill bill = billRepo.getOne(billId);
		bill.setPayment(bill.getPayment() + receipt.getPaidamt());
		bill.setTotalamt(bill.getTotalamt()-receipt.getPaidamt());
		
		billRepo.save(bill);
		
		Outstanding out = outRepo.findByOwner_Id(ownerId);
		if(out == null)
		{
			
		}
		else
		{
			out.setTotalpayment(out.getTotalpayment() + rec.getPaidamt());
			out.setTotalamt(out.getTotalamt() - rec.getPaidamt());
			outRepo.save(out);
			if(out.getBillamount()-out.getTotalpayment() == out.getTotalamt())
			{
				logger.info(" outstanding is correct ...........................");
			}
			else
			{
				logger.error("unable to update outstanding of: ",out.getOwner().getLastname());
			}
		}
		System.out.println("hi......");
			return  ResponseEntity.created(new URI("/receipts/" + rec.getId()))
					.body(rec);
	}
}
