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

import com.hardik.mcgonagall.dto.HouseDto;
import com.hardik.mcgonagall.entity.House;
import com.hardik.mcgonagall.request.HouseCreationRequest;
import com.hardik.mcgonagall.request.HouseUpdationRequest;
import com.hardik.mcgonagall.response.SuccessResponse;
import com.hardik.mcgonagall.service.HouseService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class HouseController {
	
	private HouseService houseService;
	
	@GetMapping("/house/all")
	@Cacheable(value = "houses")
	public List<House> getAllHousesHandler(){
		return houseService.getAllHouses();
	}
	
	@GetMapping("/house/{uuid}")
	@Cacheable(value = "house", key = "#uuid")
	public HouseDto getHouseByIdHandler(@PathVariable(name = "uuid")UUID uuid) {
		return houseService.getHouseById(uuid);
	}
	
	@PostMapping("/house")
	@Caching(evict = { @CacheEvict(value = "houses", allEntries = true) })
	public HouseDto createHouseHandler(@RequestBody HouseCreationRequest houseCreationRequest) {
		return houseService.createHouse(houseCreationRequest);
	}
	
	@PutMapping("/house")
	@CachePut(value = "house", key = "#houseUpdationRequest.id")
	public HouseDto updateHouseHandler(@RequestBody HouseUpdationRequest houseUpdationRequest) {
		return houseService.updateHouse(houseUpdationRequest);
	}
	
	@DeleteMapping("/house/{uuid}")
	@Caching(evict = { @CacheEvict(value = "house", key = "#uuid"), @CacheEvict(value = "houses", allEntries = true) })
	public SuccessResponse deleteHouseByIdHandler(@PathVariable(name = "uuid")UUID uuid) {
		return houseService.deleteHouseById(uuid);
	}

}
