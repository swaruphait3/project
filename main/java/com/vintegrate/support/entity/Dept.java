package com.vintegrate.support.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "deptmn")
public class Dept {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
private int id;
private String department;
@OneToMany(mappedBy = "department",cascade = CascadeType.ALL)
private List<Project> project;


@OneToMany
private List<User> user;

@Transient
private Integer count;
}
