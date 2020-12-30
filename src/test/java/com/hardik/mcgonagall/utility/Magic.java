package com.hardik.mcgonagall.utility;

import org.apache.commons.lang3.RandomStringUtils;

import com.hardik.mcgonagall.entity.House;
import com.hardik.mcgonagall.entity.Professor;
import com.hardik.mcgonagall.request.WizardCreationRequest;

public class Magic {

	public static Professor getProfessorObject() {
		var professor = new Professor();
		professor.setFirstName(RandomStringUtils.randomAlphabetic(6, 15));
		professor.setLastName(RandomStringUtils.randomAlphabetic(6, 14));
		return professor;
	}

	public static House getHouseObject(Professor professor) {
		var house = new House();
		house.setName(RandomStringUtils.randomAlphabetic(8, 12));
		house.setProfessor(professor);
		return house;
	}

	public static WizardCreationRequest getWizardProjectCreationRequest(House house) {
		return WizardCreationRequest.builder().firstName(RandomStringUtils.randomAlphabetic(6, 15))
				.lastName(RandomStringUtils.randomAlphabetic(6, 15)).gender("Male")
				.petName(RandomStringUtils.randomAlphabetic(6, 15)).petType(RandomStringUtils.randomAlphabetic(6, 15))
				.houseId(house.getUuid()).build();
	}
}
