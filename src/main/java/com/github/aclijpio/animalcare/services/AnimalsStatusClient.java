package com.github.aclijpio.animalcare.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("ANIMALS")
public interface AnimalsStatusClient {

    @PutMapping("/api/animals/status")
    void updateStatus(@RequestParam String status);
}