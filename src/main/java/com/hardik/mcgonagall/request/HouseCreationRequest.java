package com.hardik.mcgonagall.request;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class HouseCreationRequest {

	private String name;
	private UUID professorId;
	
}
