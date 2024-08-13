package com.github.aclijpio.animalcare.repositories;

import com.github.aclijpio.animalcare.entities.ScheduleHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleHistoryRepository extends JpaRepository<ScheduleHistory, Long> {

    List<ScheduleHistory> findAllByDateBetween(LocalDate start, LocalDate end);

}
