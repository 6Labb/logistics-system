package com.sixlab.logistics.delivery_service.delivery.infrastructure.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "배송 서비스 API 문서",
                description = "배송 관련 API 명세입니다.",
                version = "v1.0"
        ),
        servers = {
                @Server(url = "http://localhost:19091/api-docs-delivery-service", description = "게이트웨이 로컬")
        }
)
public class SwaggerConfig {


}
