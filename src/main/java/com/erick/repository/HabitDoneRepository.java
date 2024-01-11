package com.erick.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.erick.model.HabitDone;

@Repository
public interface HabitDoneRepository extends JpaRepository<HabitDone, Long>{

}
