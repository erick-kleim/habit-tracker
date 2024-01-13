package com.erick.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.erick.controller.valueObjects.v1.HabitVO;
import com.erick.controller.valueObjects.v1.ResponseListVO;
import com.erick.controller.valueObjects.v1.ResponseVO;
import com.erick.mock.MockHabit;
import com.erick.model.Habit;
import com.erick.repository.HabitRepository;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class HabitServiceTest {

	MockHabit mockHabit;

	@InjectMocks
	private HabitService service;

	@Mock
	HabitRepository repository;

	Habit habit;
	Habit persisted;
	Habit updated;

	@BeforeEach
	void setUp() throws Exception {
		mockHabit = new MockHabit();
		habit = mockHabit.notPersisted();
		persisted = mockHabit.persisted();
		updated = mockHabit.updated();
		MockitoAnnotations.openMocks(this);

	}

	@Test
	void testCreateHabit() {
		when(repository.save(habit)).thenReturn(persisted);
		
		HabitVO habitVOSent = new HabitVO();
		habitVOSent.setName(habit.getName());
		habitVOSent.setEnabled(habit.isEnabled());
		habitVOSent.setPeriodicity(habit.getPeriodicity());		
		ResponseVO response = service.createHabit(habitVOSent);
		
		assertNotNull(response);
		HabitVO habitVOReceived = (HabitVO)response.getValueObject();
		assertEquals(persisted.getId(), habitVOReceived.getId());
		assertEquals(persisted.getName(), habitVOReceived.getName());
		assertEquals(persisted.isEnabled(), habitVOReceived.isEnabled());
		assertEquals(persisted.getPeriodicity(), habitVOReceived.getPeriodicity());
	}

	@Test
	void testFindById() {
		when(repository.findById(1L)).thenReturn(Optional.of(persisted));

		ResponseVO habitFound = service.findById(1L);
		Assertions.assertNotNull(habitFound);
	}

	@Test
	void testUpdateById() {
		when(repository.findById(1L)).thenReturn(Optional.of(persisted));
		when(repository.save(updated)).thenReturn(updated);

		HabitVO habitVOSent = new HabitVO();
		habitVOSent.setId(updated.getId());
		habitVOSent.setName(updated.getName());
		habitVOSent.setEnabled(updated.isEnabled());
		habitVOSent.setPeriodicity(updated.getPeriodicity());

		ResponseVO response = service.updateById(habitVOSent);

		assertNotNull(response);
		HabitVO habitVOReceived = (HabitVO) response.getValueObject();
		assertNotNull(habitVOReceived);
		assertEquals(updated.getId(), habitVOReceived.getId());
		assertEquals(updated.getName(), habitVOReceived.getName());
		assertEquals(updated.isEnabled(), habitVOReceived.isEnabled());
		assertEquals(updated.getPeriodicity(), habitVOReceived.getPeriodicity());
	}

	@Test
	void testDeleteById() {
		when(repository.findById(1L)).thenReturn(Optional.of(persisted));
		
		ResponseVO response = service.deleteById(1L);
		assertNotNull(response);
		assertNotNull(response.getMessage());
		assertEquals("Habit: wash the dishes(id 1) was successfully deleted.",response.getMessage());
		assertNull(response.getValueObject());
	}

	@Test
	void testDisableById() {		
		when(repository.findById(1L)).thenReturn(Optional.of(persisted));
		when(repository.save(persisted)).thenReturn(mockHabit.disabled());
		
		ResponseVO response = service.disableById(1L);
		assertNotNull(response);
		assertNotNull(response.getMessage());
		assertEquals("Habit: wash the dishes(id 1) was successfully disabled.",response.getMessage());
		assertNull(response.getValueObject());
	}

	@Test
	void testFindAll() {
		when(repository.findAll()).thenReturn(mockHabit.list());
		
		ResponseListVO<HabitVO> responseListVO = service.findAll();
		assertNotNull(responseListVO);
		assertNotNull(responseListVO.getMessage());
		assertEquals("Success.",responseListVO.getMessage());
		assertNotNull(responseListVO.getValueObject());
		
		List<HabitVO> valueObjects = responseListVO.getValueObject();
		Assertions.assertEquals(2, valueObjects.size());
		valueObjects.stream().forEach(habitVO->{
			if(habitVO.getId()==1) {
				Assertions.assertEquals(habitVO.getName(), persisted.getName());
				Assertions.assertEquals(habitVO.isEnabled(), persisted.isEnabled());
				Assertions.assertEquals(habitVO.getPeriodicity(), persisted.getPeriodicity());
			}
			else {
				Assertions.assertEquals(habitVO.getName(), updated.getName());
				Assertions.assertEquals(habitVO.isEnabled(), updated.isEnabled());
				Assertions.assertEquals(habitVO.getPeriodicity(), updated.getPeriodicity());
			}
		});
	}

}
