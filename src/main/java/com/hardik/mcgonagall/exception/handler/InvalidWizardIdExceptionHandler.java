package com.hardik.mcgonagall.exception.handler;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.hardik.mcgonagall.exception.InvalidWizardIdException;
import com.hardik.mcgonagall.response.ExceptionResponse;

@RestControllerAdvice
public class InvalidWizardIdExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(InvalidWizardIdException.class)
	public final ResponseEntity handleInvalidEmployeeEmailException(Exception exception) {
		return new ResponseEntity(ExceptionResponse.builder().status(400).message("No Wizard Found")
				.details("Try Searching With A Valid Wizard Id.")
				.timestamp(LocalDateTime.now()).build(), HttpStatus.BAD_REQUEST);
	}

}
