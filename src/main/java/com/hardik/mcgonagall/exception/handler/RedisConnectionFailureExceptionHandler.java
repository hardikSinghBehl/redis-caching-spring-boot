package com.hardik.mcgonagall.exception.handler;

import java.time.LocalDateTime;

import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.hardik.mcgonagall.response.ExceptionResponse;

@RestControllerAdvice
public class RedisConnectionFailureExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(RedisConnectionFailureException.class)
	public final ResponseEntity handleInvalidEmployeeEmailException(Exception exception) {
		return new ResponseEntity(ExceptionResponse.builder().status(412).message("No Redis Connection Found")
				.details("Try Starting Redis On Port Specified Before Starting The Application").timestamp(LocalDateTime.now()).build(), HttpStatus.PRECONDITION_FAILED);
	}

}
