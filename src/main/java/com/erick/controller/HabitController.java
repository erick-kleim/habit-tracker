package com.erick.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.erick.controller.valueObjects.v1.HabitVO;
import com.erick.controller.valueObjects.v1.ResponseListVO;
import com.erick.controller.valueObjects.v1.ResponseVO;
import com.erick.service.HabitService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/habit/v1")
@Tag(name = "Habits", description = "Endpoints to manage habits")
public class HabitController {
	
	@Autowired
	HabitService habitService;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@Operation(summary = "Create a new habit", description = "Create a new habit.",
	tags = {"Habits"},
	responses = {
			@ApiResponse(description="Created", responseCode = "201", content = @Content),
			@ApiResponse(description="Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description="Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description="Internal Error", responseCode = "500", content = @Content)
			}
	)
	public ResponseEntity<Void> newHabit(@RequestBody HabitVO habitVO) {
		Long habitId = habitService.createHabit(habitVO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(habitId).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@GetMapping(value = "/{id}")
	@Operation(summary = "Find a habit by ID", description = "Find a habit by ID.",
	tags = {"Habits"},
	responses = {
			@ApiResponse(description="Sucess", responseCode = "200",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseVO.class))),
			@ApiResponse(description="Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description="Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description="Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description="Internal Error", responseCode = "500", content = @Content)
			}
	)
	public ResponseVO getById( @PathVariable(value="id") long id ) {
		return habitService.findById(id);
	}
	
	@PutMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Operation(summary = "Update a habit",
		description = "This EP finds a habit by ID and updates the object with the body information.",
		tags = {"Habits"},
		responses = {
			@ApiResponse(description="Sucess", responseCode = "200", content = @Content),
			@ApiResponse(description="Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description="Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description="Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description="Internal Error", responseCode = "500", content = @Content)
			}
	)
	public ResponseEntity<Void> update(@RequestBody HabitVO habitVO) {
		habitService.updateById(habitVO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(habitVO.getId()).toUri();
		return ResponseEntity.noContent().location(uri).build();
		
	}
	
	@PatchMapping(value = "/{id}/disable")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Operation(summary = "Disable a habit",
		description = """
				This EP finds a habit by ID and disables it.
				A habit disabled cannot be marked as done.
				""",
		tags = {"Habits"},
		responses = {
			@ApiResponse(description="Sucess", responseCode = "200", content = @Content),
			@ApiResponse(description="Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description="Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description="Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description="Internal Error", responseCode = "500", content = @Content)
			}
		)
	public ResponseEntity<Void> disableById( @PathVariable(value="id") long id ) {
		habitService.disableById(id);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
		return ResponseEntity.noContent().location(uri).build();
	}
	
	@DeleteMapping(value = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Operation(summary = "Delete a habit",
		description = """
				This EP finds a habit by ID and deletes it.
				Only habits without a "done" mark can be deleted.
				""",
		tags = {"Habits"},
		responses = {
			@ApiResponse(description="Sucess", responseCode = "200", content = @Content),
			@ApiResponse(description="Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description="Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description="Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description="Internal Error", responseCode = "500", content = @Content)
			}
		)
	public ResponseEntity<Void> deleteById( @PathVariable(value="id") long id ) {
		habitService.deleteById(id);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
		return ResponseEntity.noContent().location(uri).build();
	}
	
	@GetMapping
	@Operation(summary = "Finds all habits", description = "Finds all habits, either disabled or enabled.", tags = {
			"Habits" }, responses = { @ApiResponse(description = "Sucess", responseCode = "200", content = {
					@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ResponseListVO.class))) }),
					@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
					@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
					@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
					@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content) })
	public ResponseListVO<HabitVO> getAll() {
		return habitService.findAll();
	}

}
