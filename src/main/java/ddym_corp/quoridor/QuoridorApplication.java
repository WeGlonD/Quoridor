package ddym_corp.quoridor;

import ddym_corp.quoridor.history.repository.HistoryRepository;
import ddym_corp.quoridor.match.background.min10.BackgroundMatchLogic10Min;
import ddym_corp.quoridor.match.background.min3.BackgroundMatchLogic3Min;
import ddym_corp.quoridor.match.background.min30.BackgroundMatchLogic30Min;
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
	private BackgroundMatchLogic3Min backgroundMatchLogic3Min;
	@Autowired
	private BackgroundMatchLogic10Min backgroundMatchLogic10Min;
	@Autowired
	private BackgroundMatchLogic30Min backgroundMatchLogic30Min;
	public static void threadStart(BackgroundMatchLogic3Min backgroundMatchLogic3Min,
								   BackgroundMatchLogic10Min backgroundMatchLogic10Min,
								   BackgroundMatchLogic30Min backgroundMatchLogic30Min) throws InterruptedException {
		backgroundMatchLogic3Min.run();
		backgroundMatchLogic10Min.run();
		backgroundMatchLogic30Min.run();
	}
	@Bean
	public CommandLineRunner commandLineRunner(){
		return args -> threadStart(backgroundMatchLogic3Min, backgroundMatchLogic10Min, backgroundMatchLogic30Min);
	}

	@Bean
	@Profile("local")
	public TestDataInit testDataInit(UserRepository userRepository, HistoryRepository historyRepository){
		return new TestDataInit(userRepository, historyRepository);
	}

}
