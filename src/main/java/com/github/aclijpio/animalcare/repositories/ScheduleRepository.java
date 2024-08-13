package com.github.aclijpio.animalcare.repositories;

import com.github.aclijpio.animalcare.entities.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    Optional<Schedule> findTopByOrderByCreatedDateDesc();
}
