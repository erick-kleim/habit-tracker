package com.erick.controller.valueObjects.v1;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import org.springframework.hateoas.RepresentationModel;

import com.erick.model.enums.Periodicity;
import com.fasterxml.jackson.annotation.JsonProperty;

public class HabitVO extends RepresentationModel<HabitVO> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long idHabit;
	private String name;
	@JsonProperty("isEnabled")
	private boolean enabled;
	private Periodicity periodicity;
	private List<LocalDate> doneDates;
	
	public Long getIdHabit() {
		return idHabit;
	}
	public void setIdHabit(Long idHabit) {
		this.idHabit = idHabit;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public Periodicity getPeriodicity() {
		return periodicity;
	}
	public void setPeriodicity(Periodicity periodicity) {
		this.periodicity = periodicity;
	}
	public List<LocalDate> getDoneDates() {
		return doneDates;
	}
	public void setDoneDates(List<LocalDate> doneDates) {
		this.doneDates = doneDates;
	}
}
