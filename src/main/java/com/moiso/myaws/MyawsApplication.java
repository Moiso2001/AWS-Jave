package com.moiso.myaws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(AwsController.class)
public class MyawsApplication {
	     
	public static void main(String[] args) {
		SpringApplication.run(MyawsApplication.class, args);
	}

}
