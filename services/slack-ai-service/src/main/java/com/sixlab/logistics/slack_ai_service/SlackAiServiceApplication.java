package com.sixlab.logistics.slack_ai_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SlackAiServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SlackAiServiceApplication.class, args);
	}

}
