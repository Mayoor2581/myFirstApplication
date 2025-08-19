package services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import entity.UserEntry;
import repository.UserEntryRepository;

@Component
public class UserDetailServiceIMPL implements UserDetailsService{
	
	private final UserEntryRepository userEntryRepository;
	
	public UserDetailServiceIMPL(UserEntryRepository userEntryRepository){
		this.userEntryRepository = userEntryRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntry user = userEntryRepository.findByUserName(username);
		if(user != null) {
			return org.springframework.security.core.userdetails.User.builder()
					.username(user.getUserName())
					.password(user.getPassword())
					.roles(user.getRoles().toArray(new String[0]))
					.build();
			
		}
		throw new UsernameNotFoundException("User Name not found"+username);
	}

}
