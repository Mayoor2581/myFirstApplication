package controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import entity.UserEntry;
import services.UserEntryServices;

@RestController
@RequestMapping("/User")
public class UserEntryController {
	
	private final UserEntryServices userEntryServices;
	
	public UserEntryController(UserEntryServices userEntryServices){
		this.userEntryServices = userEntryServices;
	}
		
	@PutMapping("/UpdateUser")
	public ResponseEntity<?> updateUserEntry(@RequestBody UserEntry newUserEntry){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String oldUserName = authentication.getName();
		UserEntry old = userEntryServices.findUserByName(oldUserName);		
		old.setUserName(newUserEntry.getUserName());
		old.setPassword(newUserEntry.getPassword());
		userEntryServices.saveUserEntryEncoded(old);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@GetMapping("/GetUserEntry")
	public ResponseEntity<UserEntry> getUserDetails(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userName = authentication.getName();
		UserEntry r = userEntryServices.findUserByName(userName);
		if(r!=null) {
			return new ResponseEntity<>(r,HttpStatus.FOUND);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@DeleteMapping("/DeleteUser")
	public ResponseEntity<UserEntry> deleteByName(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String user = authentication.getName();
		userEntryServices.deleteUserByName(user);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
