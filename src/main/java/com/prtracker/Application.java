package com.prtracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	static void main(String[] args) {
		new SpringApplication(Application.class).run(args);
	}
}
