package services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import dto.UserEntryDTO;
import entity.UserEntry;
import repository.UserEntryRepository;

@ExtendWith(MockitoExtension.class)
public class UserEntryTest {
	
	@Mock
	private UserEntryServices userEntryServices;
	
	private UserEntryRepository userEntryRepository;
	private UserEntry user;
	
	@BeforeEach
	void setUp() {
		user = UserEntry.builder()
                .id(new ObjectId())
                .userName("johndoe")
                .password("password")
                .roles(List.of("User"))
                .journalEntries(new ArrayList<>())
                .build();
	}

	void saveEntry_ShouldSaveUserEntrywithPasswordEncoded() {
		UserEntryDTO dto = new UserEntryDTO();
		dto.setUserName("Hello");
		dto.setPassword("howareyou");
		
		
		when(userEntryRepository.save(any(UserEntry.class))).thenReturn(user);
		
		UserEntry result = userEntryServices.saveUserEntryEncoded(dto);
		
		assertNotNull(result);
		assertEquals("Hello",result.getUserName());
		assertTrue(result.getPassword().startsWith("$2a$"));
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Disabled
	@ParameterizedTest
	@ArgumentsSource(UserArgumentsProvider.class)
	public void testSaveUserDetails(UserEntryDTO userEntryDTO) {
		assertNotNull(userEntryServices.saveUserEntryEncoded(userEntryDTO) );
	}

	@Test
    void contextLoads() {
        assertTrue(true);
    }
	
	@ParameterizedTest
	@CsvSource({
		"1,2,3",
		"2,3,5",
		"3,3,6"
	})
	public void testAdding(int a, int b, int sum) {
		assertEquals(sum, a+b);
	}
}
