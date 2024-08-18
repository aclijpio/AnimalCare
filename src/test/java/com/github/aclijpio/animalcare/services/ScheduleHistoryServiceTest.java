package com.github.aclijpio.animalcare.services;

import com.github.aclijpio.animalcare.dtos.ScheduleHistoryDto;
import com.github.aclijpio.animalcare.entities.ScheduleHistory;
import com.github.aclijpio.animalcare.mappers.ScheduleHistoryMapper;
import com.github.aclijpio.animalcare.repositories.ScheduleHistoryRepository;
import com.github.aclijpio.animalcare.services.impl.ScheduleHistoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class ScheduleHistoryServiceTest {

    @Mock
    private ScheduleHistoryRepository scheduleHistoryRepository;
    @Mock
    private ScheduleHistoryMapper mapper;

    @InjectMocks
    private ScheduleHistoryServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllScheduleHistories() {
        ScheduleHistory history1 = mock(ScheduleHistory.class);
        ScheduleHistory history2 = mock(ScheduleHistory.class);
        List<ScheduleHistory> historyList = List.of(history1, history2);

        when(scheduleHistoryRepository.findAll()).thenReturn(historyList);
        when(mapper.toDtoList(historyList)).thenReturn(List.of(mock(ScheduleHistoryDto.class), mock(ScheduleHistoryDto.class)));

        List<ScheduleHistoryDto> result = service.getAllScheduleHistory();

        assertNotNull(result);
        assertEquals(historyList.size(), result.size());

        verify(scheduleHistoryRepository, times(1)).findAll();
        verify(mapper, times(1)).toDtoList(historyList);
    }

    @Test
    void testGetAllScheduleHistoriesByDate() {
        ScheduleHistory history1 = mock(ScheduleHistory.class);
        ScheduleHistory history2 = mock(ScheduleHistory.class);
        List<ScheduleHistory> historyList = List.of(history1, history2);
        List<ScheduleHistoryDto> dtoList = List.of(mock(ScheduleHistoryDto.class), mock(ScheduleHistoryDto.class));

        when(scheduleHistoryRepository.findAllByDateBetween(LocalDate.now(), LocalDate.now().plusDays(1)))
                .thenReturn(historyList);
        when(mapper.toDtoList(historyList)).thenReturn(dtoList);

        List<ScheduleHistoryDto> result = service.getAllScheduleHistoryByDate(LocalDate.now());

        assertNotNull(result);
        assertEquals(dtoList.size(), result.size());
        assertIterableEquals(dtoList, result);
        verify(scheduleHistoryRepository, times(1)).findAllByDateBetween(any(LocalDate.class), any(LocalDate.class));
        verify(mapper, times(1)).toDtoList(historyList);
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3, 4, 5, 6})
    void testGetScheduleHistoryById(Long id) {
        ScheduleHistory history = mock(ScheduleHistory.class);
        when(scheduleHistoryRepository.findById(id)).thenReturn(Optional.of(history));
        when(mapper.toDto(history)).thenReturn(mock(ScheduleHistoryDto.class));

        ScheduleHistoryDto result = service.getScheduleHistoryById(id);

        assertNotNull(result);
        verify(scheduleHistoryRepository, times(1)).findById(id);
        verify(mapper, times(1)).toDto(history);
    }
}