package com.team6.CAPSProj.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.team6.CAPSProj.model.Student;

public interface StudentRepository extends JpaRepository<Student, Integer>, PagingAndSortingRepository<Student, Integer> {
	
	Student findByStudentId (int studentId); 

	Student findByMatricNo(String matricNo);

	Student findByEmail(String email);

	List<Student> findByFirstName(String input);
	
	Student findByEmailAndPassword(String email, String password);
	
	@Query(value = "SELECT * from student where student.student_Id NOT IN (Select student_id from student_course WHERE student_course.course_Id = :courseId)", nativeQuery = true)
	Page<Student> findAllNotEnrolledStudentsByCourseByPage(@Param("courseId") int courseId, Pageable pageable);
	
	@Query(value = "SELECT * FROM student WHERE student_Id IN (SELECT student_id FROM student_course WHERE course_id = :courseId)", nativeQuery = true)
	Page<Student> findAllStudentsByCourseByPage(@Param("courseId") int courseId, Pageable pageable);
}
