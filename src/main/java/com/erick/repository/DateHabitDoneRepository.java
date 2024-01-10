package com.erick.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.erick.model.DateHabitDone;

@Repository
public interface DateHabitDoneRepository extends JpaRepository<DateHabitDone, Long>{

}
