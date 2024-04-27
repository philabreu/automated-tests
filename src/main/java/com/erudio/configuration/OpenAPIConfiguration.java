package com.erudio.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfiguration {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("hello swagger openapi")
                        .version("v1")
                        .description("some description")
                        .termsOfService("some terms")
                        .license(new License()
                                .name("apache 2.0")
                                .url("some url")));
    }
}
