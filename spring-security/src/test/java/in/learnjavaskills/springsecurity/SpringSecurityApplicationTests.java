package in.learnjavaskills.springsecurity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

@ExtendWith(MockitoExtension.class)
class SpringSecurityApplicationTests {

	@Test
	void contextLoads() {
		String date = "2023-02-21 11:34:34.0";
		LocalDateTime localDateTime = LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S"));
		System.out.println(localDateTime);
	}

}
