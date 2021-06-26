package com.team6.CAPSProj.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.team6.CAPSProj.model.Lecturer;
import com.team6.CAPSProj.model.Student;

public interface LecturerRepository extends JpaRepository<Lecturer, Integer> {
	
	Lecturer findByLecturerId(int lecturerId);
	Lecturer findByEmailAndPassword(String email, String password);


}
