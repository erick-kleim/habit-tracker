package com.erick.repository;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.erick.model.HabitDone;
import com.erick.model.Habit;
import com.erick.model.enums.Periodicity;

@DataJpaTest
public class HabitDoneRepositoryTest {

	@Autowired
	TestEntityManager entityManager;
	
	@Autowired
	HabitDoneRepository habitDoneRepository;
	
	@Test
	public void tsete() {
		Habit habit = new Habit();
		habit.setName("Pay the bills");
		habit.setPeriodicity(Periodicity.MONTHLY);
		habit.setEnabled(true);
		entityManager.persist(habit);
		
		LocalDate dec23 = LocalDate.of(2023, Month.DECEMBER, 15);
		habitDoneRepository.save(new HabitDone(habit, dec23));
		LocalDate jan24 = LocalDate.of(2024, Month.JANUARY, 15);
		habitDoneRepository.save(new HabitDone(habit, jan24));
		
		List<HabitDone> actual = habitDoneRepository.findAll();
		Assertions.assertEquals(2, actual.size());
		
		actual.stream().forEach(h->{
			Assertions.assertEquals(h.getHabit(), habit);
			if(h.getId()==1) 
				Assertions.assertEquals(h.getDoneDate(), dec23);
			else 
				Assertions.assertEquals(h.getDoneDate(), jan24);
		});
	}
}
