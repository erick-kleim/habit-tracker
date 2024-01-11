package com.erick.model;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="HABIT_DONE")
public class HabitDone implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "ID_DATE")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@JoinColumn(name="HABITS_ID")
	private Habit habit;
	private LocalDate doneDate;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public HabitDone(Habit habit, LocalDate doneDate) {
		super();
		this.habit = habit;
		this.doneDate = doneDate;
	}
	
	public Habit getHabit() {
		return habit;
	}
	public void setHabit(Habit habit) {
		this.habit = habit;
	}
	public LocalDate getDoneDate() {
		return doneDate;
	}
	public void setDoneDate(LocalDate doneDate) {
		this.doneDate = doneDate;
	}

}
