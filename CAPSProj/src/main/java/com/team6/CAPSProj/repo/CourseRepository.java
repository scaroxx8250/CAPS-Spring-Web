package com.team6.CAPSProj.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import com.team6.CAPSProj.model.Course;

public interface CourseRepository extends JpaRepository<Course, Integer> {
	
	Course findByCourseId(int courseId);

	Course findByCourseName(String courseName); 
	
	@Query("Select c from Course c where c.courseName = :courseName and YEAR(c.courseStartDate) = :year")
	Course findCourseByName(@Param("courseName") String courseName, @Param("year") int year );
	
	@Query("select c from Course c where c.id = ?1")
	Course findCourseByid(int CourseId);
	
	@Query("Select c from Course c where YEAR(c.courseStartDate) = ?1")
	List<Course> findCoursesByYear(Integer year);

	@Query("Select c from Course c where YEAR(c.courseStartDate) = :year")
	List<Course> findCourseByCurrentYear(@Param("year") Integer year);
	
	@Query("Select c from Course c where c.lecturer.lecturerId = :lecId")
	List<Course> findCourseByLecturer(@Param("lecId") Integer lecId);
	
	@Query("Select c from Course c where c.lecturer.lecturerId = :lecId and YEAR(c.courseStartDate) =:year ")
	List<Course> findCourseByYearAndLecturer(@Param("year") Integer year,@Param("lecId") Integer lecId);
	
}
