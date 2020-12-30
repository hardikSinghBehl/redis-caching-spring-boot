package com.hardik.mcgonagall.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.transaction.Transactional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.github.fppt.jedismock.RedisServer;
import com.hardik.mcgonagall.dto.WizardDto;
import com.hardik.mcgonagall.entity.House;
import com.hardik.mcgonagall.entity.Professor;
import com.hardik.mcgonagall.repository.HouseRepository;
import com.hardik.mcgonagall.repository.ProfessorRepository;
import com.hardik.mcgonagall.request.WizardCreationRequest;
import com.hardik.mcgonagall.service.WizardService;
import com.hardik.mcgonagall.utility.Magic;

import redis.clients.jedis.Jedis;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class WizardControllerTest {
	
	private RedisServer server;
	
	@Autowired
	private RedisTemplate redisTemplate;
	
	@Autowired
	private HouseRepository houseRepository;
	
	@Autowired
	private ProfessorRepository professorRepository;
	
	@Autowired
	private WizardController wizardController;
	
	@BeforeEach
	public void setUp() throws IOException {
		server = RedisServer.newRedisServer(6969);
		server.start();
		new Jedis(server.getHost(), server.getBindPort());
	}
	
	@Test
	public void cachingTest() throws IOException {
		var savedProfessor = professorRepository.save(Magic.getProfessorObject());
		var savedHouse = houseRepository.save(Magic.getHouseObject(savedProfessor));
		var savedWizard = wizardController.createWizardHandler(Magic.getWizardProjectCreationRequest(savedHouse));
		wizardController.getWizardByIdHandler(savedWizard.getId());
		assertThat(redisTemplate.keys("*").size()).isEqualTo(1);
	}
	
	@AfterEach
	public void cleanUp() {
		server.stop();
	}

}
