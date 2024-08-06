package com.github.aclijpio.petcare;

import org.springframework.boot.SpringApplication;

public class TestPetCareApplication {

    public static void main(String[] args) {
        SpringApplication.from(PetCareApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
