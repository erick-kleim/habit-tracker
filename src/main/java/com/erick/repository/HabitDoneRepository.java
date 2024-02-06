package com.erick.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.erick.model.Habit;
import com.erick.model.HabitDone;


@Repository
public interface HabitDoneRepository extends JpaRepository<HabitDone, Long>{
	
	Optional<List<HabitDone>> findByHabitAndDoneDate(Habit habit, LocalDate doneDate);

}
