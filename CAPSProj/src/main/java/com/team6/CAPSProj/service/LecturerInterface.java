package com.team6.CAPSProj.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.team6.CAPSProj.model.Lecturer;

public interface LecturerInterface {

	public List<Lecturer> GetAllLecturers();
	public void addLecturer(Lecturer lecturer);
	public void updateLecturer(Lecturer lecturer);
	public void deleteLecturer(Lecturer lecturer);
	public Lecturer findLecturerById(int lecturerId);
	public Lecturer findByEmailAndPassword(String email, String password);
	Page<Lecturer> findAllPaginatedLecturers(int pageNo, int pageSize);

}
