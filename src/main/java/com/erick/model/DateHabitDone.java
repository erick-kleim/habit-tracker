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
@Table(name="DATES_HABITS")
public class DateHabitDone implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "ID_DATE")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="HABITS_ID")
	private Habit habit;
	private LocalDate data;
	public Habit getHabit() {
		return habit;
	}
	public void setHabit(Habit habit) {
		this.habit = habit;
	}
	public LocalDate getData() {
		return data;
	}
	public void setData(LocalDate data) {
		this.data = data;
	}

}
