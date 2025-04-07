package co.analisys.entrenador;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class EntrenadorServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EntrenadorServiceApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
    public WebClient webClient() {
        return WebClient.builder()
                .filter(new ServletBearerExchangeFilterFunction())
                .build();
    }
}
