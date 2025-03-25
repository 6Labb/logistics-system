package com.sixlab.logistics.company_service;

import com.sixlab.logistics.common.infrastructure.config.JpaConfig;
import com.sixlab.logistics.common.infrastructure.config.QueryDslConfig;
import com.sixlab.logistics.company_service.config.FeignClientInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableFeignClients(defaultConfiguration = FeignClientInterceptor.class)
@SpringBootApplication
@Import({
		JpaConfig.class,
		QueryDslConfig.class
})
@ComponentScan(basePackages = {"com.sixlab.logistics.common", "com.sixlab.logistics.company_service"})
public class CompanyServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CompanyServiceApplication.class, args);
	}

}
