package com.sixlab.logistics.slack_ai_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableFeignClients
@ComponentScan(basePackages = {"com.sixlab.logistics.slack_ai_service","com.sixlab.logistics.common"})
public class SlackAiServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SlackAiServiceApplication.class, args);
	}

}
