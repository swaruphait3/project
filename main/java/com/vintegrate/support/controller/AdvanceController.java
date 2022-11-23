package com.vintegrate.support.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vintegrate.support.entity.User;
import com.vintegrate.support.repository.UserRepository;

@Controller
public class AdvanceController {
	@Autowired
	private UserRepository userRepository;
@PostMapping("/activeAndDeactive")
	public String activeDectiveHandle(@RequestParam("newstatus") Boolean newstatus,
			@RequestParam("uid") Integer uid, 
			HttpSession session) {
	try {
		System.out.println(newstatus);
		System.out.println(uid);
		User findById = this.userRepository.findById(uid).get();
		findById.setEnabled(newstatus);
		User save = this.userRepository.save(findById);
		System.out.println(save);
		return "redirect:/admin/users/0";
	} catch (Exception e) {
		e.printStackTrace();
		System.out.println(e.getMessage());
		return "redirect:/admin/users/0";
	}
//	List<User> users = this.userRepository.findAll();
//	
//	model.addAttribute("users", users);
	}



}
