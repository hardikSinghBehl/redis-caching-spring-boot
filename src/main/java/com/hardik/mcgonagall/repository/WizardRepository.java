package com.hardik.mcgonagall.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hardik.mcgonagall.entity.Wizard;

@Repository
public interface WizardRepository extends JpaRepository<Wizard, UUID> {

}
