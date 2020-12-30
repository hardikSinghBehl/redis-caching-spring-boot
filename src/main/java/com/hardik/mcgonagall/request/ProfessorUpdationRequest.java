package com.hardik.mcgonagall.request;

import java.util.UUID;

import com.google.auto.value.AutoValue.Builder;

import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class ProfessorUpdationRequest {
	
	private UUID id;
	private String firstName;
	private String lastName;

}
