package com.ras.soc;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.ras.soc.entity.Bill;
import com.ras.soc.entity.BillGen;
import com.ras.soc.entity.Owner;
import com.ras.soc.repo.BillGenRepository;
import com.ras.soc.repo.BillRepository;
import com.ras.soc.repo.OwnerRepository;
import com.ras.soc.util.CustomErrorType;

@RestController
@RequestMapping("/")
public class BillGenController {

private static final Logger logger = LoggerFactory.getLogger(BillGenController.class);
	
	@Autowired
	BillGenRepository billGenRepo;
	
	@Autowired
	OwnerRepository ownerRepo;
	
	@Autowired
	BillRepository billRepo;
	private Float perMonthmtc=2000.0f;
	
	private Integer billFreq=2; 
	
	private Float NonOccup=200.0f;
	
	@GetMapping("/allgenbills")
	public List<BillGen> getGenBills()
	{		

		return billGenRepo.findAll();
		
	}
	
	@GetMapping("/genbill/{id}")
	public Optional<BillGen> getGenBill(@PathVariable Integer id)
	{
		return billGenRepo.findById(id);
			
	}
	
	@PostMapping("/genbill")
//	@JsonDeserialize(using = LocalDeserializer.class)
	public ResponseEntity<BillGen> createGenBill(@RequestBody BillGen genbill) throws URISyntaxException {
		
		System.out.println(genbill);
		if (genbill.getId() != 0) {
		logger.error("unable to generate bill as start date is: ",genbill.getBillgenstart());
		return new ResponseEntity(new CustomErrorType("bill already generated for this end date  " + genbill.getBillgenstart() ),HttpStatus.CONFLICT);
		} 
		Date result1 = billGenRepo.findMaxDate();
		System.out.println("max date from table "+result1);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.setTime(result1);
		c.add(Calendar.DATE, 1);
		result1 = c.getTime();
		
		Date duedate = genbill.getBillgenstart();
		c.setTime(duedate);
		c.add(Calendar.DATE,15);
		duedate = c.getTime();
		
		String date1=sdf.format(result1); //from database end date+1
		
		String date2 = sdf.format(genbill.getBillgenstart()); //from android
		
		System.out.println("andr start date "+date1);
		
		System.out.println(" query +1 : "+date2);
		if(date1.equals(date2))
		{
			//bill gen proc
			Set<Bill> billset = new HashSet<Bill>();
			Long totalowners = ownerRepo.count();
			List<Owner>owners = ownerRepo.findAll();
			System.out.println(totalowners);
			//List<Owner>ownerList = ownerRepo.findAll();
			
			int count = 0; 
			
			for(Owner o : owners)
			{
				billset = o.getBills();
				
				if(billset.isEmpty())
				{
					Bill newBill = new Bill();
					newBill.setAdjustment(0.0f);
					newBill.setPrevbillamt(0.0f);
					newBill.setCurrcharges(perMonthmtc *billFreq);
					newBill.setBillamount(newBill.getAdjustment() + newBill.getCurrcharges() + newBill.getPrevbillamt());
					newBill.setPayment(0.0f);
					newBill.setDuedate(duedate);
					newBill.setBilldate(genbill.getBillgenstart());
					newBill.setTotalamt(newBill.getBillamount());
					newBill.setOwner(o);
					billRepo.save(newBill);
					
				}
				else {
				
					try {
						for(Bill b : billset )
						{
							Bill newBill = new Bill();
							newBill.setPrevbillamt(b.getTotalamt());
							newBill.setAdjustment(0.0f);
							newBill.setCurrcharges(perMonthmtc *billFreq);
							newBill.setBillamount(newBill.getAdjustment() + newBill.getCurrcharges() + newBill.getPrevbillamt());
							newBill.setPayment(0.0f);
							newBill.setTotalamt(newBill.getBillamount());
							newBill.setDuedate(duedate);
							newBill.setBilldate(genbill.getBillgenstart());
							newBill.setOwner(o);
							billRepo.save(newBill);
							
						}
					
					}catch(Exception e)
					{
						System.out.println("Bill not found for owner id: "+o.getId());
					}
				}
				count++;
			}
			genbill.setTotalgenbill(count);
			BillGen result = billGenRepo.save(genbill);
			//bill gen proc
			return  ResponseEntity.created(new URI("/genbills/" ))
					.body(result);
		}
		else
		{
			return  ResponseEntity.badRequest().body(null);
		}
		
	}
	
}
