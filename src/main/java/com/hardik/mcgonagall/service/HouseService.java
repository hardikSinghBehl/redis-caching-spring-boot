package com.hardik.mcgonagall.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.hardik.mcgonagall.dto.HouseDto;
import com.hardik.mcgonagall.dto.ProfessorDto;
import com.hardik.mcgonagall.entity.House;
import com.hardik.mcgonagall.exception.InvalidHouseIdException;
import com.hardik.mcgonagall.exception.InvalidProfessorIdException;
import com.hardik.mcgonagall.repository.HouseRepository;
import com.hardik.mcgonagall.repository.ProfessorRepository;
import com.hardik.mcgonagall.request.HouseCreationRequest;
import com.hardik.mcgonagall.request.HouseUpdationRequest;
import com.hardik.mcgonagall.response.SuccessResponse;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class HouseService {

	private HouseRepository houseRepository;

	private ProfessorRepository professorRepository;

	public List<House> getAllHouses() {
		log.info("GET ALL Method For Houses");
		return houseRepository.findAll();
	}

	public HouseDto createHouse(HouseCreationRequest houseCreationRequest) {
		log.info("POST Method Called For House " + houseCreationRequest.getName());
		var house = new House();
		var professor = professorRepository.findById(houseCreationRequest.getProfessorId())
				.orElseThrow(() -> new InvalidProfessorIdException());
		house.setName(houseCreationRequest.getName());
		house.setProfessor(professor);
		var savedHouse = houseRepository.save(house);

		return HouseDto.builder().id(savedHouse.getUuid()).name(houseCreationRequest.getName())
				.professor(ProfessorDto.builder().firstName(professor.getFirstName()).lastName(professor.getLastName())
						.id(professor.getUuid()).build())
				.build();
	}

	public HouseDto getHouseById(UUID uuid) {
		log.info("GET Method Called For House: " + uuid.toString());
		var house = houseRepository.findById(uuid).orElseThrow(() -> new InvalidHouseIdException());
		var professor = house.getProfessor();
		return HouseDto.builder().id(house.getUuid()).name(house.getName()).professor(ProfessorDto.builder()
				.firstName(professor.getFirstName()).id(professor.getUuid()).lastName(professor.getLastName()).build())
				.build();
	}

	public HouseDto updateHouse(HouseUpdationRequest houseUpdationRequest) {
		log.info("PUT Method Called For House: " + houseUpdationRequest.getId());
		var house = houseRepository.findById(houseUpdationRequest.getId())
				.orElseThrow(() -> new InvalidHouseIdException());
		house.setName(houseUpdationRequest.getName());
		var professor = house.getProfessor();
		return HouseDto.builder().id(house.getUuid()).name(house.getName()).professor(ProfessorDto.builder()
				.firstName(professor.getFirstName()).id(professor.getUuid()).lastName(professor.getLastName()).build())
				.build();
	}

	public SuccessResponse deleteHouseById(UUID uuid) {
		log.info("DELETE Method Called For House: " + uuid.toString());
		houseRepository.deleteById(uuid);
		return SuccessResponse.builder().status(200).message("Successfully Deleted House Having Id " + uuid).build();
	}

}
