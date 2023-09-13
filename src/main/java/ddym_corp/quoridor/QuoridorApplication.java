package ddym_corp.quoridor;

import ddym_corp.quoridor.history.repository.HistoryRepository;
import ddym_corp.quoridor.match.background.BackgroundMatchLogic;
import ddym_corp.quoridor.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@EnableCaching
@SpringBootApplication
public class QuoridorApplication {
	public static void main(String[] args) {
		SpringApplication.run(QuoridorApplication.class, args);
	}

	@Autowired
	private BackgroundMatchLogic backgroundMatchLogic;
	public static void threadStart(BackgroundMatchLogic backgroundMatchLogic) throws InterruptedException {
		backgroundMatchLogic.run();
	}
	@Bean
	public CommandLineRunner commandLineRunner(){
		return args -> threadStart(backgroundMatchLogic);
	}

	@Bean
	@Profile("local")
	public TestDataInit testDataInit(UserRepository userRepository, HistoryRepository historyRepository){
		return new TestDataInit(userRepository, historyRepository);
	}

}
