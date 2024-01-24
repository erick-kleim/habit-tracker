package com.erick.controller;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.erick.mock.MockHabit;
import com.erick.model.Habit;
import com.erick.service.HabitService;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class HabitControllerTest {

	MockHabit mockHabit;

	@InjectMocks
	HabitController controller;

	@Mock
	private HabitService service;

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
	void testNewHabit() {
		fail("Not yet implemented");
	}

	@Test
	void testGetById() {
		fail("Not yet implemented");
	}

	@Test
	void testUpdate() {
		fail("Not yet implemented");
	}

	@Test
	void testDisableById() {
		fail("Not yet implemented");
	}

	@Test
	void testDeleteById() {
		fail("Not yet implemented");
	}

	@Test
	void testGetAll() {
		fail("Not yet implemented");
	}

}
