package com.hardik.mcgonagall.request;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class WizardUpdationRequest {
	
	private UUID id;
	private String firstName;
	private String lastName;
	private String gender;

}
