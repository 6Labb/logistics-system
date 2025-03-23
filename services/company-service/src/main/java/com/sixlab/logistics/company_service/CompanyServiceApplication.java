package com.sixlab.logistics.company_service;

import com.sixlab.logistics.common.infrastructure.config.JpaConfig;
import com.sixlab.logistics.common.infrastructure.config.QueryDslConfig;
import com.sixlab.logistics.common.infrastructure.config.SwaggerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableFeignClients(basePackages = "com.sixlab.logistics")
@SpringBootApplication
@ComponentScan(basePackages = {"com.sixlab.logistics.common", "com.sixlab.logistics.company_service"})
@Import({
		JpaConfig.class,
		QueryDslConfig.class,
		SwaggerConfig.class
})
public class CompanyServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CompanyServiceApplication.class, args);
	}

}
