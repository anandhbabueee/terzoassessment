package com.terzo.ab;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.terzo.ab.model.Employee;
import com.terzo.ab.repository.EmployeeRepository;

//@Configuration
public class DataBaseInitializer {

	//@Bean
	  CommandLineRunner initDatabase(EmployeeRepository repository) {

	    return args -> {
	      //log.info("Preloading " + repository.save(new Employee("Bilbo Baggins", "burglar")));
	      //log.info("Preloading " + repository.save(new Employee("Frodo Baggins", "thief")));
	    };
	  }
}
