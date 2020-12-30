package com.hardik.mcgonagall.response;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class ExceptionResponse {

	private Integer status;
	private String message;
	private String details;
	private LocalDateTime timestamp;

}
