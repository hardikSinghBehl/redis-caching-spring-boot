package com.hardik.mcgonagall.dto;

import java.io.Serializable;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Builder
@Getter
@Jacksonized
public class HouseDto implements Serializable {
	
	private UUID id;
	private String name;
	private ProfessorDto professor;

}
