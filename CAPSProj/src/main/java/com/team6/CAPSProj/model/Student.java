package com.team6.CAPSProj.model;

import java.time.LocalDate;
import java.util.List;
import javax.persistence.JoinColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Student {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int studentId;
	
	@ManyToMany
	@JoinTable(name="student_course", 
	joinColumns= @JoinColumn(name = "studentId"),
	inverseJoinColumns = @JoinColumn(name="courseId"))
	private List<Course> courses;
	
	private String firstName;
	private String lastName;
	private String personEmail;
	private String email;
	private String password;
	private LocalDate matrDate;
	
	
	//Empty Constructor
	public Student() {
		super();
	}


	//Constructor with all attributes
	public Student(int studentId, List<Course> courses, String firstName, String lastName, String personEmail,
			String email, String password, LocalDate matrDate) {
		super();
		this.studentId = studentId;
		this.courses = courses;
		this.firstName = firstName;
		this.lastName = lastName;
		this.personEmail = personEmail;
		this.email = email;
		this.password = password;
		this.matrDate = matrDate;
	}
	
	//Constructor without id and courses
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


	public List<Course> getCourses() {
		return courses;
	}


	public void setCourses(List<Course> courses) {
		this.courses = courses;
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

	
	//toString method 
	@Override
	public String toString() {
		return "Student [studentId=" + studentId + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", personEmail=" + personEmail + ", email=" + email + ", password=" + password + ", matrDate="
				+ matrDate + "]";
	}
	
}
