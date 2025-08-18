package services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;


import entity.UserEntry;
import repository.UserEntryRepository;


class UserDetailServiceIMPLTest {

	@InjectMocks
	private UserDetailServiceIMPL userDetailServiceIMPL;
	
	@Mock
	private UserEntryRepository userEntryRepository;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	void loadUserByUsernameTest() {
		when(userEntryRepository.findByUserName(ArgumentMatchers.anyString()))
								.thenReturn(UserEntry.builder()
										.userName("Luf")
										.password("One")
										.roles(new ArrayList<>())
										.build());
		UserDetails user = userDetailServiceIMPL.loadUserByUsername("Luffy");
		assertNotNull(user);
	}

}
