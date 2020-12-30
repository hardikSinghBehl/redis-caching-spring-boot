package com.hardik.mcgonagall.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "pet")
@Getter
@Setter
public class Pet implements Serializable{

	@Id
	@Column(name = "uuid", nullable = false, unique = true)
	private UUID uuid;
	
	@Column(name = "type", nullable = false)
	private String type;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "created_on")
	private LocalDateTime createdOn;
	
	@Column(name = "updated_on")
	private LocalDateTime updatedOn;

	@PrePersist
	public void onCreate() {
		this.uuid = UUID.randomUUID();
		this.createdOn = LocalDateTime.now();
		this.updatedOn = LocalDateTime.now();
	}
	
	@PreUpdate
	public void onUpdate() {
		this.updatedOn = LocalDateTime.now();
	}
}
