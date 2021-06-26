package com.team6.CAPSProj.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@IdClass(StudentCourseId.class)
public class StudentCourse implements Serializable {

@Id
@ManyToOne
@JoinColumn(name="student_id", referencedColumnName="studentId")
private Student student;

@Id
@ManyToOne
@JoinColumn(name="course_id", referencedColumnName="courseId")
private Course course;

//additional fields
@Column(nullable = true)
private Double grade;

public Student getStudent() {
	return student;
}
public void setStudent(Student student) {
	this.student = student;
}
public Course getCourse() {
	return course;
}
public void setCourse(Course course) {
	this.course = course;
}
public Double getGrade() {
	return grade;
}
public void setGrade(Double grade) {
	this.grade = grade;
}

public StudentCourse(Student student, Course course, Double grade) {
	super();
	this.student = student;
	this.course = course;
	this.grade = grade;
}


public StudentCourse(Course course, Student student) {
	super();
	this.student = student;
	this.course = course;
}

public StudentCourse() {
	super();
}
@Override
public String toString() {
	return "StudentCourse [student=" + student + ", course=" + course + ", grade=" + grade + "]";
}
@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((course == null) ? 0 : course.hashCode());
	result = prime * result + ((grade == null) ? 0 : grade.hashCode());
	result = prime * result + ((student == null) ? 0 : student.hashCode());
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
	StudentCourse other = (StudentCourse) obj;
	if (course == null) {
		if (other.course != null)
			return false;
	} else if (!course.equals(other.course))
		return false;
	if (grade != other.grade)
		return false;
	if (student == null) {
		if (other.student != null)
			return false;
	} else if (!student.equals(other.student))
		return false;
	return true;
}

}
