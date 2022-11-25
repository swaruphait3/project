package com.example.demo.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.repository.RoleRepo;
import com.example.demo.repository.UserRepositiry;

@Service
public class UserService {
	@Autowired
 private UserRepositiry userRepositiry;
	@Autowired
private RoleRepo roleRepo;
	public User registerNewUser(User user) {
	 return	userRepositiry.save(user); 
	}
	
	public void initRolesUser() {
		Role adminRole = new Role();
		adminRole.setRoleName("Admin");
		roleRepo.save(adminRole);
		
		Role userRole = new Role();
		userRole.setRoleName("User");
		roleRepo.save(userRole);
		
		User adminUser = new User();
		adminUser.setName("admin");
		adminUser.setUsername("admin");
		adminUser.setPassword("admin");
		Set<Role> adminRoles = new HashSet<>();
//		adminUser.setRole(adminRoles);
		adminRoles.add(adminRole);
		userRepositiry.save(adminUser);
		
		User user = new User();
		user.setName("Swarup");
		user.setUsername("swarup");
		user.setPassword("swarup");
		Set<Role> userRoles = new HashSet<>();
//		user.setRole(userRoles);
		userRoles.add(userRole);
		userRepositiry.save(user);
	}
}
