package com.github.aclijpio.petcare.controllers;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AnimalCareController {

    @Scheduled(fixedRateString = "${schedule.rate}")
    void getAllAnimals(){

    }






}
