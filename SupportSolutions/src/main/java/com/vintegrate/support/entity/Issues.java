package com.vintegrate.support.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="trn_issues")
public class Issues {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int cId;
	private String project_name;
	private String issue;
	private String details;
	@Column(length = 2000)
	private String solution;
	
	@ManyToOne	
	@JsonIgnore
	private User user;
	
}
