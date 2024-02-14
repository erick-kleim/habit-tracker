package com.erick.controller.valueObjects.v1;

import java.io.Serializable;
import java.util.List;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder({"valueObject", "message", "_links"})
public class ResponseListVO extends RepresentationModel<ResponseListVO> implements Serializable {
		private static final long serialVersionUID = 1L;
		private String message;
		private List<HabitVO> valueObject;
		
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		public List<HabitVO> getValueObject() {
			return valueObject;
		}
		public void setValueObject(List<HabitVO> valueObject) {
			this.valueObject = valueObject;
		}
}
