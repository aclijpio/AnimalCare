package com.github.aclijpio.animalcare.entities;


import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "schedules_history")
public class ScheduleHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    @ManyToOne
    private Schedule schedule;
}
