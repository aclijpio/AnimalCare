package com.github.aclijpio.animalcare.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
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
    @Column(name = "trigger_name")
    private List<String> triggerName;

    public void addTriggerName(String triggerName) {
        this.triggerName.add(triggerName);
    }
    public Schedule removeTriggerName(String triggerName) {
        this.triggerName.remove(triggerName);
        return this;
    }
    public void setCreatedDateNow(){
        this.createdDate = LocalDateTime.now();
    }

    public Schedule() {
        this.triggerName = new ArrayList<>();
    }
    public static Schedule copyOf(Schedule original){
        Schedule copy = new Schedule();
        copy.setId(original.getId());
        copy.setCreatedDate(original.getCreatedDate());

        List<String> triggerNames =  new ArrayList<>(original.getTriggerName());
        copy.setTriggerName(triggerNames);
        return copy;
    }
}


