package com.team6.CAPSProj.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.team6.CAPSProj.model.Lecturer;

public interface LecturerRepository extends JpaRepository<Lecturer, Integer> {
	
	Lecturer findByLecturerId(int lecturerId);
}
