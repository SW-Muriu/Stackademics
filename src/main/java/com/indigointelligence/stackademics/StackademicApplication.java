package com.indigointelligence.stackademics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StackademicApplication {

	public static void main(String[] args) {
		SpringApplication.run(StackademicApplication.class, args);
		System.out.println("System is running!! : port 3324");
	}

}
