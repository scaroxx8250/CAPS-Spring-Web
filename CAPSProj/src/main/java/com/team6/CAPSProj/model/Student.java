package com.team6.CAPSProj.model;

import java.time.LocalDate;
import java.util.HashSet;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Student {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int studentId;
	
	private HashSet<StudentCourse> studentCourses= new HashSet<StudentCourse>();

	private String matricNo;
	
	@NotEmpty
	private String firstName;
	
	@NotEmpty
	private String lastName;

	@Email
	@NotEmpty
	private String personEmail;

	private String email;

	private String password;

	//@DateTimeFormat(pattern="dd/MM/yyyy")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull
	private LocalDate matrDate;
	
	
	//Empty Constructor
	public Student() {
		super();
	}
	


	public Student(String matricNo, String firstName, String lastName, String personEmail, String email, String password,
			LocalDate matrDate) {
		super();
		this.matricNo = matricNo;
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
	
	
	public String getMatricNo() {
		return matricNo;
	}



	public void setMatricNo(String matricNo) {
		this.matricNo = matricNo;
	}



	public HashSet<StudentCourse> getStudentCourse() {
		return studentCourses;
	}

	@OneToMany(mappedBy = "student")
	public void setStudentCourses(HashSet<StudentCourse> studentCourse) {
		this.studentCourses = studentCourse;
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((matrDate == null) ? 0 : matrDate.hashCode());
		result = prime * result + studentId;
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
		Student other = (Student) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
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
		if (matrDate == null) {
			if (other.matrDate != null)
				return false;
		} else if (!matrDate.equals(other.matrDate))
			return false;
		if (studentId != other.studentId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Student [studentCourses=" + studentCourses + ", matricNo=" + matricNo + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", personEmail=" + personEmail + ", email=" + email + ", password="
				+ password + ", matrDate=" + matrDate + "]";
	}

}
