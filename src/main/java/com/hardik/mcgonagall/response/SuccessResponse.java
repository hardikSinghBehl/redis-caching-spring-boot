package com.hardik.mcgonagall.response;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Builder
@Getter
@Jacksonized
public class SuccessResponse implements Serializable{
	
	private Integer status;
	private String message;
	private LocalDateTime timestamp;

}
