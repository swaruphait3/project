package com.vintegrate.support.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vintegrate.support.entity.Project;
import com.vintegrate.support.entity.User;
@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer>{

	@Query(value =  "select * from Project", nativeQuery=true)
	public Page<Project> findPageByProject(Pageable pePageable);

}
