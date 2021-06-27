package com.team6.CAPSProj.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team6.CAPSProj.model.Lecturer;
import com.team6.CAPSProj.model.Student;
import com.team6.CAPSProj.repo.LecturerRepository;

@Service
public class LecturerServiceImpl implements LecturerInterface {
	
	@Autowired
	LecturerRepository lrepo; 
	
	public Lecturer findByEmailAndPassword(String email, String password) {
		return lrepo.findByEmailAndPassword(email, password);
	}
	 
	public List<Lecturer> GetAllLecturers() {
		return lrepo.findAll(); 
	}
	
	public void addLecturer(Lecturer lecturer) {
		lrepo.save(lecturer);
		
		///generate email
		if(lecturer.getEmail() == null) {
			String uuid = UUID.randomUUID().toString().substring(0, 5);
			String email = uuid + "@nus.edu.sg";
			lecturer.setEmail(email);
			lrepo.save(lecturer);
		}
		///generate password
		if(lecturer.getPassword() == null) {
			String uuid = UUID.randomUUID().toString().substring(0, 10);
			String pw = uuid;
			lecturer.setPassword(pw);
			lrepo.save(lecturer);
		}
	}
	
	public Lecturer findLecturerById(int lecturerId)
	{
		return lrepo.findByLecturerId(lecturerId);
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
