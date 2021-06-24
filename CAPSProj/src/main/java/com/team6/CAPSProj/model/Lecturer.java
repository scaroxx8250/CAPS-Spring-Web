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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((faculty == null) ? 0 : faculty.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + lecturerId;
		result = prime * result + ((personalEmail == null) ? 0 : personalEmail.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Lecturer other = (Lecturer) obj;
		if (faculty != other.faculty)
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (lecturerId != other.lecturerId)
			return false;
		if (personalEmail == null) {
			if (other.personalEmail != null)
				return false;
		} else if (!personalEmail.equals(other.personalEmail))
			return false;
		return true;
	}
	
	
}
