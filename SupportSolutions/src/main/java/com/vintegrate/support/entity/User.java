package com.vintegrate.support.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;



@Data
@Entity
@Table(name="USER")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@NotBlank(message = "Name field is required !!")
	@Size(min = 2,max = 20,message = "min 2 and max 20 characters are allowed !!")
	private String name;
	@Column(unique = true)
	private String username;
	private String password;
	private String role;
	private boolean enabled;
	
	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true)
	private List<Issues> issues=new ArrayList<>();

	}
	
	
	
	
	

