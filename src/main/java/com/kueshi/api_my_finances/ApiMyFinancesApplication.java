package com.kueshi.api_my_finances;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.TimeZone;

@SpringBootApplication
@EnableMongoRepositories
public class ApiMyFinancesApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiMyFinancesApplication.class, args);
	}

}
