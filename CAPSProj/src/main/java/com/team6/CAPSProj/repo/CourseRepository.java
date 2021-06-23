package com.team6.CAPSProj.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.team6.CAPSProj.model.Course;

public interface CourseRepository extends JpaRepository<Course, Integer> {
	
	@Query("Select c from Course c where c.courseName = :courseName")
	 Course findCourseByName(@Param("courseName") String courseName);
	
	@Query("select c from Course c where c.id = ?1")
	Course findCourseByid(int CourseId);
	
	@Query("Select c from Course c where YEAR(c.courseStartDate) == ?1")
	List<Course> findCoursesByYear(String year);

}
