package com.erick.repository;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.erick.model.DateHabitDone;
import com.erick.model.Habit;
import com.erick.model.enums.Periodicity;

@DataJpaTest
public class DateHabitDoneRepositoryTest {

	@Autowired
	TestEntityManager entityManager;
	
	@Autowired
	DateHabitDoneRepository dateHabitDoneRepository;
	
	@Test
	public void tsete() {
		Habit habit = new Habit();
		habit.setName("Pay the bills");
		habit.setPeriodicity(Periodicity.MONTHLY);
		habit.setEnabled(true);
		entityManager.persist(habit);
		
		LocalDate dec23 = LocalDate.of(2023, Month.DECEMBER, 15);
		dateHabitDoneRepository.save(new DateHabitDone(habit, dec23));
		LocalDate jan24 = LocalDate.of(2024, Month.JANUARY, 15);
		dateHabitDoneRepository.save(new DateHabitDone(habit, jan24));
		
		List<DateHabitDone> actual = dateHabitDoneRepository.findAll();
		Assertions.assertEquals(2, actual.size());
		
		actual.stream().forEach(h->{
			Assertions.assertEquals(h.getHabit(), habit);
			if(h.getId()==1) 
				Assertions.assertEquals(h.getDate(), dec23);
			else 
				Assertions.assertEquals(h.getDate(), jan24);
		});
	}
}
