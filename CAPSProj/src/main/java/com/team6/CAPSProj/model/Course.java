package com.team6.CAPSProj.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Course {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int courseId;
	private String courseName;
	private String description;
	private String faculty;
	private int credits;
	@ManyToOne
	private Lecturer lecturer;
	@ManyToMany(mappedBy = "courses")
	private List<Student> students;
	private int size;
	
	public Course(int courseId, String courseName, String description, String faculty, int credits, Lecturer lecturer,
			int size) {
		super();
		this.courseId = courseId;
		this.courseName = courseName;
		this.description = description;
		this.faculty = faculty;
		this.credits = credits;
		this.lecturer = lecturer;
		this.size = size;
	}
	
	public Course(String courseName, String description, String faculty, int credits, int size) {
		super();
		this.courseName = courseName;
		this.description = description;
		this.faculty = faculty;
		this.credits = credits;
		this.size = size;
	}
	
	public Course() {
		super();
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
	
	public String getFaculty() {
		return faculty;
	}
	
	public void setFaculty(String faculty) {
		this.faculty = faculty;
	}
	
	public int getCredit() {
		return credits;
	}
	
	public void setCredit(int credits) {
		this.credits = credits;
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
	
	@Override
	public String toString() {
		return "Course [courseId=" + courseId + ", courseName=" + courseName + ", description=" + description
				+ ", faculty=" + faculty + ", credits=" + credits + ", lecturer=" + lecturer + ", size=" + size + "]";
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
