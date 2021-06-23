package com.team6.CAPSProj.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team6.CAPSProj.model.Lecturer;
import com.team6.CAPSProj.repo.LecturerRepository;

@Service
public class LecturerServiceImpl implements LecturerInterface {
	
	@Autowired
	LecturerRepository lrepo; 
	
	 
	public List<Lecturer> GetAllLecturers() {
		return lrepo.findAll(); 
	}
	
	public void addLecturer(Lecturer lecturer) {
		lrepo.save(lecturer);
	}

	
	public void updateLecturer(Lecturer lecturer) {
		if(lrepo.findById(lecturer.getLecturerId()) != null) {
			Lecturer lecturer1 = lrepo.findById(lecturer.getLecturerId()).get();
			lecturer1.setFirstName(lecturer.getFirstName());
			lecturer1.setLastName(lecturer.getLastName());
			lecturer1.setFaculty(lecturer.getFaculty());
			lecturer1.setPersonalEmail(lecturer.getPersonalEmail());
			lrepo.save(lecturer1);
		}

	}

	
	public void deleteLecturer(Lecturer lecturer) {
		lrepo.delete(lecturer);

	}

}
