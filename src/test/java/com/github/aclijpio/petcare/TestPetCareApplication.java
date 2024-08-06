package com.github.aclijpio.petcare;

import org.springframework.boot.SpringApplication;

public class TestPetCareApplication {

    public static void main(String[] args) {
        SpringApplication.from(AnimalCareApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
