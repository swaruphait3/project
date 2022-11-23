package com.vintegrate.support.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.vintegrate.support.entity.Issues;
import com.vintegrate.support.entity.User;
import com.vintegrate.support.repository.DepartmentRepository;
import com.vintegrate.support.repository.SolutionRepository;
import com.vintegrate.support.repository.UserRepository;

@RestController
public class SearchController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private SolutionRepository solutionRepository;
	@Autowired
	private DepartmentRepository departmentRepository;
	//search handler
	@GetMapping("/search/{query}")
 public ResponseEntity<?> seachControl(@PathVariable("query") String query){
  System.out.println(query);
   List<User> users= this.userRepository.findByNameContaining(query);
   return ResponseEntity.ok(users);
 }
//search solution
	@GetMapping("/solutionSearch/{query}")
	public ResponseEntity<?> solutionSearch(@PathVariable("query") String query){
	List<Issues> issues= this.solutionRepository.findByIssueContaining(query);
return ResponseEntity.ok(issues);
	}
}
