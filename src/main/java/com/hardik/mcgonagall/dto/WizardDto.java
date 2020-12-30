package com.hardik.mcgonagall.dto;

import java.io.Serializable;
import java.util.UUID;

import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import lombok.Getter;

@Builder
@Getter
@Jacksonized
public class WizardDto implements Serializable {
	
	private UUID id;
	private String firstName;
	private String lastName;
	private String gender;
	private PetDto pet;
	private HouseDto house;

}
