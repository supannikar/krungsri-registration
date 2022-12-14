package com.krungsri.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.BasicAuth;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

	@Bean
	public Docket api() {
			return new Docket(DocumentationType.SWAGGER_2)
					.apiInfo(apiInfo())
					.select()
					.apis(RequestHandlerSelectors.basePackage("com.krungsri.controller"))
					.paths(PathSelectors.any())
					.build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Registration API")
				.description("The Registration API ")
				.version("1.0").build();
	}

	private SecurityScheme basicAuth() {
		return new BasicAuth("Basic Authentication");
	}
}
