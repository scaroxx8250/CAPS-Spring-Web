package com.team6.CAPSProj.model;

import java.time.LocalDate;
import java.util.HashSet;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Student {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int studentId;
	
	private HashSet<StudentCourse> studentCourses= new HashSet<StudentCourse>();

	private String firstName;

	private String lastName;

	private String personEmail;

	private String email;

	private String password;

	@DateTimeFormat(pattern="dd/MM/yyyy")
	private LocalDate matrDate;
	
	
	//Empty Constructor
	public Student() {
		super();
	}
	


	public Student(String firstName, String lastName, String personEmail, String email, String password,
			LocalDate matrDate) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.personEmail = personEmail;
		this.email = email;
		this.password = password;
		this.matrDate = matrDate;
	}
	
	
	//Getters and Setters
	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	
	public HashSet<StudentCourse> getStudentCourse() {
		return studentCourses;
	}

	@OneToMany(mappedBy = "student", cascade=CascadeType.PERSIST)
	public void setStudentCourses(HashSet<StudentCourse> studentCourse) {
		this.studentCourses = studentCourse;
	}
	
	public void addStudentCourse(StudentCourse studentCourse) {
		this.studentCourses.add(studentCourse);
	}

	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getPersonEmail() {
		return personEmail;
	}


	public void setPersonEmail(String personEmail) {
		this.personEmail = personEmail;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public LocalDate getMatrDate() {
		return matrDate;
	}


	public void setMatrDate(LocalDate matrDate) {
		this.matrDate = matrDate;
	}



	@Override
	public String toString() {
		return "Student [studentId=" + studentId + ", studentCourse=" + studentCourses + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", personEmail=" + personEmail + ", email=" + email + ", password="
				+ password + ", matrDate=" + matrDate + "]";
	}

	
	
}
