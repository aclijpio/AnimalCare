package com.github.aclijpio.animalcare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AnimalCareApplication {
    public static void main(String[] args) {
        SpringApplication.run(AnimalCareApplication.class, args);
    }
}