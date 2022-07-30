package app.EasyFoodAPI;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class EasyFoodApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(EasyFoodApiApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

//	@Bean
//	public WebMvcConfigurer corsConfig() {
//		return new WebMvcConfigurerAdapter() {
//			@Override
//			public void addCorsMappings(CorsRegistry registry) {
//				registry.addMapping("/api/easyFood/**");
//			}
//		};
//	}
}
