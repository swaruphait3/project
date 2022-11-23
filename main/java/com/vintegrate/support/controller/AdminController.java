package com.vintegrate.support.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vintegrate.support.entity.Dept;
import com.vintegrate.support.entity.Message;
import com.vintegrate.support.entity.Project;
import com.vintegrate.support.entity.User;
import com.vintegrate.support.repository.DepartmentRepository;
import com.vintegrate.support.repository.ProjectRepository;
import com.vintegrate.support.repository.UserRepository;

@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private UserRepository userRepository;
   

	
	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private DepartmentRepository departmentRepository;

	@ModelAttribute
	public void addCommonData(Model model, Principal principal) {
		String userName = principal.getName();
		User user = this.userRepository.getUserByUserName(userName);
		model.addAttribute("user", user);
	}

	@RequestMapping("/index")
	public String dashboard(Model model, Principal principal) {
		model.addAttribute("title", "User Dashboard");
		return "admin/admin_dashboard";
	}

//***** department ******
	@GetMapping("/add_dept")
	public String addDepartment(Model model) {
		try {
			List<Dept> dp = this.departmentRepository.findAll();
			model.addAttribute("dp", dp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "admin/add_dept";
	}

	@PostMapping("/addDeptForm")
	public String addDeptmentForm(@ModelAttribute("dept") Dept deptmnt, HttpSession session) {
		System.out.println(deptmnt);
		try {
			this.departmentRepository.save(deptmnt);
			session.setAttribute("message", new Message("Succesffully Added", "success"));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			session.setAttribute("message", new Message("Something went wrong!! try again..", "danger"));
		}
		return "redirect:/admin/add_dept";
	}

//deprtment update
	@GetMapping("/updateDepart/{id}")
	public String editDepartmentForm(@PathVariable("id") Integer id, Model model, HttpSession session) {
		try {
			Dept dp = this.departmentRepository.findById(id).get();
			model.addAttribute("dp", dp);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "admin/update_dprt";
	}

	@PostMapping("/updateDept")
	public String updateDepartment(@ModelAttribute("deprt") Dept deprt,
			Model model,
			HttpSession session) {

		try {
			System.out.println(deprt.getDepartment());
			this.departmentRepository.save(deprt);
			session.setAttribute("message", new Message("Successfully Updated", "success"));
			return "redirect:/admin/add_dept";

		} catch (Exception e) {
			session.setAttribute("message", new Message("Something went wrong!" + e.getMessage(), "danger"));
			return "redirect:/admin/update_dprt";
		}
	}

//####################User Managment*****
	@GetMapping("/add_user")
	public String addNewUser(Model model) {
		model.addAttribute("title", "Add New User");
		model.addAttribute("user", new User());
		List<Dept> dept = this.departmentRepository.findAll();
		model.addAttribute("dept", dept);
		System.out.println(dept);
		return "admin/signup";
	}

//	@RequestMapping(value = "/register", method = RequestMethod.POST)
//	public String registerUser(@Valid @ModelAttribute("user") User user, Model model, HttpSession session) {
//		try {
//			user.setRole("ROLE_USER");
//			user.setEnabled(true);
//			user.setPassword(passwordEncoder.encode(user.getPassword()));
//			Dept dept = departmentRepository.findById(user.getDepId()).get();
//			user.setDepartment(dept);
//			System.out.println("USER " + user);
//			User result = this.userRepository.save(user);
//			model.addAttribute("user", new User());
//			session.setAttribute("message", new Message("Successfully Registered !!", "alert-success"));
//			return "redirect:/admin/users";
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			model.addAttribute("user", user);
//			session.setAttribute("message", new Message("Something Went wrong !! " + e.getMessage(), "alert-danger"));
//			return "admin/signup";
//		}
//	}

//**************show all users*****************
	@GetMapping("/users/{page}")
	public String showUserDetails(@PathVariable("page") Integer page,
			 Model model) {
		
  //currentpage-page
  //show per page= 8
		Pageable pageable =PageRequest.of(page, 8);
		
		Page<User> users = this.userRepository.findPageByUser(pageable);
		
		model.addAttribute("users", users);
		model.addAttribute("currentPage", page);
		List<Dept> dept = this.departmentRepository.findAll();
		model.addAttribute("dept", dept);
		model.addAttribute("totalPages", users.getTotalPages());
		return "admin/users";
	}
	
//********************	
	
//	@GetMapping("/users")
//	public String showUserDetails(Model model) {
//		List<User> users = this.userRepository.findAll();
//		model.addAttribute("users", users);
//		return "admin/users";
//	}

//################### Project Mangment*************
	@GetMapping("/add_project")
	public String addProjects(Model model) {
		try {
			List<Dept> dept = this.departmentRepository.findAll();
			model.addAttribute("dept", dept);
			List<Project> proj = this.projectRepository.findAll();
			model.addAttribute("proj", proj);
			System.out.println(dept);
			System.out.println(proj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "admin/add_project";
	}


	@PostMapping("/addProjectForm")
	public String addProjectForm(@ModelAttribute("proj") Project proj, HttpSession session) {
		try {
			this.projectRepository.save(proj);
			session.setAttribute("message", new Message("Succesffully Added", "success"));
			return "redirect:/admin/add_project";
		} catch (Exception e) {

			session.setAttribute("message", new Message("Something went wrong!! " + e.getMessage(), "danger"));
			return "redirect:/admin/add_project";
		}
	}

	@GetMapping("/updateProject/{id}")
	public String editProjectForm(@PathVariable("id") Integer id, Model model, HttpSession session) {
		try {
			Project projects=this.projectRepository.findById(id).get();
			model.addAttribute("projects", projects);
			List<Dept> dept= this.departmentRepository.findAll();
			model.addAttribute("dept", dept);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "admin/update_project";
	}
	
	@PostMapping("/updateProject")
	public String upateProject(@ModelAttribute Project project) {
		try {
			this.projectRepository.save(project);
			
			return "redirect:/admin/add_project";
		} catch (Exception e) {
			e.printStackTrace();
			return "admin/update_project";
		}
	}
	
	
	@GetMapping("/delete/{id}")
	@Transactional
	public String deleteContact(@PathVariable("id") Integer id,
			Model model,
			HttpSession session) {
try {
	 Project project = this.projectRepository.findById(id).get();
	 this.projectRepository.deleteById(id);
	System.out.println("DELETED");
	session.setAttribute("message", new Message("Removed succesfully...", "success"));
	return "redirect:/admin/add_project";
} catch (Exception e) {
	e.printStackTrace();
	session.setAttribute("message", new Message("Failled to remove...", "danger"));
	return "redirect:/admin/add_project";
}
	}


	
	
//**************update users*******
	@GetMapping("/updateUser/{id}")
	public String updateUsers(@PathVariable("id") Integer id, Model model) {
		User userDetail = this.userRepository.findById(id).get();
		List<Dept> dprtmn = this.departmentRepository.findAll();
		model.addAttribute("dprtmn", dprtmn);
		model.addAttribute("userDetail", userDetail);
		return "admin/update_user";
	}
	//*********** try to implement it but not update department
//	@PostMapping("/processUserUpdate")
//	public String processUserUpdate(@RequestParam("nname") String nname, 
//			@RequestParam("id") Integer id,
//			@RequestParam("department") Integer department,
//			HttpSession session) {
//		try {
//			System.out.println(nname);
//			System.out.println(id);
//			System.out.println(department);
//			User users = this.userRepository.findById(id).get();
//			users.setName(nname);	
//			users.setDepId(department);
//			this.userRepository.save(users);
//    	Optional<Dept> findById2 = departmentRepository.findById(udepartment);
//			System.out.println(findById2.get());
//		users.setDepartment(findById2.get());
//			User saveuser =this.userRepository.save(users);
//		    System.out.println(saveuser);
//		    session.setAttribute("message", new Message("Successfully Updated!", "success"));
//			return "redirect:/admin/users";
//		} catch (Exception e) {
//			e.printStackTrace();
//			System.out.println(e.getMessage());
//			session.setAttribute("message", new Message("Something went wrong!" + e.getMessage(), "d"));
//			return "redirect:/admin/users";
//		}
//	}

	@PostMapping("/processUserUpdate")
	public String updateUserForm(@ModelAttribute("users") User users, Model model, HttpSession session) {
		try {
			System.out.println(users);
			this.userRepository.save(users);
			session.setAttribute("message", new Message("Successfully Updated!", "success"));
			return "redirect:/admin/users/0";
		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("message", new Message("Something went wrong!" + e.getMessage(), "warning"));
			return "redirect:/admin/users/0";
		}
	}

//**********password reset***********
	@GetMapping("/resetPassword/{id}")
	public String forgotPassword(@PathVariable("id") Integer id, Model model) {
		User usrDts = this.userRepository.findById(id).get();
		model.addAttribute("usrDts", usrDts);
		return "admin/resetPassword";
	}

	@PostMapping("/changePassword")
	public String changePasswordHandler(@RequestParam("newpassword") String newpassword,
			@RequestParam("userId") String userId, @ModelAttribute User user,
			HttpSession session) {
		try {
			System.out.println(newpassword);
			System.out.println(userId);
			Integer id = Integer.parseInt(userId);
			String encode = passwordEncoder.encode(newpassword);
			User findById = userRepository.findById(id).get();
			findById.setPassword(encode);
			User save = userRepository.save(user);
			session.setAttribute("message", new Message("Successfull Updated Password!", "success"));
			return "redirect:/admin/index";
		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("message", new Message("Something went wrong!" + e.getMessage(), "warning"));
			return "admin/resetPassword";
		}

	}
}
