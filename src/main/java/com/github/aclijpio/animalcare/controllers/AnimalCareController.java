package com.github.aclijpio.animalcare.controllers;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
public class AnimalCareController {

    @Scheduled(fixedRateString = "${schedule.rate}")
    void getAllAnimals(){

        new RestTemplate().getForObject("http://localhost:8080/animals", String.class);

    }






}
