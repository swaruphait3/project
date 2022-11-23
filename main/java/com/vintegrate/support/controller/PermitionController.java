package com.vintegrate.support.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vintegrate.support.entity.User;
import com.vintegrate.support.repository.UserRepository;

@Controller
public class PermitionController {
	
	@Autowired
	private UserRepository userRepository;
	
	@PostMapping("/ActiveAndDeactive")
public String activeAndDeactive(@ModelAttribute User user, 
		@RequestParam("newStatus") Boolean newStatus,
		@RequestParam("userId") String userId) {
		Integer id = Integer.parseInt(userId);
		User findById = userRepository.findById(id).get();
		findById.setEnabled(newStatus);
		User save = userRepository.save(user);
	return "redirect:/admin/users";
}
}
