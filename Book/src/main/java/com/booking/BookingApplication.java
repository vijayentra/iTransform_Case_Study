package com.booking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
@EnableDiscoveryClient
//@EnableSwagger2
public class BookingApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookingApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
//	 @Bean
//	 public Docket productApi() {
//	      return new Docket(DocumentationType.SWAGGER_2).select()
//	         .apis(RequestHandlerSelectors.any())
//	         .paths(PathSelectors.any())
//	         .build();
//	   }
	
//	 @Bean
//	    public Docket api() {
//	        return new Docket(DocumentationType.SWAGGER_2)
//	            .select()
//	            .apis(RequestHandlerSelectors.basePackage("com.booking")) // Specify the package where your controllers are located
//	            .paths(PathSelectors.any())
//	            .build();
//	    }
}
