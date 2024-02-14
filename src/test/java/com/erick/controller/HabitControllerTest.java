package com.erick.controller;

import com.erick.controller.valueObjects.v1.HabitVO;
import com.erick.controller.valueObjects.v1.ResponseListVO;
import com.erick.controller.valueObjects.v1.ResponseVO;
import com.erick.service.HabitService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HabitController.class)
public class HabitControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HabitService habitService;

    @Test
    void newHabit() throws Exception {
        HabitVO habitVO = new HabitVO();
        habitVO.setName("Exercise");
        habitVO.setEnabled(true);

        when(habitService.createHabit(habitVO)).thenReturn(1L);

        mockMvc.perform(post("/habit/v1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Exercise\",\"enabled\":true}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/habit/v1/1"));

        verify(habitService, times(1)).createHabit(habitVO);
    }

    @Test
    void getById() throws Exception {
        long habitId = 1L;
        ResponseVO responseVO = new ResponseVO();
        when(habitService.findById(habitId)).thenReturn(responseVO);

        mockMvc.perform(get("/habit/v1/{id}", habitId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(habitService, times(1)).findById(habitId);
    }

    @Test
    void update() throws Exception {
        HabitVO habitVO = new HabitVO();
        habitVO.setId(1L);
        habitVO.setName("Running");
        habitVO.setEnabled(true);

        mockMvc.perform(put("/habit/v1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":1,\"name\":\"Running\",\"enabled\":true}"))
                .andExpect(status().isNoContent());

        verify(habitService, times(1)).updateById(habitVO);
    }

    @Test
    void disableById() throws Exception {
        long habitId = 1L;
        mockMvc.perform(patch("/habit/v1/{id}/disable", habitId))
                .andExpect(status().isNoContent());

        verify(habitService, times(1)).disableById(habitId);
    }

    @Test
    void deleteById() throws Exception {
        long habitId = 1L;
        mockMvc.perform(delete("/habit/v1/{id}", habitId))
                .andExpect(status().isNoContent());

        verify(habitService, times(1)).deleteById(habitId);
    }

    @Test
    void getAll() throws Exception {
        ResponseListVO responseListVO = new ResponseListVO();
        HabitVO habitVO = new HabitVO();
        habitVO.setId(1L);
        habitVO.setName("Running");
        habitVO.setEnabled(true);
        responseListVO.setValueObject(Arrays.asList(habitVO));

        when(habitService.findAll()).thenReturn(responseListVO);

        mockMvc.perform(get("/habit/v1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(habitService, times(1)).findAll();
    }
}
