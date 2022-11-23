package com.vintegrate.support.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vintegrate.support.entity.Issues;

@Repository
public interface SolutionRepository extends JpaRepository<Issues, Integer> {

	@Query(value = "select * from trn_issues where pro_id= ?1", nativeQuery = true)
	public List<Issues> IssuesList(Integer id);

	@Query("from Issues as c where c.user.id =:id")
	public List<Issues> findIssuesByUser(@Param("id") int id);
//	@Query(value = "SELECT c_id, details,  images, solution ,issue, p.project_name,u.name"
//			+ "FROM solution.trn_issues a " + "inner join projects p on (a.pro_id = p.department_id)"
//			+ "inner join solution.user u on (u.id = a.user_id) " + "where a.issue like = ?'query'", nativeQuery = true)
	//@Query(value = "SELECT * FROM solution.trn_issues where details LIKE '%'+?1+'%' OR issue LIKE '%'+?1+'%' OR technology LIKE '%'+?1+'%' ",nativeQuery = true)
	@Query("Select m FROM Issues m WHERE m.details LIKE %:keywords% OR m.issue LIKE %:keywords% OR m.technology LIKE %:keywords%")
	public List<Issues> findByIssueContaining(@Param("keywords") String keywords);
}
