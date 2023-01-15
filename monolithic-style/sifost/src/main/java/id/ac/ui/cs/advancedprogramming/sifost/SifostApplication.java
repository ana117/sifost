package id.ac.ui.cs.advancedprogramming.sifost;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:application-prod.properties")
public class SifostApplication {

	public static void main(String[] args) {
		SpringApplication.run(SifostApplication.class, args);
	}

}
