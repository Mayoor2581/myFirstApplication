package controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import entity.UserEntry;
import lombok.extern.slf4j.Slf4j;
import services.UserEntryServices;

@Slf4j
@RestController
@RequestMapping("/public")
public class PublicController {
	
	private final UserEntryServices userEntryServices;

	public PublicController(UserEntryServices userEntryServices){
		this.userEntryServices = userEntryServices; 
	}
	
	@GetMapping("/healthCheck")
	public void healthCheck() {
		log.info("good");
	}
	
	@GetMapping("/GetUsers")
	public ResponseEntity<List<UserEntry>> getUserEntry(){
		return new ResponseEntity<>(userEntryServices.getUserEntry(),HttpStatus.OK);
	}
	
	@PostMapping("/CreateUser")
	public ResponseEntity<UserEntry> saveUserEntry(@RequestBody UserEntry userEntry){	
		log.info("hello");
		try {
			userEntryServices.saveUserEntryEncoded(userEntry);
			return new ResponseEntity<>(userEntry,HttpStatus.CREATED);
		}
		catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
}
