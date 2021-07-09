package com.example.softwarebackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({"controller"})
@SpringBootApplication
public class SoftwarebackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(SoftwarebackendApplication.class, args);
	}

}
