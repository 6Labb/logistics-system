package com.sixlab.logistics.product_service;

import com.sixlab.logistics.common.infrastructure.config.JpaConfig;
import com.sixlab.logistics.common.infrastructure.config.QueryDslConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;


@SpringBootApplication
@EnableFeignClients
@Import({
		JpaConfig.class,
		QueryDslConfig.class,
})
public class ProductServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductServiceApplication.class, args);
	}

}
