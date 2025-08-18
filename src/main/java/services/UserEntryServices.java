package services;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import entity.UserEntry;
import repository.UserEntryRepository;

@Service
public class UserEntryServices {
	@Autowired 
	private UserEntryRepository userEntryRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	//private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//	@PostConstruct
//	public void existingPasswordEncoder() {
//		List<UserEntry> users = userEntryRepository.findAll();
//		for(UserEntry user : users) {
//			if(!user.getPassword().startsWith("$2a$")) {
//				user.setPassword(passwordEncoder.encode(user.getPassword()));
//				userEntryRepository.save(user);
//			}
//		}
//		System.out.println("changed existing password");
//	}
	
	public List<UserEntry> getUserEntry(){
		return userEntryRepository.findAll();
	}
	
	public UserEntry saveUserEntry(UserEntry userEntry) {
		return userEntryRepository.save(userEntry);
	}
	
	public UserEntry saveUserEntryEncoded(UserEntry userEntry) {
		userEntry.setPassword(passwordEncoder.encode(userEntry.getPassword()));
		userEntry.setRoles(Arrays.asList("User"));
		return userEntryRepository.save(userEntry);
	}
	
	public UserEntry findUserByName(String UserName) {
		return userEntryRepository.findByUserName(UserName);
	}

	public void deleteUserByName(String userEntry) {
		userEntryRepository.deleteByUserName(userEntry);
	}

	public UserEntry saveAdmin(UserEntry userEntry) {
		userEntry.setPassword(passwordEncoder.encode(userEntry.getPassword()));
		userEntry.setRoles(Arrays.asList("User","ADMIN"));
		return userEntryRepository.save(userEntry);
	}
}
