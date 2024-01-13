package com.erick.model;

import java.io.Serializable;
import java.util.Objects;

import com.erick.model.enums.Periodicity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="HABITS")
public class Habit implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;
	
	@Column
	private boolean enabled;

	@Column
	private Periodicity periodicity;

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

	@Override
	public String toString() {
		return "Habit [id=" + id + ", name=" + name + ", enabled=" + enabled + ", periodicity=" + periodicity + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(enabled, id, name, periodicity);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Habit other = (Habit) obj;
		return enabled == other.enabled && Objects.equals(id, other.id) && Objects.equals(name, other.name)
				&& periodicity == other.periodicity;
	}	
}
