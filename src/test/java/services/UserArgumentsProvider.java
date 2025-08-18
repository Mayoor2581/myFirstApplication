package services;

import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.springframework.boot.test.context.SpringBootTest;

import com.edigest.myFirstApplication.MyFirstApplication;

import entity.UserEntry;

@SpringBootTest(classes = MyFirstApplication.class)
public class UserArgumentsProvider implements ArgumentsProvider {

	@Override
	public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
		// TODO Auto-generated method stub
		return Stream.empty();//of(
//				Arguments.of(UserEntry.builder().userName("BlackClover").password("Asta").build()),
//				Arguments.of(UserEntry.builder().userName("JJK").password("Gojo").build()));
	}

}
