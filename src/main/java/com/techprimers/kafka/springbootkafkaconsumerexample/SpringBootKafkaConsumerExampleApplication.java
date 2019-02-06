package com.techprimers.kafka.springbootkafkaconsumerexample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringBootKafkaConsumerExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootKafkaConsumerExampleApplication.class, args);
	}
}
