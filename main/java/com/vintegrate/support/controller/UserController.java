package com.vintegrate.support.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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
@RequestMapping("/user")
public class UserController {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private DepartmentRepository departmentRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private SolutionRepository solutionRepository;

	// method for adding common data to response
	@ModelAttribute
	public void addCommonData(Model model, Principal principal) {
		String userName = principal.getName();
		User user = userRepository.getUserByUserName(userName);
		model.addAttribute("user", user);
	}

	// dashboard home
	@RequestMapping("/index")
	public String dashboard(Model model, Principal principal) {
		model.addAttribute("title", "User Dashboard");
		return "normal/user_dashboard";
	}

	// open add form handler
	@GetMapping("/add-issue")
	public String openAddContactForm(Model model) {
		try {
			model.addAttribute("title", "Issue Entry");
			model.addAttribute("issues", new Issues());
			String name = SecurityContextHolder.getContext().getAuthentication().getName();
			System.out.println(name);
			User userByUserName = userRepository.getUserByUserName(name);
			System.out.println(userByUserName);
			System.out.println(userByUserName.getDepId());
			Optional<Dept> findById = departmentRepository.findById(userByUserName.getDepartment().getId());
			if (findById.isPresent()) {
				System.out.println(findById.get().getProject());
				List<Project> project = findById.get().getProject();
				model.addAttribute("project", project);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		return "normal/add_issues";
	}

	// processing add contact form
	@PostMapping("/process-issues")
	public String processContact(@ModelAttribute Issues issues, @RequestParam("error_img") MultipartFile file,
			Principal principal, HttpSession session) {
		System.out.println(issues.getProjectId());
		try {
			if (file.isEmpty()) {
				System.out.println("File is empty");
				issues.setImages("default.png");

			} else {
				// file the file to folder and update the name to contact

				issues.setImages(file.getOriginalFilename());
				File saveFile = new ClassPathResource("static/images").getFile();
				Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
				System.out.println("Image is uploaded");

			}

			String name = principal.getName();
			User user = this.userRepository.getUserByUserName(name);
			Project findById = projectRepository.findById(issues.getProjectId()).get();
			issues.setPro(findById);
			// processing and uploading file..
			// user.getIssues().add(issues);
			issues.setUser(user);
			this.solutionRepository.save(issues);
//			this.userRepository.save(user);
			System.out.println("DATA " + issues);
			System.out.println("Added to data base");
			session.setAttribute("message", new Message("Your Issues is succesffully register ", "success"));

		} catch (Exception e) {
			System.out.println("ERROR " + e.getMessage());
			e.printStackTrace();
			session.setAttribute("message", new Message("Some went wrong !! Try again.." + e.getMessage(), "danger"));

		}
		return "normal/add_issues";
	}

	// show solutions
	@GetMapping("/showSolutionUser")
	public String showSolutionUser(Model model, Principal principal) {
	 String userName =	principal.getName();
	 User currentUser =  this.userRepository.getUserByUserName(userName);
	 List<Issues> issue = this.solutionRepository.findIssuesByUser(currentUser.getId());
     model.addAttribute("issue", issue);
	 
	 //	  User currentUser= this.userRepository.getUserByUserName(userName);
//	  List<Issues> iss=currentUser.getIssues();
//	  System.out.println(iss);
		return "normal/show_solutions";
	}
//solution update
@PostMapping("/updateSolution/{id}")
public String editDepartmentForm(@PathVariable("id") Integer id, Model model, 
		HttpSession session) {
	try {
	Issues issue = this.solutionRepository.findById(id).get();
	model.addAttribute("issueById", issue);
	} catch (Exception e) {
		e.printStackTrace();
	}
System.out.println(id);
	return "normal/edit_solutionss";
}

@PostMapping("/updateSolution")
public String updateSolution(@RequestParam("id") Integer id,
		@RequestParam("nsolution") String nsolution) {
	System.out.println(id);
	System.out.println(nsolution);
	try {
		Issues issu=this.solutionRepository.findById(id).get();
		issu.setSolution(nsolution);
		this.solutionRepository.save(issu);
		return "redirect:/user/showSolutionUser";
	} catch (Exception e) {
		e.printStackTrace();
		return "normal/edit_solutionss";
	}
	
}
}


//@PostMapping("/updateSolution")
//public String updateSolutionEntry(@ModelAttribute Issues iss,
//		Model m, HttpSession session, Principal principal) {
//	// old solution details
//	Issues oldIssueDetails= this.solutionRepository.findById(iss.getCId()).get();
//	User user = this.userRepository.getUserByUserName(principal.getName());
//	iss.setUser(user);
//System.out.println(user);
//	this.solutionRepository.save(iss);
//	session.setAttribute("message", new Message("Your contact is updated...", "success"));
//	
//
//	return "normal/show_solutions";
//}
//}

//	@GetMapping("/show-solutions")
//	public String showSolutions(Model model) {
//		try {
//			String name = SecurityContextHolder.getContext().getAuthentication().getName();
//			System.out.println(name);
//			User userByUserName = userRepository.getUserByUserName(name);
//			System.out.println(userByUserName);
//			System.out.println(userByUserName.getDepId());
//			Optional<Dept> findById = departmentRepository.findById(userByUserName.getDepartment().getId());
//			if (findById.isPresent()) {				
//				int dId =findById.get().getId();
//				System.out.println(dId);
//				List<Issues> issues=this.solutionRepository.IssuesList(dId);
//		       model.addAttribute("issues", issues);
////				List<Project> project = findById.get().getProject();
////				model.addAttribute("project", project);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return "normal/show_solutions";
//	}
//	*********************admin controller**************************


