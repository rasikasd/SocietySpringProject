package com.ras.soc;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.apache.tomcat.util.http.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ras.soc.entity.Owner;
import com.ras.soc.repo.OwnerRepository;
import com.ras.soc.util.CustomErrorType;
import com.ras.soc.entity.Message;

@RestController
@RequestMapping("/")
public class OwnerController
{
	private static final Logger logger = LoggerFactory.getLogger(OwnerController.class);
	
	@Autowired
	OwnerRepository ownerRepo;
	
	@GetMapping("/all")
	public List<Owner> getOwners()
	{
		List<Owner>owners = ownerRepo.findAll();
		Long totalowners = ownerRepo.count();
		System.out.println(totalowners);
		//for(Owner o : owners){			System.out.println(o);	}
		
		return owners;
	}
	
	@GetMapping("/owner/{id}")
	public Optional<Owner> getOwner(@PathVariable Integer id)
	{
		return ownerRepo.findById(id);
			
	}
	
	@PostMapping("/owner")
	public ResponseEntity<Owner> createOwner(@RequestBody Owner owner) throws URISyntaxException {
		
		System.out.println(owner);
		if (owner.getId() != 0) {
		logger.error("unable to create user with name {} already exists",owner.getFirstname());
		return new ResponseEntity(new CustomErrorType("user with id " + owner.getId() + " already exists"),HttpStatus.CONFLICT);
		} 
			Owner result = ownerRepo.save(owner);
			return  ResponseEntity.created(new URI("/owners/" + result.getId()))
					.body(result);
	}
	
	
	//@RequestMapping(value = "/owner/{id}", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	//@PutMapping("/owner")
	@RequestMapping(value = "/owner/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Owner> updateOwner(@PathVariable("id") Integer id, @RequestBody Owner owner)
	{
		if(owner.getId() == 0)
		{
			logger.error("unable to  update user with id {} not found ",owner.getId());
			 return ResponseEntity.notFound().build();
		}
		else
		{
		System.out.println(owner);
		
		
		 Owner result = ownerRepo.save(owner);
		
	        return ResponseEntity.ok()
	   	            .body(result);
	        
		}
	}
	
	
	@RequestMapping(value = "/getmessage", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody Message getMessage() {
		logger.info("Accessing protected resource");
		return new Message(100, "Congratulations!", "I have accessed a Basic Auth protected resource.");
	}

	@RequestMapping(value = "/getmsg", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody Message getMsg() {
		logger.info("Accessing protected resource");
		return new Message(100, "hi!", "new rest .");
	}	
	
	
}
