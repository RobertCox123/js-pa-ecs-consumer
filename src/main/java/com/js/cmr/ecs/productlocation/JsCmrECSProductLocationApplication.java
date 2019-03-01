package com.js.cmr.ecs.productlocation;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class JsCmrECSProductLocationApplication {

	public static void main(String[] args) {
		SpringApplication.run(JsCmrECSProductLocationApplication.class, args);
	}


}
