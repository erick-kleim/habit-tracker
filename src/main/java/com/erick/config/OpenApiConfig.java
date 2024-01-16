package com.erick.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {

	@Bean
	OpenAPI customOpenAPI() {
		return new OpenAPI()
				.info(new Info()
						.title("Habit Tracker - RESTful API with Java 21 and Spring Boot 3")
						.version("v1")
						.description("""
								This application provides control over habits by allowing the registration 
								and marking of habits as "done," along with progress tracking through reports.
								 Habits are categorized by recurrence, such as daily, weekly, monthly, or yearly.
								""")
						.termsOfService("https://www.linkedin.com/in/erick-kleim-dev-java/")
						.license(new License().name("Apache 2.0")
								.url("https://www.linkedin.com/in/erick-kleim-dev-java/")));
		
	
	}
}
