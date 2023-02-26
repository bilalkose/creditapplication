package com.bilalkose.creditapplication;

import com.bilalkose.creditapplication.model.Customer;
import com.bilalkose.creditapplication.repository.CustomerRepository;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import lombok.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Set;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class} )
public class CreditApplicationMain {

	private final CustomerRepository customerRepository;
	public CreditApplicationMain(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(CreditApplicationMain.class, args);
	}

	@Bean
	public OpenAPI customOpenAPI(){
		return new OpenAPI()
				.info(new Info()
						.title("Credit Application API")
						.description("DefineX Java Spring Practicum"));
	}

}
