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

@RestController
@RequestMapping("/habit/v1")
public class HabitController {
	
	@Autowired
	HabitService habitService;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Void> newHabit(@RequestBody HabitVO habitVO) {
		Long habitId = habitService.createHabit(habitVO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(habitId).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@GetMapping(value = "/{id}")
	public ResponseVO getById( @PathVariable(value="id") long id ) {
		return habitService.findById(id);
	}
	
	@PutMapping()	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> update(@RequestBody HabitVO habitVO) {
		habitService.updateById(habitVO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(habitVO.getId()).toUri();
		return ResponseEntity.noContent().location(uri).build();
		
	}
	
	@PatchMapping(value = "/{id}/disable")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> disableById( @PathVariable(value="id") long id ) {
		habitService.disableById(id);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
		return ResponseEntity.noContent().location(uri).build();
	}
	
	@DeleteMapping(value = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> deleteById( @PathVariable(value="id") long id ) {
		habitService.deleteById(id);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
		return ResponseEntity.noContent().location(uri).build();
	}
	
	@GetMapping()
	public ResponseListVO<HabitVO> getAll() {
		return habitService.findAll();
	}

}
