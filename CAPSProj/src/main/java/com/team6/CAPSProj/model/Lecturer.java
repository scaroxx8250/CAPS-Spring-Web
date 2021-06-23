package com.team6.CAPSProj.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Lecturer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int lecturerId;
	private String firstName;
	private String lastName;
	private Faculty faculty;
	private String personalEmail;
	private String email;
	private String password;
	@OneToMany(mappedBy = "lecturer")
	private List<Course> courses;
	
	
	public Lecturer(String firstName, String lastName, Faculty faculty, String personalEmail) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.faculty = faculty;
		this.personalEmail = personalEmail;
	}

	public Lecturer(int lecturerId, List<Course> courses, String firstName, String lastName, Faculty faculty,
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

	public Lecturer(String firstName, String lastName, Faculty faculty, String personalEmail, String email,
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
	}
	
	//Getter and Setters
	public int getLecturerId() {
		return lecturerId;
	}

	public void setLecturerId(int lecturerId) {
		this.lecturerId = lecturerId;
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

	public Faculty getFaculty() {
		return faculty;
	}

	public void setFaculty(Faculty faculty) {
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

	@Override
	public String toString() {
		return "Lecturer [lecturerId=" + lecturerId + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", faculty=" + faculty + ", personalEmail=" + personalEmail + ", email=" + email + ", password="
				+ password + ", courses=" + courses + "]";
	}
	
	
}
