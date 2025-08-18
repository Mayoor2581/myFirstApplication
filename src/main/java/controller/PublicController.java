package controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	private UserEntryServices userEntryServices;
	
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
		System.out.println("hello");
		try {
			userEntryServices.saveUserEntryEncoded(userEntry);
			return new ResponseEntity<>(userEntry,HttpStatus.CREATED);
		}
		catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
}
