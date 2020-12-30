package com.hardik.mcgonagall.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.hardik.mcgonagall.dto.HouseDto;
import com.hardik.mcgonagall.dto.PetDto;
import com.hardik.mcgonagall.dto.ProfessorDto;
import com.hardik.mcgonagall.dto.WizardDto;
import com.hardik.mcgonagall.entity.Pet;
import com.hardik.mcgonagall.entity.Wizard;
import com.hardik.mcgonagall.exception.InvalidWizardIdException;
import com.hardik.mcgonagall.repository.HouseRepository;
import com.hardik.mcgonagall.repository.PetRepository;
import com.hardik.mcgonagall.repository.WizardRepository;
import com.hardik.mcgonagall.request.WizardCreationRequest;
import com.hardik.mcgonagall.request.WizardUpdationRequest;
import com.hardik.mcgonagall.response.SuccessResponse;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class WizardService {

	private WizardRepository wizardRepository;

	private HouseRepository houseRepository;

	private PetRepository petRepository;

	public List<Wizard> getAllWizards() {
		log.info("GET ALL Method Called For Wizard");
		return wizardRepository.findAll();
	}

	public WizardDto getWizardById(UUID uuid) {
		log.info("GET Method Called For Wizard id: " + uuid.toString());
		var wizard = wizardRepository.findById(uuid).orElseThrow(() -> new InvalidWizardIdException());
		var pet = wizard.getPet();
		var house = wizard.getHouse();
		var professor = house.getProfessor();
		return WizardDto.builder().id(wizard.getUuid()).firstName(wizard.getFirstName()).lastName(wizard.getLastName())
				.gender(wizard.getGender())
				.pet(PetDto.builder().id(pet.getUuid()).type(pet.getType()).name(pet.getName()).build()).house(
						HouseDto.builder().name(house.getName()).id(house.getUuid())
								.professor(ProfessorDto.builder().firstName(professor.getFirstName())
										.lastName(professor.getLastName()).id(professor.getUuid()).build())
								.build())
				.build();
	}

	public SuccessResponse deleteWizardById(UUID uuid) {
		log.info("DELETE Method Called For Wizard id: " + uuid.toString());
		wizardRepository.deleteById(uuid);
		return SuccessResponse.builder().status(200).message("Successfully Deleted Wizard By Id " + uuid.toString())
				.timestamp(LocalDateTime.now()).build();
	}

	public WizardDto createWizard(WizardCreationRequest wizardCreationRequest) {
		log.info("POST Method Called For Wizard : " + wizardCreationRequest.getFirstName() + " "
				+ wizardCreationRequest.getLastName());
		var wizard = new Wizard();
		var pet = new Pet();
		var house = houseRepository.findById(wizardCreationRequest.getHouseId()).get();
		var professor = house.getProfessor();
		pet.setName(wizardCreationRequest.getPetName());
		pet.setType(wizardCreationRequest.getPetType());
		var savedPet = petRepository.save(pet);

		wizard.setFirstName(wizardCreationRequest.getFirstName());
		wizard.setLastName(wizardCreationRequest.getLastName());
		wizard.setGender(wizardCreationRequest.getGender());
		wizard.setHouse(house);
		wizard.setPet(savedPet);

		var savedWizard = wizardRepository.save(wizard);

		return WizardDto.builder().id(savedWizard.getUuid()).firstName(savedWizard.getFirstName())
				.lastName(savedWizard.getLastName()).gender(savedWizard.getGender())
				.pet(PetDto.builder().id(savedPet.getUuid()).type(savedPet.getType()).name(savedPet.getName())
						.build())
				.house(HouseDto.builder().name(house.getName()).id(house.getUuid())
						.professor(ProfessorDto.builder().firstName(professor.getFirstName())
								.lastName(professor.getLastName()).id(professor.getUuid()).build())
						.build())
				.build();
	}

	public WizardDto updateWizard(WizardUpdationRequest wizardUpdationRequest) {
		log.info("PUT Method Called For Wizard : " + wizardUpdationRequest.getFirstName() + " "
				+ wizardUpdationRequest.getLastName());
		var wizard = wizardRepository.findById(wizardUpdationRequest.getId()).get();
		var house = wizard.getHouse();
		var pet = wizard.getPet();
		var professor = house.getProfessor();

		wizard.setFirstName(wizardUpdationRequest.getFirstName());
		wizard.setLastName(wizardUpdationRequest.getLastName());
		wizard.setGender(wizardUpdationRequest.getGender());
		wizard = wizardRepository.save(wizard);

		return WizardDto.builder().id(wizard.getUuid()).firstName(wizard.getFirstName()).lastName(wizard.getLastName())
				.gender(wizard.getGender())
				.pet(PetDto.builder().id(pet.getUuid()).type(pet.getType()).name(pet.getName()).build()).house(
						HouseDto.builder().name(house.getName()).id(house.getUuid())
								.professor(ProfessorDto.builder().firstName(professor.getFirstName())
										.lastName(professor.getLastName()).id(professor.getUuid()).build())
								.build())
				.build();
	}

}
