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
import com.erick.model.Habit;
import com.erick.repository.HabitRepository;


@Service
public class HabitService{

	@Autowired
	HabitRepository habitRepository;
	
	private Logger logger = Logger.getLogger(HabitService.class.getName());

	public ResponseVO createHabit(HabitVO habitVO) {
		logger.info("Start to create 'habit'.");
		
		Habit habit = new Habit();
		habit.setName(habitVO.getName());
		habit.setEnabled(habitVO.isEnabled());
		habit.setPeriodicity(habitVO.getPeriodicity());
		
		habit = habitRepository.save(habit);
		
		habitVO.setId(habit.getId());
		habitVO.setName(habit.getName());
		habitVO.setEnabled(habit.isEnabled());
		habitVO.setPeriodicity(habit.getPeriodicity());
		
		ResponseVO responseVO = new ResponseVO();
		responseVO.setValueObject(habitVO);
		responseVO.setMessage("Success.");

		logger.info("Finish to create 'habit'.");
		return responseVO;
	}
	
	public ResponseVO findById(Long id) {
		logger.info("Finding habit by id: " + id + ".");
		ResponseVO responseVO = new ResponseVO();
		responseVO.setValueObject(habitRepository.findById(id));
		return responseVO;
	}	
	
	public ResponseVO updateById(HabitVO habitVO) {
		ResponseVO responseVO = new ResponseVO();

		logger.info("Update by id: " + habitVO.getId() + ".");
		Optional<Habit> habit = habitRepository.findById(habitVO.getId());
		habit.ifPresent(h->{
			h.setName(habitVO.getName());
			h.setEnabled(habitVO.isEnabled());
			h.setPeriodicity(habitVO.getPeriodicity());
			
			h = habitRepository.save(h);
			
			habitVO.setId(h.getId());
			habitVO.setName(h.getName());
			habitVO.setEnabled(h.isEnabled());
			habitVO.setPeriodicity(h.getPeriodicity());
			responseVO.setValueObject(habitVO);
			
			logger.info("Update was a success.");
		});
		return responseVO;
	}
	
	public ResponseVO disableById(long id) {
	
		ResponseVO responseVO = new ResponseVO();

		logger.info("Disasble by id: " + id + ".");
		Optional<Habit> habit = habitRepository.findById(id);
		habit.ifPresent(h->{
			h.setEnabled(false);
			habitRepository.save(h);
			responseVO.setMessage(
					MessageFormat.format("Habit: {0}(id {1}) was successfully disabled.", h.getName(), h.getId())
					);
			logger.info("Delete was a success.");
		});
		return responseVO;
	}
	
	public ResponseVO deleteById(long id) {
		ResponseVO responseVO = new ResponseVO();

		logger.info("Delete by id: " + id + ".");
		Optional<Habit> habit = habitRepository.findById(id);
		habit.ifPresent(h->{
			habitRepository.delete(h);
			responseVO.setMessage(
					MessageFormat.format("Habit: {0}(id {1}) was successfully deleted.", h.getName(), h.getId())
					);
			logger.info("Delete was a success.");
		});
		return responseVO;
	}
	
	public ResponseListVO<HabitVO> findAll() {
		logger.info("Finding all habits.");
		ResponseListVO<HabitVO> responseListVO = new ResponseListVO<HabitVO>();
		List<Habit> habits = habitRepository.findAll();
		List<HabitVO> listVO = habits.stream().map((h) ->{
			HabitVO hvo = new HabitVO();
			hvo.setId(h.getId());
			hvo.setName(h.getName());
			hvo.setEnabled(h.isEnabled());
			hvo.setPeriodicity(h.getPeriodicity());
			return hvo;
		}).collect(Collectors.toList());		
		responseListVO.setValueObject(listVO);
		responseListVO.setMessage("Success.");
		logger.info("Search completed.");
		return responseListVO;
	}
}
