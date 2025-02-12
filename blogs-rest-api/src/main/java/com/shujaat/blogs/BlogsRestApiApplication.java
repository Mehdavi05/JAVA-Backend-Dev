package com.shujaat.blogs;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@OpenAPIDefinition(
	info = @Info(
			title = "Spring Boot Blog REST APIs",
			description = "Spring Boot Blog REST APIs documentation",
			version = "v1.0",
			contact = @Contact(
					name = "Shujaat Hussain",
					email = "shujaat.apple05@gmail.com",
					url = "https://www.linkedin.com/in/shujaat-hussain-28557419/"
			),
			license = @License(
					name = "Apache 2.0",
					url = "https://www.linkedin.com/in/shujaat-hussain-28557419/"
			)
	),
		externalDocs = @ExternalDocumentation(
				description = "Spring Boot Blog REST APIs documentation",
				url = "https://github.com/Mehdavi05/JAVA-Backend-Dev"
		)
)
public class BlogsRestApiApplication {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(BlogsRestApiApplication.class, args);
	}

}
