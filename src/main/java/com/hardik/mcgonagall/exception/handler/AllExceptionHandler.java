package com.hardik.mcgonagall.exception.handler;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.hardik.mcgonagall.exception.InvalidProfessorIdException;
import com.hardik.mcgonagall.response.ExceptionResponse;

@RestControllerAdvice
public class AllExceptionHandler extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler(Exception.class)
	public final ResponseEntity handleInvalidEmployeeEmailException(Exception exception) {
		return new ResponseEntity(ExceptionResponse.builder().status(404).message("Something Went Wrong")
				.details("Go To Documentation: http:localhost:8080/mcgonagall/api/v1/swagger-ui.html")
				.timestamp(LocalDateTime.now()).build(), HttpStatus.NOT_FOUND);
	}

}
