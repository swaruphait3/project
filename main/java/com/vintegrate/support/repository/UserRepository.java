package com.vintegrate.support.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import com.vintegrate.support.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	
	@Query("select u from User u where u.username = :username")
	public User getUserByUserName(@Param("username") String username);
	
	@Query(value =  "select * from User", nativeQuery=true)
	public Page<User> findPageByUser(Pageable pePageable);
	
	//search
	public List<User> findByNameContaining(String keywords);
//	@Query("SELECT p FROM User p WHERE CONCAT(p.name, ' ', p.department) LIKE %?1%")
//	 public List<User> search(String keyword);

}
