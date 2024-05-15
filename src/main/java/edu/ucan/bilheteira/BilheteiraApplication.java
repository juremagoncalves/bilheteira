package edu.ucan.bilheteira;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class BilheteiraApplication {

	public static void main(String[] args) {
		SpringApplication.run(BilheteiraApplication.class, args);
	}

}
