package com.erick.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.erick.controller.valueObjects.v1.ResponseVO;
import com.erick.service.HabitDoneService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/habit-done/v1/")
@Tag(name = "Done habits", description = "Endpoints to manage done habtis")
public class HabitDoneController {
	
	@Autowired
	HabitDoneService service;
	
	@PutMapping(value = "/habit/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	@Operation(summary = "Sets a habit as done",
		description = "This EP finds a habit by ID and sets as done now.",
		tags = {"Done habits"},
		responses = {
			@ApiResponse(description="Created", responseCode = "201", content = @Content(mediaType = "application/json")),
			@ApiResponse(description="Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description="Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description="Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description="Internal Error", responseCode = "500", content = @Content)
			}
	)
	public ResponseEntity<Void> markAsDoneNow(@PathVariable(value="id") long id) {
		service.createHabitDoneNow(id);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/today").buildAndExpand(id).toUri();
		return ResponseEntity.created(uri).contentType(MediaType.APPLICATION_JSON).build();
		
	}
	
		@PutMapping(value = "/habit/{id}/date/{date}")
		@ResponseStatus(HttpStatus.CREATED)
		@Operation(summary = "Sets a habit as done on a specific date",
			description = "This EP finds a habit by ID and sets as done on a specific date.",
			tags = {"Done habits"},
			responses = {
				@ApiResponse(description="Created", responseCode = "201", content = @Content(mediaType = "application/json")),
				@ApiResponse(description="Bad Request", responseCode = "400", content = @Content),
				@ApiResponse(description="Unauthorized", responseCode = "401", content = @Content),
				@ApiResponse(description="Not Found", responseCode = "404", content = @Content),
				@ApiResponse(description="Internal Error", responseCode = "500", content = @Content)
				}
		)
		public ResponseEntity<Void> markAsDone(@PathVariable(value="id") long id, @PathVariable(value="date") String date) {
			service.createHabitDoneOnDate(id, date);
			URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/date/{date}/habit/{id}").buildAndExpand(date, id).toUri();
			return ResponseEntity.created(uri).contentType(MediaType.APPLICATION_JSON).build();
		}

		@GetMapping(value = "/habit/{id}/date/{date}")
		@Operation(summary = "Finds a done habit on a specific date",
			description = "Return 200 Success if finds a done habit by ID on a specific date, otherwise return 404 not found.",
			tags = {"Done habits"},
			responses = {
				@ApiResponse(description="Ok", responseCode = "200",
						content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseVO.class))),
				@ApiResponse(description="Bad Request", responseCode = "400", content = @Content),
				@ApiResponse(description="Unauthorized", responseCode = "401", content = @Content),
				@ApiResponse(description="Not Found", responseCode = "404", content = @Content),
				@ApiResponse(description="Internal Error", responseCode = "500", content = @Content)
				}
		)
		public ResponseEntity<ResponseVO> doneHabisOnDate( @PathVariable(value="date") String doneDate, @PathVariable(value="id") long habitId) {
			ResponseVO doneHabit = service.findByHabitOnDate(habitId, doneDate);
			return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON).body(doneHabit);
		}
		
		@GetMapping(value = "/habit/{id}/today")
		@Operation(summary = "Finds a done habit today",
			description = "Return 200 Success if finds a habit by ID marked as done on today's date, otherwise return 404 not found.",
			tags = {"Done habits"},
			responses = {
				@ApiResponse(description="Ok", responseCode = "200",
						content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseVO.class))),
				@ApiResponse(description="Bad Request", responseCode = "400", content = @Content),
				@ApiResponse(description="Unauthorized", responseCode = "401", content = @Content),
				@ApiResponse(description="Not Found", responseCode = "404", content = @Content),
				@ApiResponse(description="Internal Error", responseCode = "500", content = @Content)
				}
		)
		public ResponseEntity<ResponseVO> getById( @PathVariable(value="id") long habitId ) {
			ResponseVO doneHabit = service.findByHabitToday(habitId);
			return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON).body(doneHabit);
		}
}
