package com.team6.CAPSProj.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.team6.CAPSProj.model.Course;
import com.team6.CAPSProj.model.Student;
import com.team6.CAPSProj.model.StudentCourse;

public interface StudentCourseRepository extends JpaRepository<StudentCourse, Integer> {
	
	@Query("SELECT COUNT (sc) FROM StudentCourse sc WHERE sc.course = :course")
		int countStudents (@Param("course") Course course); 
	
	@Query("SELECT sc FROM StudentCourse sc WHERE sc.student = :student")
		List<StudentCourse> findAllCoursesByStudent(@Param("student") Student student); 
	
	@Query("SELECT sc FROM StudentCourse sc WHERE sc.course = :course")
		List<StudentCourse> findAllStudentsByCourse(@Param("course") Course course); 
	
	@Query("SELECT sc FROM StudentCourse sc join sc.course c WHERE sc.course = :course AND sc.student = :student and YEAR(c.courseStartDate) =:year")
		List<StudentCourse> findByCourseIdAndStudentIdAndYear(@Param("course") Course course, @Param("student") Student student, @Param("year") int year) ;
	
	@Query("SELECT sc FROM StudentCourse sc join sc.course c WHERE sc.course = :course AND sc.student = :student")
	List<StudentCourse> findByCourseIdAndStudentId(@Param("course") Course course, @Param("student") Student student) ;
	
	@Query("SELECT sc FROM StudentCourse sc WHERE sc.course = :course AND sc.student = :student")
		StudentCourse findStudentCourseByCourseAndStudent(@Param("course") Course course, @Param("student") Student student);

	@Query("SELECT sc FROM StudentCourse sc join sc.course c WHERE sc.student = :student and sc.grade is null and YEAR(c.courseStartDate) =:year")
	Page<StudentCourse> findAllCoursesByStudentByPage(@Param("student") Student student, @Param("year") int year, Pageable pageable);
}
