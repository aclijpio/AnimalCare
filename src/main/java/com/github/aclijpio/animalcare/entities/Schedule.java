package com.github.aclijpio.animalcare.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "schedules")
@Data
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime createdDate;

    @ElementCollection
    @CollectionTable(name = "schedule_triggers",
            joinColumns = @JoinColumn(name = "schedule_id"))
    @Column(name = "trigger_name", unique = true)
    private List<String> triggerName;
}
