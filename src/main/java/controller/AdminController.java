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
import services.UserEntryServices;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private UserEntryServices userEntryServices;
	
	@GetMapping("GetAllUsers")
	public ResponseEntity<List<UserEntry>> getAllUsers() {
		List<UserEntry> users = userEntryServices.getUserEntry();
		if(!users.isEmpty()) {
			return new ResponseEntity<>(users, HttpStatus.FOUND);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@PostMapping("CreateAdmin")
	public ResponseEntity<?> createAdminEntry(@RequestBody UserEntry userEntry) {
		userEntryServices.saveAdmin(userEntry);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
}
