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
		Habit habitr = new Habit();
		habitr.setName("Pay the bills");
		habitr.setPeriodicity(Periodicity.MONTHLY);
		
		habitRepository.save(habitr);
		Habit actual = habitRepository.findAll().get(0);
		Assertions.assertEquals("Pay the bills", actual.getName());
		Assertions.assertEquals(Periodicity.MONTHLY, actual.getPeriodicity());
		
	}
}
