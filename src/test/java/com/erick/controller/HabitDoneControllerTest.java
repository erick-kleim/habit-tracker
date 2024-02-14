package com.erick.controller;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.erick.controller.valueObjects.v1.ResponseVO;
import com.erick.service.HabitDoneService;

@WebMvcTest(HabitDoneController.class)
public class HabitDoneControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HabitDoneService habitDoneService;

    @Test
    void markAsDoneNow() throws Exception {
        long habitId = 1L;
        mockMvc.perform(put("/habit-done/v1/habit/{id}", habitId))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));

        verify(habitDoneService, times(1)).createHabitDoneNow(habitId);
    }

    @Test
    void markAsDoneOnDate() throws Exception {
        long habitId = 1L;
        String date = "2022-01-26";
        mockMvc.perform(put("/habit-done/v1/habit/{id}/date/{date}", habitId, date))
        .andExpect(status().isCreated())
        .andExpect(header().exists("Location"));

        verify(habitDoneService, times(1)).createHabitDoneOnDate(habitId, date);
    }

    @Test
    void doneHabisOnDate() throws Exception {
        long habitId = 1L;
        String date = "2022-01-26";
        ResponseVO responseVO = new ResponseVO();
        when(habitDoneService.findByHabitOnDate(habitId, date)).thenReturn(responseVO);

        mockMvc.perform(get("/habit-done/v1/habit/{id}/date/{date}", habitId, date))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(habitDoneService, times(1)).findByHabitOnDate(habitId, date);
    }

    @Test
    void getById() throws Exception {
        long habitId = 1L;
        ResponseVO responseVO = new ResponseVO();
        when(habitDoneService.findByHabitToday(habitId)).thenReturn(responseVO);

        mockMvc.perform(get("/habit-done/v1/habit/{id}/today", habitId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(habitDoneService, times(1)).findByHabitToday(habitId);
    }
}