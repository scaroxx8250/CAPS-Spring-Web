package com.team6.CAPSProj.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.team6.CAPSProj.model.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {
	
	public Student findByMatricNo(String matricNo);

	public Student findByEmail(String email);

	public List<Student> findByFirstName(String input);

}
