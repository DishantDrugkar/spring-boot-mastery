package com.example.springboot_series;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SpringbootSeriesApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootSeriesApplication.class, args);
	}

}
