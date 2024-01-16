package com.erick.service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erick.controller.valueObjects.v1.HabitVO;
import com.erick.controller.valueObjects.v1.ResponseListVO;
import com.erick.controller.valueObjects.v1.ResponseVO;
import com.erick.exceptions.ResourceNotFoundException;
import com.erick.model.Habit;
import com.erick.repository.HabitRepository;


@Service
public class HabitService{

	@Autowired
	HabitRepository habitRepository;
	
	private Logger logger = Logger.getLogger(HabitService.class.getName());

	public Long createHabit(HabitVO habitVO) {
		String name = habitVO.getName();
		logger.info(MessageFormat.format("Initiating the creation of a new habit: '{0}'.", name));
		
		Habit habit = new Habit();
		habit.setName(name);
		habit.setEnabled(habitVO.isEnabled());
		habit.setPeriodicity(habitVO.getPeriodicity());
		habit = habitRepository.save(habit);
		
		Long id = habit.getId();
		logger.info(MessageFormat.format("A new habit ({0} ID:{1}) has been created successfully.",name, id));
		return id;
	}
	
	public ResponseVO findById(Long id) {
		logger.info(MessageFormat.format("Searching for the habit with ID: {0}.",id));
		
		Optional<Habit> habit = habitRepository.findById(id);
		HabitVO habitVO = new HabitVO();
		habit.ifPresent(h->{
			habitVO.setId(h.getId());
			habitVO.setName(h.getName());
			habitVO.setEnabled(h.isEnabled());
			habitVO.setPeriodicity(h.getPeriodicity());
		});
		habit.orElseThrow(() -> habitNotFound(id));
		
		ResponseVO responseVO = new ResponseVO();
		responseVO.setValueObject(habitVO);

		logger.info(MessageFormat.format("Found the habit with ID: {0} successfully.",id));
		return responseVO;
	}	
	
	public void updateById(HabitVO habitVO) {
		String name = habitVO.getName();
		Long id = habitVO.getId();
		logger.info(MessageFormat.format("Starting the update of the habit {0} ID:{0}.", name, id));
		
		Optional<Habit> habit = habitRepository.findById(id);
		habit.ifPresent(h->{
			h.setName(name);
			h.setEnabled(habitVO.isEnabled());
			h.setPeriodicity(habitVO.getPeriodicity());
			h = habitRepository.save(h);
			logger.info(MessageFormat.format("The habit ({0}, ID:{1}) has been successfully updated.", name, id));
		});
		habit.orElseThrow(() -> habitNotFound(id));
	}
	
	public void disableById(long id) {
		logger.info(MessageFormat.format("Starting the disable of the habit ID:{0}.", id));
		
		logger.info(MessageFormat.format("The habit ({0}, ID:{1}) has been successfully disabled.", id, id));
		
		Optional<Habit> habit = habitRepository.findById(id);
		habit.ifPresent(h->{
			h.setEnabled(false);
			habitRepository.save(h);
			logger.info(MessageFormat.format("The habit ({0}, ID:{1}) has been successfully disabled.", h.getName(), id));
			});
		habit.orElseThrow(() -> habitNotFound(id));
	}
	
	public void deleteById(long id) {
		logger.info(MessageFormat.format("Starting the deletion of the habit ID:{0}.", id));
		
		Optional<Habit> habit = habitRepository.findById(id);
		habit.ifPresent(h->{
			habitRepository.delete(h);
			logger.info(MessageFormat.format("The habit ({0}, ID:{1}) has been successfully deleted.",h.getName(), id));
		});
		
		habit.orElseThrow(() -> habitNotFound(id));
	}
	
	public ResponseListVO<HabitVO> findAll() {
		logger.info("Initiating the retrieval of all habits.");
		
		List<Habit> habits = habitRepository.findAll();
		List<HabitVO> listVO = habits.stream().map((h) ->{
			HabitVO hvo = new HabitVO();
			hvo.setId(h.getId());
			hvo.setName(h.getName());
			hvo.setEnabled(h.isEnabled());
			hvo.setPeriodicity(h.getPeriodicity());
			return hvo;
		}).collect(Collectors.toList());		

		ResponseListVO<HabitVO> responseListVO = new ResponseListVO<HabitVO>();
		responseListVO.setValueObject(listVO);
		
		logger.info("Successfully retrieved all habits.");
		return responseListVO;
	}
	
	private ResourceNotFoundException habitNotFound(long id) {
		String message = MessageFormat.format("No habit found with ID: {0}.",id);
		logger.info(message);
		return new ResourceNotFoundException(message);
	}

}
