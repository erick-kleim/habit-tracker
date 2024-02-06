package com.erick.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name="HABITS_DONE")
public class HabitDone implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@JoinColumn(name="HABITS_ID")
	private Habit habit;
	@Temporal(TemporalType.DATE)
	private LocalDate doneDate;
	
	public HabitDone() {
		super();
	}

	public HabitDone(Habit habit) {
		super();
		this.habit = habit;
		this.doneDate = LocalDate.now();
	}
	
	public HabitDone(Habit habit, LocalDate doneDate) {
		super();
		this.habit = habit;
		this.doneDate = doneDate;
	}
	
	public HabitDone(Long id, Habit habit, LocalDate doneDate) {
		super();
		this.id = id;
		this.habit = habit;
		this.doneDate = doneDate;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	
	@Override
	public int hashCode() {
		return Objects.hash(doneDate, habit, id);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HabitDone other = (HabitDone) obj;
		return Objects.equals(doneDate, other.doneDate) && Objects.equals(habit, other.habit)
				&& Objects.equals(id, other.id);
	}

}
