package com.erick.controller.valueObjects.v1;

import java.io.Serializable;
import java.util.List;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder({"result", "message", "_links"})
public class ResponseListVO<T> extends RepresentationModel<ResponseListVO<T>> implements Serializable {
		private static final long serialVersionUID = 1L;
		private String message;
		@JsonProperty("result")
		private List<T> valueObject;
		
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		public List<T> getValueObject() {
			return valueObject;
		}
		public void setValueObject(List<T> valueObject) {
			this.valueObject = valueObject;
		}
}
