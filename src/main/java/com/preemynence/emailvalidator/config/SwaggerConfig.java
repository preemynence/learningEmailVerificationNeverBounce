package com.preemynence.emailvalidator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDate;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket customImplementation() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.select().paths(PathSelectors.any())
				.apis(RequestHandlerSelectors.basePackage("com.preemynence"))
				.build()
				.pathMapping("/")
				.useDefaultResponseMessages(false)
				.directModelSubstitute(LocalDate.class, String.class)
				.genericModelSubstitutes(ResponseEntity.class)
				;
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("Learning Email for Bounce protection application.")
				.version("1.0.0")
				.contact(new Contact("Vishal Prajapati", "https://www.linkedin.com/in/vishalprajapati", "vishalprajapati@gmail.com"))
				.description("This is reference document for the learning purpose.")
				.license("This is copyrighted material, please contact person before using the code.")
				.build();
	}

}