package com.github.aclijpio.animalcare.services;

import com.github.aclijpio.animalcare.dtos.ScheduleDto;
import com.github.aclijpio.animalcare.entities.Schedule;
import com.github.aclijpio.animalcare.exceptions.ScheduleNotFoundException;
import com.github.aclijpio.animalcare.mappers.ScheduleMapper;
import com.github.aclijpio.animalcare.repositories.ScheduleRepository;
import com.github.aclijpio.animalcare.services.impl.ScheduleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class ScheduleServiceTest {

    @Autowired
    private ScheduleRepository repository;
    @Autowired
    private ScheduleMapper mapper;

    private ScheduleService service;


    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:latest"
    );

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.jpa.generate-ddl", () -> true);
        registry.add("spring.jpa.properties.hibernate.dialect", () -> "org.hibernate.dialect.PostgreSQLDialect");
    }

    @BeforeEach
    void setUp() {
        service = new ScheduleServiceImpl(repository, mapper);
    }

    @Test
    void testGetAllSchedules() {
        Schedule schedule1 = new Schedule();
        Schedule schedule2 = new Schedule();
        repository.save(schedule1);
        repository.save(schedule2);

        List<ScheduleDto> schedules = service.getAllSchedules();
        assertEquals(2, schedules.size());
    }

    @Test
    void testGetCurrentSchedule() {
        Schedule schedule = new Schedule();
        schedule.setCreatedDate(LocalDateTime.now());
        repository.save(schedule);

        ScheduleDto currentSchedule = service.getCurrentSchedule();

        assertNotNull(currentSchedule);
        assertEquals(schedule.getId(), currentSchedule.id());
    }

    @Test
    void testGetScheduleById() {
        Schedule schedule = new Schedule();
        repository.save(schedule);


        ScheduleDto foundSchedule = service.getScheduleById(schedule.getId());

        assertNotNull(foundSchedule);
        assertEquals(schedule.getId(), foundSchedule.id());
    }

    @Test
    void testGetScheduleById_NotFound() {
        assertThrows(ScheduleNotFoundException.class, () -> service.getScheduleById(999L));
    }
}