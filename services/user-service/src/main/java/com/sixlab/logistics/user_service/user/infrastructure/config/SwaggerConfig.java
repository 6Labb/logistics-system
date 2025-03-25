package com.sixlab.logistics.user_service.user.infrastructure.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "유저 서비스 API 문서",
                description = "유저 관련 API 명세입니다.",
                version = "v1.0"
        ),
        servers = {
                @Server(url = "http://localhost:19091/api-docs-user-service", description = "로컬 서버")
        }
)
public class SwaggerConfig {

//    @Bean
//    public Docket api() {
//        return new Docket(DocumentationType.OAS_30)
//                .useDefaultResponseMessages(false)
//                .select()
//                .apis(RequestHandlerSelectors.any())
//                .paths(PathSelectors.any())
//                .build()
//                .apiInfo(apiInfo());
//    }
//
//    private ApiInfo apiInfo() {
//        return new ApiInfoBuilder()
//                .title("스웨거 제목")
//                .description("스웨거 설명")
//                .version("1.0")
//                .build();
//    }
}
