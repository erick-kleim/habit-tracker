package com.erick.integrationtests.swagger;

import static io.restassured.RestAssured.given;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.erick.configs.TestConfigs;
import com.erick.integrationtests.testcontainers.AbstractIntegrationTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SwaggerIntegrationTest extends AbstractIntegrationTest{
	
	@Test
	void shouldDisplaySwaggerUiPage() {
		String responseBody = given()
			.basePath("/swagger-ui/index.html")
			.port(TestConfigs.SERVER_PORT)
			.when()
				.get()
			.then()
				.statusCode(200)
			.extract()
				.body()
					.asString();
		Assertions.assertTrue(responseBody.contains("Swagger UI"));
	}
	
	@Test
	void shouldReturnApiDocs() {
		String responseBody = given()
			.basePath("/v3/api-docs")
			.port(TestConfigs.SERVER_PORT)
			.when()
				.get()
			.then()
				.statusCode(200)
			.extract()
				.body()
					.asString();
		System.out.println(responseBody);
		Assertions.assertTrue(responseBody.contains("openapi"));
		
		
	}
}
