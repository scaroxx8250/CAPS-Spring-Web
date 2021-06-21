package com.team6.CAPSProj.model;

import javax.persistence.*;

@Entity
public class Lecturer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int lecturerId;
	private String firstName;
	private String lastName;
	private String faculty;
	private String personalEmail;
	private String email;
	private String password;
	@OneToMany(mappedBy = "Lecturer")
	private List<Courses> courses;
	
	
	public Lecturer(int lecturerId, List<Courses> courses, String firstName, String lastName, String faculty,
			String personalEmail, String email, String password) {
		super();
		this.lecturerId = lecturerId;
		this.courses = courses;
		this.firstName = firstName;
		this.lastName = lastName;
		this.faculty = faculty;
		this.personalEmail = personalEmail;
		this.email = email;
		this.password = password;
	}

	public Lecturer(String firstName, String lastName, String faculty, String personalEmail, String email,
			String password) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.faculty = faculty;
		this.personalEmail = personalEmail;
		this.email = email;
		this.password = password;
	}
	
	public Lecturer() {
		super();
		//TODO Auto-generated constructor stub
	}
	
	//Getter and Setters
	public int getLecturerId() {
		return lecturerId;
	}

	public void setLecturerId(int lecturerId) {
		this.lecturerId = lecturerId;
	}

	public List<Courses> getCourses() {
		return courses;
	}

	public void setCourses(List<Courses> courses) {
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

	public String getFaculty() {
		return faculty;
	}

	public void setFaculty(String faculty) {
		this.faculty = faculty;
	}

	public String getPersonalEmail() {
		return personalEmail;
	}

	public void setPersonalEmail(String personalEmail) {
		this.personalEmail = personalEmail;
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
	
}
