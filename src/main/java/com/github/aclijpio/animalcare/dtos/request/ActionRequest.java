package com.github.aclijpio.animalcare.dtos.request;

import java.time.LocalTime;

public record ActionRequest (LocalTime time, String name, String description) {
}
