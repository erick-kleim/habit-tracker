package com.erick.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
import com.erick.exceptions.ResourceNotFoundException;
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
		
		Long habitId = service.createHabit(habitVOSent);

		assertEquals(persisted.getId(), habitId);
	}

	@Test
	void testFindById() {
		when(repository.findById(1L)).thenReturn(Optional.of(persisted));

		ResponseVO habitFound = service.findById(1L);
		assertNotNull(habitFound);
	}

	@Test
	void errorWhenNotFoundById() {
		when(repository.findById(4L)).thenReturn(Optional.empty());

		Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
			service.findById(4L);
		});
		
		assertNotNull(exception);
		assertEquals("Habit not found with ID: 4.", exception.getMessage());
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

		service.updateById(habitVOSent);
	}

	@Test
	void testDeleteById() {
		when(repository.findById(1L)).thenReturn(Optional.of(persisted));
		service.deleteById(1L);
	}

	@Test
	void testDisableById() {	
		when(repository.findById(1L)).thenReturn(Optional.of(persisted));
		when(repository.save(persisted)).thenReturn(mockHabit.disabled());
		service.disableById(1L);
	}

	@Test
	void testFindAll() {
		when(repository.findAll()).thenReturn(mockHabit.list());
		
		ResponseListVO responseListVO = service.findAll();
		assertNotNull(responseListVO);
		assertNull(responseListVO.getMessage());
		assertNotNull(responseListVO.getValueObject());
		
		List<HabitVO> valueObjects = responseListVO.getValueObject();
		Assertions.assertEquals(2, valueObjects.size());
		valueObjects.stream().forEach(habitVO->{
			if(habitVO.getId()==1) {
				assertEquals(habitVO.getName(), persisted.getName());
				assertEquals(habitVO.isEnabled(), persisted.isEnabled());
				assertEquals(habitVO.getPeriodicity(), persisted.getPeriodicity());
			}
			else {
				assertEquals(habitVO.getName(), updated.getName());
				assertEquals(habitVO.isEnabled(), updated.isEnabled());
				assertEquals(habitVO.getPeriodicity(), updated.getPeriodicity());
			}
		});
	}

}
