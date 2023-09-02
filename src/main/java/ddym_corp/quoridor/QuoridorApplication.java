package ddym_corp.quoridor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class QuoridorApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuoridorApplication.class, args);
	}

}
