package services;

import java.util.Arrays;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import dto.UserEntryDTO;
import entity.UserEntry;
import repository.UserEntryRepository;

@Service
public class UserEntryServices {
	
	private final UserEntryRepository userEntryRepository;
	private final PasswordEncoder passwordEncoder;
	
	public UserEntryServices(UserEntryRepository userEntryRepository, PasswordEncoder passwordEncoder){
		this.userEntryRepository = userEntryRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	//I may use it sometime later
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
	
	public UserEntry saveUserEntryEncoded(UserEntryDTO userEntryDTO) {
		UserEntry userEntry = new UserEntry();
		userEntry.setUserName(userEntryDTO.getUserName());
		userEntry.setPassword(passwordEncoder.encode(userEntryDTO.getPassword()));
		userEntry.setRoles(Arrays.asList("User"));
		return userEntryRepository.save(userEntry);
	}
	
	public UserEntry saveAdmin(UserEntryDTO userEntryDTO) {
		UserEntry userEntry = new UserEntry();
		userEntry.setUserName(userEntryDTO.getUserName());
		userEntry.setPassword(passwordEncoder.encode(userEntryDTO.getPassword()));
		userEntry.setRoles(Arrays.asList("User","ADMIN"));
		return userEntryRepository.save(userEntry);
	}
	
	public UserEntry findUserByName(String UserName) {
		return userEntryRepository.findByUserName(UserName);
	}

	public void deleteUserByName(String userEntry) {
		userEntryRepository.deleteByUserName(userEntry);
	}

	
}
