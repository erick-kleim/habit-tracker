package com.erick.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.erick.controller.valueObjects.v1.HabitVO;
import com.erick.controller.valueObjects.v1.ResponseListVO;

@RestController
@RequestMapping("/habit/v1")
public class HabitController {
	
//	@GetMapping(produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})	
//	public ResponseListVO<HabitVO> findAll() {
//		ResponseListVO<HabitVO> rlvo = new ResponseListVO<>();
//		rlvo.setValueObject(diceServices.findAll());
//		rlvo.add(diceServices.linkTofindAll());
//		return rlvo;
//	}
	
	@GetMapping()	
	public ResponseListVO<HabitVO> findAll() {
		return null;
	}

}
