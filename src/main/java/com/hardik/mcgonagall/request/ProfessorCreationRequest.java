package com.hardik.mcgonagall.request;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Builder
@Getter
@Jacksonized
public class ProfessorCreationRequest {
	
	private String firstName;
	private String lastName;

}
