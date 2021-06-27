package com.team6.CAPSProj.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team6.CAPSProj.model.Student;
import com.team6.CAPSProj.repo.StudentRepository;

@Service
public class StudentImplementation implements StudentInterface {
	//test
	@Autowired
	StudentRepository srepo;
	
	public Student findByEmailAndPassword(String email, String password) {
		return srepo.findByEmailAndPassword(email, password);
	}
	
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
	
	public Student findStudentByStudentId(Integer id) {
		return srepo.findByStudentId(id);
	}
	
	public List<Student> findStudentByMatric_FirstName(String input) {
		//Substring the number part of the entered String
		String s = input.substring(1,4);
		List<Student> studentlist = new ArrayList<Student>();
		try {
			int num = Integer.parseInt(s);
			if(num != 0 ) {
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
	
	public String findFirstNameByStudentId(Integer id)
	{
		Student student = srepo.findByStudentId(id);
		String firstName = student.getFirstName();
		return firstName; 
	}
	public String findLastNameByStudentId(Integer id)
	{
		Student student = srepo.findByStudentId(id);
		String lastName = student.getLastName(); 
		return lastName; 
	}
	
	public void addStudent(Student student) {
			srepo.save(student);
				
			///generate matriculation number
			if(student.getMatricNo() == null) 
			{
				int studentID = student.getStudentId();
				//Generate four centre decimal places, e.g. 1234 for MatrNo A1234B
				String centreDecimal = String.valueOf(9999 - studentID);
				//Concatenate to String following format AXXXXB
				String matrNo = "A" + centreDecimal + "B";
				student.setMatricNo(matrNo);
				srepo.save(student);	
			}
			
			///generate email
			if(student.getEmail() == null) {
				String uuid = UUID.randomUUID().toString().substring(0, 5);
				String email = uuid + "@gmail.com";
				student.setEmail(email);
				srepo.save(student);
			}
			///generate password
			if(student.getPassword() == null) {
				String uuid = UUID.randomUUID().toString().substring(0, 10);
				String pw = uuid;
				student.setPassword(pw);
				srepo.save(student);
			}		
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
