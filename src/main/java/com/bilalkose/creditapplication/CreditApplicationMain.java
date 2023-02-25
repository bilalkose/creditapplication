package com.bilalkose.creditapplication;

import com.bilalkose.creditapplication.model.Customer;
import com.bilalkose.creditapplication.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

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

//	@Override
//	public void run(String... args) throws Exception {
//		Customer customer = customerRepository.save(new Customer("123456","Bilal",
//				"Köse",1000,"055555555",
//				LocalDateTime.now(),10, Set.of()));
//		System.out.println(customer);
//	}
}
