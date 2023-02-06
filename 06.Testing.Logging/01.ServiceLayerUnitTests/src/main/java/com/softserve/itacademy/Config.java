package com.softserve.itacademy;

import com.softserve.itacademy.utils.AppValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.softserve.itacademy.service")
public class Config {
    @Bean
    public AppValidator createValidator() {
        return AppValidator.getValidator();
    }
}
