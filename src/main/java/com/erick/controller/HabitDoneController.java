package com.erick.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.erick.service.HabitDoneService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/habit-done/v1/")
@Tag(name = "Done habits", description = "Endpoints to manage done habtis")
public class HabitDoneController {
	
	@Autowired
	HabitDoneService service;
	
	@PostMapping(value = "{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Operation(summary = "Sets a habit as done",
		description = "This EP finds a habit by ID and updates the object with the body information.",
		tags = {"Done habits"},
		responses = {
			@ApiResponse(description="Sucess", responseCode = "200", content = @Content),
			@ApiResponse(description="Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description="Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description="Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description="Internal Error", responseCode = "500", content = @Content)
			}
	)
	public ResponseEntity<Void> markAsDoneNow(@PathVariable(value="id") long id) {
		service.createHabitDoneNow(id);
//		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(habitVO.getId()).toUri();
//		return ResponseEntity.noContent().location(uri).build();
		return ResponseEntity.noContent().build();
		
	}

}
