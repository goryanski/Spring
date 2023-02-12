package com.micro.eurekaclienttodos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class EurekaClientTodosApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaClientTodosApplication.class, args);
    }

}
