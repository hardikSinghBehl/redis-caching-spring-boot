package com.hardik.mcgonagall.exception.handler;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.hardik.mcgonagall.exception.InvalidHouseIdException;
import com.hardik.mcgonagall.exception.InvalidProfessorIdException;
import com.hardik.mcgonagall.response.ExceptionResponse;

@RestControllerAdvice
public class InvalidHouseIdExceptionHandler {
	
	@ExceptionHandler(InvalidHouseIdException.class)
	public final ResponseEntity handleInvalidEmployeeEmailException(Exception exception) {
		return new ResponseEntity(ExceptionResponse.builder().status(400).message("No House Found")
				.details("Try Searching With A Valid House Id.")
				.timestamp(LocalDateTime.now()).build(), HttpStatus.BAD_REQUEST);
	}

}
