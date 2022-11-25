package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Role;
import com.example.demo.repository.RoleRepo;

@Service
public class RoleService {
	@Autowired
	private RoleRepo roleRepo;
public Role createNewRole(Role role) {
	return roleRepo.save(role);	
}
}
