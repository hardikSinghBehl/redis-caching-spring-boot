package com.hardik.mcgonagall.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hardik.mcgonagall.entity.House;

@Repository
public interface HouseRepository extends JpaRepository<House, UUID> {

}
