package com.hardik.mcgonagall.controller;

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

import com.hardik.mcgonagall.dto.WizardDto;
import com.hardik.mcgonagall.entity.Wizard;
import com.hardik.mcgonagall.request.WizardCreationRequest;
import com.hardik.mcgonagall.request.WizardUpdationRequest;
import com.hardik.mcgonagall.response.SuccessResponse;
import com.hardik.mcgonagall.service.WizardService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class WizardController {

	private WizardService wizardService;

	@GetMapping("/wizard/all")
	@Cacheable(value = "wizards")
	public List<Wizard> getAllWizardsHandler() {
		return wizardService.getAllWizards();
	}

	@GetMapping("/wizard/{uuid}")
	@Cacheable(value = "wizard", key = "#uuid")
	public WizardDto getWizardByIdHandler(@PathVariable(name = "uuid") UUID uuid) {
		return wizardService.getWizardById(uuid);
	}

	@PostMapping("/wizard")
	@Caching(evict = { @CacheEvict(value = "wizards", allEntries = true) })
	public WizardDto createWizardHandler(@RequestBody WizardCreationRequest wizardCreationRequest) {
		return wizardService.createWizard(wizardCreationRequest);
	}

	@PutMapping("/wizard")
	@CachePut(value = "wizard", key = "#wizardUpdationRequest.id")
	public WizardDto updateWizardHandler(@RequestBody WizardUpdationRequest wizardUpdationRequest) {
		return wizardService.updateWizard(wizardUpdationRequest);
	}

	@DeleteMapping("/wizard/{uuid}")
	@Caching(evict = { @CacheEvict(value = "wizard", key = "#uuid"), @CacheEvict(value = "wizards", allEntries = true) })
	public SuccessResponse deleteWizardByIdHandler(@PathVariable(name = "uuid") UUID uuid) {
		return wizardService.deleteWizardById(uuid);
	}
}
