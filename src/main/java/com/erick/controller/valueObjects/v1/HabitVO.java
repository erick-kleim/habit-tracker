package com.erick.controller.valueObjects.v1;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import org.springframework.hateoas.RepresentationModel;

import com.erick.model.enums.Periodicity;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class HabitVO extends RepresentationModel<HabitVO> implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	private boolean enabled;
	private Periodicity periodicity;
	private List<LocalDate> doneDates;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
