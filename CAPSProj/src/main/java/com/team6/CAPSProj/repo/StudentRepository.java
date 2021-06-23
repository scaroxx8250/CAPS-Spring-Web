package com.team6.CAPSProj.repo;

import org.springframework.data.jpa.repository.JpaRepository;


import com.team6.CAPSProj.model.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {
	
		Student findByStudentId (int studentId); 
}
