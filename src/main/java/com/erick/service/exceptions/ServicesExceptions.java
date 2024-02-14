package com.erick.service.exceptions;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.logging.Logger;

import com.erick.exceptions.DateParserException;
import com.erick.exceptions.ResourceNotFoundException;

public class ServicesExceptions {
	public static ResourceNotFoundException habitNotFound(Logger logger, long id) {
		String message = MessageFormat.format("Habit not found with ID: {0}.",id);
		logger.info(message);
		return new ResourceNotFoundException(message);
	}
	
	public static  ResourceNotFoundException doneHabitNotFound(Logger logger, String habitName, Long habitId, LocalDate date) {
		String message = MessageFormat.format("The habit ({0}, ID:{1}) was not done on date {2}",habitName, habitId, date);
		logger.info(message);
		return new ResourceNotFoundException(message);
	}

	
	public static  DateParserException parseNotAllowed(Logger logger, String date) {
		String message = MessageFormat.format("Date {0} should be a text such as 2000-12-31", date);
		logger.info(message);
		return new DateParserException(message);
	}
}
