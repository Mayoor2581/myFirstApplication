package services;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.edigest.myFirstApplication.MyFirstApplication;

import entity.UserEntry;

@ExtendWith(MockitoExtension.class)
public class UserEntryTest {
	
	@Mock
	private UserEntryServices userEntryServices;
	
	
	@Disabled
	@ParameterizedTest
	@ArgumentsSource(UserArgumentsProvider.class)
	public void testSaveUserDetails(UserEntry userEntry) {
		assertNotNull(userEntryServices.saveUserEntryEncoded(userEntry) );
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
