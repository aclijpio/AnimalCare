package com.github.aclijpio.animalcare.services;

import com.github.aclijpio.animalcare.dtos.request.AdoptionRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient("ADOPTION")
public interface TestClient {

    @GetMapping("/adoption")
    List<AdoptionRequest> getAdoptions();
}
