package com.erick.service;

import java.text.MessageFormat;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erick.exceptions.ResourceNotFoundException;
import com.erick.model.Habit;
import com.erick.model.HabitDone;
import com.erick.repository.HabitDoneRepository;
import com.erick.repository.HabitRepository;

@Service
public class HabitDoneService {

	@Autowired
	HabitDoneRepository habitDoneRepository;
	
	@Autowired
	HabitRepository habitRepository;
	
	private Logger logger = Logger.getLogger(HabitDoneService.class.getName());
	
	public void createHabitDoneNow(long habitId){
		logger.info(MessageFormat.format("Searching for the habit with ID: {0}",habitId));
		Optional<Habit> habit = habitRepository.findById(habitId);
		habit.ifPresent(h-> {
			logger.info(MessageFormat.format("Marking the habit ({0}, ID:{1}) as done.",h.getName(), habitId));
			habitDoneRepository.save(new HabitDone(h));});
		habit.orElseThrow(() -> habitNotFound(habitId));
		logger.info("The habit was marked as done.");
	}
	
	private ResourceNotFoundException habitNotFound(long id) {
		String message = MessageFormat.format("No habit found with ID: {0}.",id);
		logger.info(message);
		return new ResourceNotFoundException(message);
	}
	
}
