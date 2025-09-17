package services;

import static org.junit.jupiter.api.Assertions.*;

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

@ExtendWith(MockitoExtension.class)
public class UserEntryTest {
	
	@Mock
	private UserEntryServices userEntryServices;
	
	
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
