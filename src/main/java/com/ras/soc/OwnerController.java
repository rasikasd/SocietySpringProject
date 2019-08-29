package com.ras.soc;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

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
		return ownerRepo.findAll();
	}
	
	
	@PostMapping("/owner")
	public ResponseEntity<Owner> createOwner(@RequestBody Owner owner) throws URISyntaxException {
		if (owner.getId() != null) {
		logger.error("unable to create user with name {} already exists",owner.getFirstname());
		return new ResponseEntity(new CustomErrorType("user with id " + owner.getId() + " already exists"),HttpStatus.CONFLICT);
		} 
			Owner result = ownerRepo.save(owner);
			return  ResponseEntity.created(new URI("/owners/" + result.getId()))
					.body(result);
	}
	
	@PutMapping("/owner")
	public ResponseEntity<Owner> updateOwner(Owner owner)
	{
		if(owner.getId() == null)
		{
			logger.error("unable to  update user with id {} not found ",owner.getId());
		}
		
		 Owner result = ownerRepo.save(owner);
	        return ResponseEntity.ok()
	   	            .body(result);
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