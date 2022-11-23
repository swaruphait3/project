package com.vintegrate.support.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.vintegrate.support.entity.Dept;
import com.vintegrate.support.entity.Issues;
import com.vintegrate.support.entity.Message;
import com.vintegrate.support.entity.Project;
import com.vintegrate.support.entity.User;
import com.vintegrate.support.repository.DepartmentRepository;
import com.vintegrate.support.repository.ProjectRepository;
import com.vintegrate.support.repository.SolutionRepository;
import com.vintegrate.support.repository.UserRepository;

@Controller
public class HomeController {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
    @Autowired
	private SolutionRepository solutionRepository;
    
	@Autowired
	private ProjectRepository projectRepository;
    
	@Autowired
	private DepartmentRepository departmentRepository;
	
	@Autowired
	private UserRepository userRepository;

	@RequestMapping("/")
	public String home(Model model) {
		List<Dept> dpt=this.departmentRepository.findAll();
		for (Dept dept : dpt) {
			List<Project> project = dept.getProject();
			Integer a = 0;
			for (Project pro : project) {
				int size = this.solutionRepository.IssuesList(pro.getId()).size();
				
				a=a+size;
			}
			dept.setCount(a);
		}
		model.addAttribute("dpt", dpt);
		model.addAttribute("title", "Login - Software Support Solutions");
		return "index";
	}
//*************user login*************
	@RequestMapping("/login")
	public String loginHandler(Model model) {
		model.addAttribute("title", "Login - Software Support Solutions");
		return "login";
	}
//*************user registration*************
	@RequestMapping("/signup")
	public String signup(Model model) {
		model.addAttribute("title", "Register - Software Support Solutions");
		model.addAttribute("user", new User());
		List<Dept> dprt=this.departmentRepository.findAll();
		model.addAttribute("dprt", dprt);
		return "signup";
	}

	@RequestMapping(value = "/do_register", method = RequestMethod.POST)
	public String registerUser(@ModelAttribute("user") User user,  Model model,
			HttpSession session) {
		try {
			user.setRole("ROLE_USER");
			user.setEnabled(true);
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			System.out.println("USER " + user);
			User result = this.userRepository.save(user);
			model.addAttribute("user", new User());
			session.setAttribute("message", new Message("Successfully Registered !!", "alert-success"));
			return "/signup";

		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("user", user);
			session.setAttribute("message", new Message("Something Went wrong !! " + e.getMessage(), "alert-danger"));
			return "/signup";
		}
	}
	
	@GetMapping("/showErrors/{id}")
	public String softwareErrorShow(@PathVariable("id") int id,Model model) {
		Dept dept = departmentRepository.findById(id).get();
		List<Project> project = dept.getProject();
		List<Issues> iss = new ArrayList<>();
		for (Project pro : project) {
			List<Issues> issuesList = solutionRepository.IssuesList(pro.getId());
		
			iss.addAll(issuesList);
		}
		System.out.println(iss);	
	model.addAttribute("issuesList", iss);
		return "/show_error";
		
	}
}
//	@RequestMapping(value = "/do_register", method = RequestMethod.POST)
//	public String registerUser(@Valid @ModelAttribute("user") User user,  Model model,
//			HttpSession session) {
//		try {
//			user.setRole("ROLE_USER");
//			user.setEnabled(true);
//			user.setPassword(passwordEncoder.encode(user.getPassword()));
//			System.out.println("USER " + user);
//			User result = this.userRepository.save(user);
//			model.addAttribute("user", new User());
//			session.setAttribute("message", new Message("Successfully Registered !!", "success"));
//			return "admin/signup";
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			model.addAttribute("user", user);
//			session.setAttribute("message", new Message("Something Went wrong !! " + e.getMessage(), "danger"));
//			return "admin/signup";
//		}
//	}
//	
//}
//	// handler for registering user
//	@RequestMapping(value = "/do_register", method = RequestMethod.POST)
//	public String registerUser(@Valid @ModelAttribute("user") User user,  Model model,
//			HttpSession session) {
////BindingResult result1,
//	//	@RequestParam(value = "agreement", defaultValue = "false") boolean agreement,
//		try {
//
////			if (!agreement) {
////				System.out.println("You have not agreed the terms and conditions");
////				throw new Exception("You have not agreed the terms and conditions");
////			}
////
////			if (result1.hasErrors()) {
////				System.out.println("ERROR " + result1.toString());
////				model.addAttribute("user", user);
////				return "signup";
////			}
//
//			user.setRole("ROLE_USER");
//			user.setEnabled(true);
//			
//			user.setPassword(passwordEncoder.encode(user.getPassword()));
//
////			System.out.println("Agreement " + agreement);
//			System.out.println("USER " + user);
//
//			User result = this.userRepository.save(user);
//			List<Dept> deptm=this.departmentRepository.findAll();
//			model.addAttribute("deptm", deptm);
//			model.addAttribute("user", new User());
//
//			session.setAttribute("message", new Message("Successfully Registered !!", "alert-success"));
//			return "signup";
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			model.addAttribute("user", user);
//			session.setAttribute("message", new Message("Something Went wrong !! " + e.getMessage(), "alert-danger"));
//			return "signup";
//		}
//
//	}