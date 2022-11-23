package com.vintegrate.support.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vintegrate.support.entity.Dept;

@Repository
public interface DepartmentRepository extends JpaRepository<Dept, Integer>{

}
