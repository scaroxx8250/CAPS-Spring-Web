package com.team6.CAPSProj.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team6.CAPSProj.model.Student;
import com.team6.CAPSProj.repo.StudentRepository;

@Service
public class StudentImplementation implements StudentInterface {
	
	@Autowired
	StudentRepository srepo;
	
	
	public List<Student>findAllStudents() {
		
		return srepo.findAll();
	}
	

	public  List<Student>findAllStudentsById(List<Integer>Id) {
		
		List<Student> studentlist = new ArrayList<Student>();
		for (Integer sid: Id) {
			if(srepo.findById(sid).get() != null)
				studentlist.add(srepo.findById(sid).get());
		}
		return studentlist;
	}
	
	
	public Student findStudentByMatricNo(String matricNo) {
		return srepo.findByMatricNo(matricNo);
	}
	
	public Student findStudentByEmail(String email) {
		return srepo.findByEmail(email);
	}
	

	public List<Student> findStudentByMatric_FirstName(String input) {
		//Substring the number part of the entered String
		String s = input.substring(1,-1);
		List<Student> studentlist = new ArrayList<Student>();
		try {
			if(Integer.parseInt(s) != 0 ) {
				Student student = srepo.findByMatricNo(input);
				studentlist.add(student);
		}
		//If substring is not a number Integer.parseInt will throw number format exception
		//We will then find students by first name
		} catch (NumberFormatException e) {
	      studentlist = srepo.findByFirstName(input);
		}
		return studentlist;
	}
	
	
	public void addStudent(Student student) {
			srepo.save(student);
	}
	
	
	public void updateStudent(Student student) {
		//student object contains original studentId (which will change after update)
		//and it also contains new student particulars (to be updated)
		
		if(srepo.findById(student.getStudentId()) != null) {
			Student student1 = srepo.findById(student.getStudentId()).get();
			student1.setFirstName(student.getFirstName());
			student1.setLastName(student.getLastName());
			student1.setPersonEmail(student.getPersonEmail());
			student1.setMatrDate(student.getMatrDate());
			srepo.save(student1);
		}
		
	}
	public void deleteStudent(Student student) {
			srepo.delete(student);
	}
	
	
}
