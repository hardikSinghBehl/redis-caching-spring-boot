package com.hardik.mcgonagall.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hardik.mcgonagall.dto.ProfessorDto;
import com.hardik.mcgonagall.entity.Professor;
import com.hardik.mcgonagall.request.ProfessorCreationRequest;
import com.hardik.mcgonagall.request.ProfessorUpdationRequest;
import com.hardik.mcgonagall.response.SuccessResponse;
import com.hardik.mcgonagall.service.ProfessorService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class ProfessorController {

	private ProfessorService professorService;

	@GetMapping("/professor/all")
	@Cacheable(value = "professors")
	public List<Professor> getAllProfessorsHandler() {
		return professorService.getAllProfessors();
	}

	@GetMapping("professor/{uuid}")
	@Cacheable(value = "professor", key = "#uuid")
	public ProfessorDto getProfessorByIdHandler(@PathVariable(name = "uuid") UUID uuid) {
		return professorService.getProfessorById(uuid);
	}

	@PostMapping("/professor")
	@Caching(evict = { @CacheEvict(value = "professors", allEntries = true) })
	public ProfessorDto createProfessorHandler(@RequestBody ProfessorCreationRequest professorCreationRequest) {
		return professorService.createProfessor(professorCreationRequest);
	}

	@PutMapping("/professor")
	@CachePut(value = "professor", key = "#professorUpdationRequest.id")
	public ProfessorDto updateProfesorHandler(@RequestBody ProfessorUpdationRequest professorUpdationRequest) {
		return professorService.updateProfessor(professorUpdationRequest);
	}

	@DeleteMapping("/professor/{uuid}")
	@Caching(evict = { @CacheEvict(value = "professor", key = "#uuid"),
			@CacheEvict(value = "professors", allEntries = true) })
	public SuccessResponse deleteProfessorByIdhandler(@PathVariable(name = "uuid") UUID uuid) {
		return professorService.deleteProfessorById(uuid);
	}

}
