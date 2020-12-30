package com.hardik.mcgonagall.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Setter;
import lombok.Getter;

@Entity
@Table(name = "wizard")
@Getter
@Setter
public class Wizard implements Serializable{
	
	@Id
	@Column(name = "uuid", nullable = false, unique = true)
	private UUID uuid;
	
	@Column(name = "first_name", nullable = false)
	private String firstName;
	
	@Column(name = "last_name", nullable = false)
	private String lastName;
	
	@Column(name = "gender", nullable = false)
	private String gender;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pet_id", referencedColumnName = "uuid")
    private Pet pet;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "house_id", nullable = false)
	@JsonBackReference
	private House house;
	
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
