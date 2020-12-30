package com.hardik.mcgonagall.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "house")
@Getter
@Setter
@JsonIgnoreProperties(value = {"professor"})
public class House implements Serializable {
	
	@Id
	@Column(name = "uuid", nullable = false, unique = true)
	private UUID uuid;
	
	@Column(name = "name", nullable = false, unique = true)
	private String name;
	
	@OneToMany(mappedBy = "house")
	@JsonManagedReference
	private List<Wizard> wizards;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "professor_id", referencedColumnName = "uuid")
    private Professor professor;

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
