package sawan.dev.NutriTrackAI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class NutriTrackAiApplication {

	public static void main(String[] args) {
		SpringApplication.run(NutriTrackAiApplication.class, args);
	}

}
