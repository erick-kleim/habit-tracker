package com.erick.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import com.erick.model.enums.Periodicity;

public class Habit implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String name;
	private List<LocalDate> done;
	private Periodicity periodicity;

}
