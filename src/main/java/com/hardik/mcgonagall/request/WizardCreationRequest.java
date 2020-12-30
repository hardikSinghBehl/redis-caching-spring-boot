package com.hardik.mcgonagall.request;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class WizardCreationRequest {
	
	private String firstName;
	private String lastName;
	private String gender;
	private String petType;
	private String petName;
	private UUID houseId;

}
