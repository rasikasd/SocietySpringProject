package com.ras.soc;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

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
import com.ras.soc.entity.Outstanding;
import com.ras.soc.entity.Owner;
import com.ras.soc.repo.BillGenRepository;
import com.ras.soc.repo.BillRepository;
import com.ras.soc.repo.OutstandingRepository;
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
	
	@Autowired
	OutstandingRepository outRepo;
	
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
		/*LocalDateTime result1 = billGenRepo.findMaxDate();
		System.out.println("max date from table "+result1);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");*/
		
		/*Calendar c = Calendar.getInstance();
		c.setTime(result1);
		c.add(Calendar.DATE, 1);
		result1 = c.getTime();
		*/
		
		/*result1 = result1.plusDays(1);
		
		LocalDateTime duedate = genbill.getBillgenstart();*/
		
		/*c.setTime(duedate);
		c.add(Calendar.DATE,15);
		duedate = c.getTime();
		*/
		
		/*duedate = duedate.plusDays(15);
		System.out.println("ok ................");
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date1 = result1.format(formatter);
        String date2 = genbill.getBillgenstart().format(formatter);*/
		//String date1=sdf.format(result1); //from database end date+1
		
		//String date2 = sdf.format(genbill.getBillgenstart()); //from android
		
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
		
		Integer freq = genbill.getBillfreq();
		
		System.out.println("hi.........billfreq : "+freq+" genbill.getBillfreq"+ genbill.getBillfreq());
		Date billend = genbill.getBillgenstart();
		c.setTime(billend);
		c.add(Calendar.MONTH,freq);
		billend = c.getTime();
		
		c.setTime(billend);
		c.add(Calendar.DATE, -1);
		billend = c.getTime();
		
		System.out.println("hi.........billend : "+billend);
		genbill.setBillgenend(billend);
		String date1=sdf.format(result1); //from database end date+1
		
		String date2 = sdf.format(genbill.getBillgenstart()); //from android
		System.out.println("andr start date "+date1);
		
		System.out.println(" query +1 : "+date2);
		if(date1.equals(date2))
		{
			//bill gen proc
			Set<Bill> billset = new TreeSet<Bill>();
				
			
			//Comparator<Bill> comp = new BillSort();
			//Collections.sort(billset,comp);
			
			Long totalowners = ownerRepo.count();
			List<Owner>owners = ownerRepo.findAll();
			System.out.println(totalowners);
			//List<Owner>ownerList = ownerRepo.findAll();
			
			int count = 0; 
			
			for(Owner o : owners)
			{
				billset = o.getBills();
				Integer	maxid=0;
				if(!billset.isEmpty())
				{
					for(Bill b : billset )
					{
							if(maxid<b.getId())
							{
								maxid = b.getId();
							}
					}
				}
				
				if(billset.isEmpty())
				{
					Outstanding outs = new Outstanding();
					outs.setOwner(o);
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
					Bill savedBill =billRepo.save(newBill);
					outs.setBill_id(savedBill.getId());
					outs.setBillamount(savedBill.getTotalamt());
					outs.setTotalpayment(0.0f);
					outs.setTotalamt(savedBill.getTotalamt());
					outRepo.save(outs);
					
				}
				else {
				
					try {
						for(Bill b : billset )
						{
							if(b.getId() == maxid)
							{
								Bill newBill = new Bill();     // to get amount from recent bill maxid = getid
								newBill.setPrevbillamt(b.getTotalamt());
								newBill.setAdjustment(0.0f);
								newBill.setCurrcharges(perMonthmtc *genbill.getBillfreq());
								newBill.setBillamount(newBill.getAdjustment() + newBill.getCurrcharges() + newBill.getPrevbillamt());
								newBill.setPayment(0.0f);
								newBill.setTotalamt(newBill.getBillamount());//billamt-payment
								newBill.setDuedate(duedate);
								newBill.setBilldate(genbill.getBillgenstart());
								newBill.setOwner(o);
								Bill savedBill = billRepo.save(newBill);
								Outstanding outs = new Outstanding();
								outs = outRepo.findByOwner_Id(o.getId());
								outs.setBill_id(savedBill.getId());
								outs.setBillamount(savedBill.getTotalamt());
								outs.setTotalpayment(0.0f);
								outs.setTotalamt(savedBill.getTotalamt());
								outRepo.save(outs);
								
							}
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
