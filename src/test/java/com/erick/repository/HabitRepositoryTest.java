package com.erick.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.erick.model.Habit;
import com.erick.model.enums.Periodicity;

@DataJpaTest
public class HabitRepositoryTest {
	
	@Autowired
	HabitRepository habitRepository;

	@Test
	void findHabitById() {
		Habit habit = new Habit();
		habit.setName("Pay the bills");
		habit.setPeriodicity(Periodicity.MONTHLY);
		habit.setEnabled(true);
		
		habitRepository.save(habit);
		
		Habit actual = habitRepository.findAll().get(0);
		Assertions.assertEquals("Pay the bills", actual.getName());
		Assertions.assertEquals(Periodicity.MONTHLY, actual.getPeriodicity());
		Assertions.assertTrue(actual.isEnabled());
		
	}
}
