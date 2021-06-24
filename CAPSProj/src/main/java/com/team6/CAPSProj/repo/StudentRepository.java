package com.team6.CAPSProj.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.team6.CAPSProj.model.Student;
import com.team6.CAPSProj.model.StudentCourse;

public interface StudentRepository extends JpaRepository<Student, Integer> {
	
	Student findByStudentId (int studentId); 

	Student findByMatricNo(String matricNo);

	Student findByEmail(String email);

	List<Student> findByFirstName(String input);

}
