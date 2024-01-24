package com.erick.controller.valueObjects.v1;

import java.io.Serializable;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder({"valueObject", "message", "_links"})
public class ResponseVO extends RepresentationModel<ResponseVO> implements Serializable {

	private static final long serialVersionUID = 1L;
	private String message;
	private HabitVO valueObject;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public HabitVO getValueObject() {
		return valueObject;
	}
	public void setValueObject(HabitVO valueObject) {
		this.valueObject = valueObject;
	}
}
