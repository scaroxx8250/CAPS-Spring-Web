package com.team6.CAPSProj.model;

import java.time.LocalDate;
import java.util.HashSet;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Course {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int courseId;

	private String courseName;

	private String description;

	private Faculty faculty;

	private int credits;
	@DateTimeFormat(pattern="dd/MM/yyyy")
	private LocalDate courseStartDate;
	
	@ManyToOne
	private Lecturer lecturer;

	private int size;
	
	private HashSet<StudentCourse> studentCourses = new HashSet<StudentCourse>();

	
	public Course() {
		super();
	}
	

	public Course(String courseName, String description, Faculty faculty, int credits, LocalDate courseStartDate,
			int size) {
		super();
		this.courseName = courseName;
		this.description = description;
		this.faculty = faculty;
		this.credits = credits;
		this.courseStartDate = courseStartDate;
		this.size = size;
	}




	public int getCourseId() {
		return courseId;
	}
	
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	
	public String getCourseName() {
		return courseName;
	}
	
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Faculty getFaculty() {
		return faculty;
	}
	
	public void setFaculty(Faculty faculty) {
		this.faculty = faculty;
	}
	
	
	
	public int getCredits() {
		return credits;
	}


	public void setCredits(int credits) {
		this.credits = credits;
	}


	public LocalDate getCourseStartDate() {
		return courseStartDate;
	}


	public void setCourseStartDate(LocalDate courseStartDate) {
		this.courseStartDate = courseStartDate;
	}


	public Lecturer getLecturer() {
		return lecturer;
	}
	
	public void setLecturer(Lecturer lecturer) {
		this.lecturer = lecturer;
	}
	
	public int getSize() {
		return size;
	}
	
	public void setSize(int size) {
		this.size = size;
	}
	
	public HashSet<StudentCourse> getStudentCourses() {
		return studentCourses;
	}
	
	@OneToMany(mappedBy = "course")
	public void setStudentCourses(HashSet<StudentCourse> studentCourse) {
		this.studentCourses = studentCourse;
	}
//	public void addStudentCourse(StudentCourse studentCourse) {
//		this.studentCourses.add(studentCourse);
//	}
	
	@Override
	public String toString() {
		return "Course [courseId=" + courseId + ", courseName=" + courseName + ", description=" + description
				+ ", faculty=" + faculty + ", credits=" + credits + ", courseStartDate=" + courseStartDate + ", size="
				+ size + "]";
	}

	


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + courseId;
		result = prime * result + ((courseName == null) ? 0 : courseName.hashCode());
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
		Course other = (Course) obj;
		if (courseId != other.courseId)
			return false;
		if (courseName == null) {
			if (other.courseName != null)
				return false;
		} else if (!courseName.equals(other.courseName))
			return false;
		return true;
	}
	
}
