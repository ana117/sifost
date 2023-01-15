package id.ac.ui.cs.advancedprogramming.sifost;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;

import org.springframework.web.client.RestTemplate;


@SpringBootApplication
@PropertySource("classpath:application-prod.properties")
@EnableDiscoveryClient
public class SifostApplication {

	public static void main(String[] args) {
		SpringApplication.run(SifostApplication.class, args);
	}
	@LoadBalanced
	@Bean
	public RestTemplate restTemplate(){ return new RestTemplate();}

}
