package com.erick.service;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erick.controller.valueObjects.v1.HabitVO;
import com.erick.controller.valueObjects.v1.ResponseVO;
import com.erick.exceptions.DateParserException;
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
	
	public void createHabitDoneOnDate(long habitId, String date){
		Optional<LocalDate> doneDate = parseToLocalDate(date);
		
		doneDate.ifPresent(localDate -> {
			logger.info(MessageFormat.format("Searching for the habit with ID: {0}",habitId));
			Optional<Habit> habit = habitRepository.findById(habitId);
			habit.ifPresent(h-> {
				logger.info(MessageFormat.format("Marking the habit ({0}, ID:{1}) as done on the date: {2}.",
						h.getName(),
						habitId,
						localDate));
				habitDoneRepository.save(new HabitDone(h, doneDate.get()));
				});
			habit.orElseThrow(() -> habitNotFound(habitId));
		});
		doneDate.orElseThrow(()-> parseNotAllowed(date));
		logger.info("The habit was marked as done.");
	}
	
	public void findByHabitToday(Long habitId) {
		findByHabitOnDate(habitId, LocalDate.now());
	}
	
	public ResponseVO findByHabitOnDate(Long habitId, String doneDate) {
		Optional<LocalDate> parsedDate = parseToLocalDate(doneDate);
		parsedDate.orElseThrow(()-> parseNotAllowed(doneDate));
		return findByHabitOnDate(habitId, parsedDate.get());	
	}
	
	public ResponseVO findByHabitOnDate(Long habitId, LocalDate doneDate) {
		
		logger.info(MessageFormat.format("Searching for the habit with ID: {0}.",habitId));
		Optional<Habit> habit = habitRepository.findById(habitId);
		ResponseVO response = new ResponseVO();
		habit.ifPresent(h->{
			Optional<List<HabitDone>> habitDone = habitDoneRepository.findByHabitAndDoneDate(h, doneDate);
			habitDone.ifPresent(hd->{
				HabitVO habitVO = new HabitVO();
				habitVO.setId(h.getId());
				habitVO.setName(h.getName());
				habitVO.setEnabled(h.isEnabled());
				habitVO.setPeriodicity(h.getPeriodicity());
				habitVO.setDoneDates(Arrays.asList(doneDate));
				response.setValueObject(habitVO);
				logger.info(MessageFormat.format("Successfully found the habit ({0}, ID:{1}) on date: {2}.",h.getName() ,habitId, doneDate));
			});
			habitDone.orElseThrow(()-> doneHabitNotFound(h.getName(), habitId, doneDate));
		});
		habit.orElseThrow(() -> habitNotFound(habitId));
		return response;
	}
	
	private Optional<LocalDate> parseToLocalDate(String date) {
		try {
			logger.info(MessageFormat.format("Parsing the date: {0}", date));
			return Optional.of(LocalDate.parse(date));
		} catch (DateTimeParseException e) {
			return Optional.empty();
		}
	}
	
	private ResourceNotFoundException doneHabitNotFound(String habitName, Long habitId, LocalDate date) {
		String message = MessageFormat.format("The habit ({0}, ID:{1}) was not done on date {2}",habitName, habitId, date);
		logger.info(message);
		return new ResourceNotFoundException(message);
	}
	
	private ResourceNotFoundException habitNotFound(long id) {
		String message = MessageFormat.format("No habit found with ID: {0}.",id);
		logger.info(message);
		return new ResourceNotFoundException(message);
	}
	
	private DateParserException parseNotAllowed(String date) {
		String message = MessageFormat.format("Date {0} should be a text such as 2000-12-31", date);
		logger.info(message);
		return new DateParserException(message);
	}
	
}
