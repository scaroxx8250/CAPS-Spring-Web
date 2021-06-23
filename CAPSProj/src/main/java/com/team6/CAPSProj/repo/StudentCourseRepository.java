package com.team6.CAPSProj.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.team6.CAPSProj.model.Course;
import com.team6.CAPSProj.model.Student;
import com.team6.CAPSProj.model.StudentCourse;

public interface StudentCourseRepository extends JpaRepository<StudentCourse, Integer> {
	
	@Query("SELECT COUNT (sc) FROM StudentCourse sc WHERE sc.course = :course")
		int countStudents (@Param("course") Course course); 
	
	@Query("SELECT sc.course FROM StudentCourse sc WHERE sc.student = :student")
		List<StudentCourse> findAllCoursesByStudent(@Param("student") Student student); 
	
	@Query("SELECT sc.student FROM StudentCourse sc WHERE sc.course = :course")
		List<StudentCourse> findAllStudentsByCourse(@Param("course") Course course); 
	
	@Query("SELECT sc FROM StudentCourse sc WHERE sc.course = :course AND sc.student = :student")
		List<StudentCourse> findByCourseIdAndStudentId(@Param("course") Course course, @Param("student") Student student);
	
	@Query("SELECT sc FROM StudentCourse sc WHERE sc.course = :course AND sc.student = :student")
		StudentCourse findStudentCourseByCourseAndStudent(@Param("course") Course course, @Param("student") Student student);
}
