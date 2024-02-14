package com.erick.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.erick.controller.valueObjects.v1.ResponseVO;
import com.erick.exceptions.DateParserException;
import com.erick.exceptions.ResourceNotFoundException;
import com.erick.model.Habit;
import com.erick.model.HabitDone;
import com.erick.repository.HabitDoneRepository;
import com.erick.repository.HabitRepository;

@ExtendWith(MockitoExtension.class)
class HabitDoneServiceTest {

    @InjectMocks
    private HabitDoneService service;

    @Mock
    private HabitDoneRepository habitDoneRepository;

    @Mock
    private HabitRepository habitRepository;

    //private Logger logger;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
       // logger = Logger.getLogger(HabitDoneService.class.getName());
    }

    @Test
    void testCreateHabitDoneNow() {
        Habit habit = new Habit();
        habit.setId(1L);
        when(habitRepository.findById(1L)).thenReturn(Optional.of(habit));

        service.createHabitDoneNow(1L);

        verify(habitDoneRepository, times(1)).save(any(HabitDone.class));
    }

    @Test
    void testCreateHabitDoneNowWhenHabitNotFound() {
        when(habitRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            service.createHabitDoneNow(1L);
        });

        assertNotNull(exception);
        assertEquals("Habit not found with ID: 1.", exception.getMessage());
        verify(habitDoneRepository, never()).save(any(HabitDone.class));
    }

    @Test
    void testCreateHabitDoneOnDate() {
        LocalDate date = LocalDate.now();
        Habit habit = new Habit();
        habit.setId(1L);
        when(habitRepository.findById(1L)).thenReturn(Optional.of(habit));

        service.createHabitDoneOnDate(1L, date.toString());

        verify(habitDoneRepository, times(1)).save(any(HabitDone.class));
    }

    @Test
    void testCreateHabitDoneOnDateWhenHabitNotFound() {
        LocalDate date = LocalDate.now();
        when(habitRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            service.createHabitDoneOnDate(1L, date.toString());
        });

        assertNotNull(exception);
        assertEquals("Habit not found with ID: 1.", exception.getMessage());
        verify(habitDoneRepository, never()).save(any(HabitDone.class));
    }

    @Test
    void testCreateHabitDoneOnDateWhenInvalidDate() {
        Exception exception = assertThrows(DateParserException.class, () -> {
            service.createHabitDoneOnDate(1L, "InvalidDate");
        });

        assertNotNull(exception);
        verify(habitDoneRepository, never()).save(any(HabitDone.class));
    }

    @Test
    void testFindByHabitToday() {
        LocalDate date = LocalDate.now();
        Habit habit = new Habit();
        habit.setId(1L);
        when(habitRepository.findById(1L)).thenReturn(Optional.of(habit));
        when(habitDoneRepository.findByHabitAndDoneDate(habit, date)).thenReturn(Optional.of(Arrays.asList(new HabitDone(habit, date))));

        ResponseVO responseVO = service.findByHabitToday(1L);

        assertNotNull(responseVO);
        assertNotNull(responseVO.getValueObject());
        verify(habitDoneRepository, times(1)).findByHabitAndDoneDate(habit, date);
    }

    @Test
    void testFindByHabitOnDate() {
        LocalDate date = LocalDate.now();
        Habit habit = new Habit();
        habit.setId(1L);
        when(habitRepository.findById(1L)).thenReturn(Optional.of(habit));
        when(habitDoneRepository.findByHabitAndDoneDate(habit, date)).thenReturn(Optional.of(Arrays.asList(new HabitDone(habit, date))));

        ResponseVO responseVO = service.findByHabitOnDate(1L, date.toString());

        assertNotNull(responseVO);
        assertNotNull(responseVO.getValueObject());
        verify(habitDoneRepository, times(1)).findByHabitAndDoneDate(habit, date);
    }

    @Test
    void testFindByHabitOnDateWhenHabitNotFound() {
        LocalDate date = LocalDate.now();
        when(habitRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            service.findByHabitOnDate(1L, date.toString());
        });

        assertNotNull(exception);
       	assertEquals("Habit not found with ID: 1.", exception.getMessage());
       	verify(habitDoneRepository, never()).findByHabitAndDoneDate(any(Habit.class), any(LocalDate.class));
    }
}
/* 
package com.erick.service;

import static com.erick.service.exceptions.ServicesExceptions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.erick.controller.valueObjects.v1.HabitVO;
import com.erick.controller.valueObjects.v1.ResponseVO;
import com.erick.model.Habit;
import com.erick.model.HabitDone;
import com.erick.repository.HabitDoneRepository;
import com.erick.repository.HabitRepository;

@ExtendWith(MockitoExtension.class)
class HabitDoneServiceTest {

    @InjectMocks
    private HabitDoneService service;

    @Mock
    private HabitDoneRepository habitDoneRepository;

    @Mock
    private HabitRepository habitRepository;

    private Logger logger;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        logger = Logger.getLogger(HabitDoneService.class.getName());
    }

    @Test
    void testCreateHabitDoneNow() {
        Habit habit = new Habit();
        habit.setId(1L);
        when(habitRepository.findById(1L)).thenReturn(Optional.of(habit));

        service.createHabitDoneNow(1L);

        verify(habitDoneRepository, times(1)).save(any(HabitDone.class));
    }

    @Test
    void testCreateHabitDoneNowWhenHabitNotFound() {
        when(habitRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            service.createHabitDoneNow(1L);
        });

        assertNotNull(exception);
        assertEquals("Habit not found with ID: 1.", exception.getMessage());
        verify(habitDoneRepository, never()).save(any(HabitDone.class));
    }

    @Test
    void testCreateHabitDoneOnDate() {
        LocalDate date = LocalDate.now();
        Habit habit = new Habit();
        habit.setId(1L);
        when(habitRepository.findById(1L)).thenReturn(Optional.of(habit));

        service.createHabitDoneOnDate(1L, date.toString());

        verify(habitDoneRepository, times(1)).save(any(HabitDone.class));
    }

    @Test
    void testCreateHabitDoneOnDateWhenHabitNotFound() {
        LocalDate date = LocalDate.now();
        when(habitRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            service.createHabitDoneOnDate(1L, date.toString());
        });

        assertNotNull(exception);
        assertEquals("Habit not found with ID: 1.", exception.getMessage());
        verify(habitDoneRepository, never()).save(any(HabitDone.class));
    }

    @Test
    void testCreateHabitDoneOnDateWhenInvalidDate() {
        Exception exception = assertThrows(DateTimeParseException.class, () -> {
            service.createHabitDoneOnDate(1L, "InvalidDate");
        });

        assertNotNull(exception);
        verify(habitDoneRepository, never()).save(any(HabitDone.class));
    }

    @Test
    void testFindByHabitToday() {
        LocalDate date = LocalDate.now();
        Habit habit = new Habit();
        habit.setId(1L);
        when(habitRepository.findById(1L)).thenReturn(Optional.of(habit));
        when(habitDoneRepository.findByHabitAndDoneDate(habit, date)).thenReturn(Optional.of(Arrays.asList(new HabitDone(habit, date))));

        ResponseVO responseVO = service.findByHabitToday(1L);

        assertNotNull(responseVO);
        assertNotNull(responseVO.getValueObject());
        verify(habitDoneRepository, times(1)).findByHabitAndDoneDate(habit, date);
    }

    @Test
    void testFindByHabitOnDate() {
        LocalDate date = LocalDate.now();
        Habit habit = new Habit();
        habit.setId(1L);
        when(habitRepository.findById(1L)).thenReturn(Optional.of(habit));
        when(habitDoneRepository.findByHabitAndDoneDate(habit, date)).thenReturn(Optional.of(Arrays.asList(new HabitDone(habit, date))));

        ResponseVO responseVO = service.findByHabitOnDate(1L, date.toString());

        assertNotNull(responseVO);
        assertNotNull(responseVO.getValueObject());
        verify(habitDoneRepository, times(1)).findByHabitAndDoneDate(habit, date);
    }

    @Test
    void testFindByHabitOnDateWhenHabitNotFound() {
        LocalDate date = LocalDate.now();
        when(habitRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            service.findByHabitOnDate(1L, date.toString());
        });
*/
       
