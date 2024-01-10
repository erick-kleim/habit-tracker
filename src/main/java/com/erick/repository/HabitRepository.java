package com.erick.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.erick.model.Habit;

@Repository
public interface HabitRepository extends JpaRepository<Habit, Long>{

}
