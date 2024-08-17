package com.github.aclijpio.animalcare.repositories;

import com.github.aclijpio.animalcare.entities.Schedule;
import com.github.aclijpio.animalcare.entities.ScheduleHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ScheduleHistoryRepository extends JpaRepository<ScheduleHistory, Long> {

    List<ScheduleHistory> findAllByDateBetween(LocalDate first, LocalDate second);
    Optional<ScheduleHistory> findFirstBySchedule(Schedule schedule);
    boolean existsByScheduleId(Long schedule_id);
    @Query("SELECT CASE WHEN COUNT(sh) > 0 THEN true ELSE false END " +
            "FROM ScheduleHistory sh " +
            "JOIN sh.schedule s " +
            "WHERE s.id = :scheduleId AND :triggerName MEMBER OF s.triggerName")
    boolean existsByScheduleTriggerName(Long scheduleId, String triggerName);
}
