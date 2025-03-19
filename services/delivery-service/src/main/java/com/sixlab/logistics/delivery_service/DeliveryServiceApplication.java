package com.sixlab.logistics.delivery_service;

import com.sixlab.logistics.common.infrastructure.config.JpaConfig;
import com.sixlab.logistics.common.infrastructure.config.QueryDslConfig;
import com.sixlab.logistics.common.infrastructure.config.SwaggerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableFeignClients
@Import({
		JpaConfig.class,
		QueryDslConfig.class,
		SwaggerConfig.class
})
public class DeliveryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeliveryServiceApplication.class, args);
	}

}
