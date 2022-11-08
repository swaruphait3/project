package com.vintegrate.support.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vintegrate.support.entity.Issues;

public interface SolutionRepository extends JpaRepository<Issues, Integer>{

}
