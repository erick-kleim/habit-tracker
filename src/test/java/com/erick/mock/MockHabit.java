package com.erick.mock;

import java.util.List;

import com.erick.model.Habit;
import com.erick.model.enums.Periodicity;

public class MockHabit {

	public Habit persisted() {
		Habit habit = new Habit();
		habit.setId(1L);
		habit.setName("wash the dishes");
		habit.setEnabled(true);
		habit.setPeriodicity(Periodicity.DAILY);
		return habit;
	}
	
	public Habit notPersisted() {
		Habit habit = new Habit();
		habit.setName("wash the dishes");
		habit.setEnabled(true);
		habit.setPeriodicity(Periodicity.DAILY);
		return habit;
	}
	
	public Habit updated() {
		Habit habit = new Habit();
		habit.setId(1L);
		habit.setName("wash the car");
		habit.setEnabled(true);
		habit.setPeriodicity(Periodicity.WEEKLY);
		return habit;
	}
	
	public Habit disabled() {
		Habit habit = new Habit();
		habit.setId(1L);
		habit.setName("wash");
		habit.setEnabled(false);
		habit.setPeriodicity(Periodicity.DAILY);
		return habit;
	}
	
	public List<Habit> list() {
		Habit updated = updated();
		updated.setId(2L);
		return List.of(persisted(), updated);
	}
}
