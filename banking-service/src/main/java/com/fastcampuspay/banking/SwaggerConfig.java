package com.fastcampuspay.banking;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {
	@Bean
	public OpenAPI OpenAPI() {
		return new OpenAPI()
			.info(new Info()
				.title("Banking Service API")
				.version("1.0")
				.description("Banking Service API v1.0")
			);
	}
}
