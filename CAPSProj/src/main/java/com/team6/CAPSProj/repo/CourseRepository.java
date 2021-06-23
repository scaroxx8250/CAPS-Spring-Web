package com.team6.CAPSProj.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.team6.CAPSProj.model.Course;

public interface CourseRepository extends JpaRepository<Course, Integer> {

	@Query("Select c from Course c where YEAR(c.courseStartDate) == :year")
	List<Course> findCourseByCurrentYear(@Param("year") String year);
	
	@Query("Select c from Course where c.lecturer.lecturerId = :lecId")
	List<Course> findCourseByLecturer(@Param("lecId") Integer lecId);
	
	@Query("Select c from Course where c.lecturer.lecturerId = :lecId and YEAR(c.courseStartDate) == :year ")
	List<Course> findCourseByYearAndLecturer(@Param("year") String year,@Param("lecId") Integer lecId);
	
}
