package com.hardik.mcgonagall.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.hardik.mcgonagall.dto.ProfessorDto;
import com.hardik.mcgonagall.entity.Professor;
import com.hardik.mcgonagall.exception.InvalidProfessorIdException;
import com.hardik.mcgonagall.repository.ProfessorRepository;
import com.hardik.mcgonagall.request.ProfessorCreationRequest;
import com.hardik.mcgonagall.request.ProfessorUpdationRequest;
import com.hardik.mcgonagall.response.SuccessResponse;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class ProfessorService {

	private ProfessorRepository professorRepository;

	public List<Professor> getAllProfessors() {
		log.info("GET ALL Method Called For Professors");
		return professorRepository.findAll();
	}

	public ProfessorDto createProfessor(ProfessorCreationRequest professorCreationRequest) {
		log.info("POST Method called for professor " + professorCreationRequest.getFirstName() + " "
				+ professorCreationRequest.getLastName());
		var professor = new Professor();
		professor.setFirstName(professorCreationRequest.getFirstName());
		professor.setLastName(professorCreationRequest.getLastName());

		var savedProfessor = professorRepository.save(professor);
		return ProfessorDto.builder().firstName(savedProfessor.getFirstName()).lastName(savedProfessor.getLastName())
				.id(savedProfessor.getUuid()).build();
	}

	public ProfessorDto getProfessorById(UUID uuid) {
		log.info("GET Method Called For Professor: " + uuid.toString());
		var professor = professorRepository.findById(uuid).orElseThrow(() -> new InvalidProfessorIdException());
		return ProfessorDto.builder().id(professor.getUuid()).firstName(professor.getFirstName())
				.lastName(professor.getLastName()).build();
	}

	public SuccessResponse deleteProfessorById(UUID uuid) {
		log.info("DELETE Method Called For Professor " + uuid.toString());
		professorRepository.deleteById(uuid);
		return SuccessResponse.builder().status(200).timestamp(LocalDateTime.now())
				.message("Successfully Deleted Professor: " + uuid.toString()).build();
	}

	public ProfessorDto updateProfessor(ProfessorUpdationRequest professorUpdationRequest) {
		log.info("PUT Method Called For Professor " + professorUpdationRequest.getId().toString());
		var professor = professorRepository.findById(professorUpdationRequest.getId())
				.orElseThrow(() -> new InvalidProfessorIdException());
		return ProfessorDto.builder().firstName(professor.getFirstName()).lastName(professor.getLastName())
				.id(professor.getUuid()).build();
	}

}
