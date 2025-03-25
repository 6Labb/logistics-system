package com.sixlab.logistics.product_service.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "상품 서비스 API 문서",
                description = "상품 관련 API 명세입니다.",
                version = "v1.0"
        ),
        servers = {
                @Server(url = "http://localhost:19091/api-docs-product-service", description = "게이트웨이 로컬")
        }
)
public class SwaggerConfig {


}
