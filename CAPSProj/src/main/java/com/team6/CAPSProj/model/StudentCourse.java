package com.team6.CAPSProj.model;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class StudentCourse {
private int id;
private Student student;
private Course course;

//additional fields
private Grade grade;
private LocalDate EnrolDate;

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name= "StudentCourseID")
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
@ManyToOne(cascade=CascadeType.ALL)
@JoinColumn(name="studentId")
public Student getStudent() {
	return student;
}
public void setStudent(Student student) {
	this.student = student;
}
@ManyToOne(cascade=CascadeType.ALL)
@JoinColumn(name="courseId")
public Course getCourse() {
	return course;
}
public void setCourse(Course course) {
	this.course = course;
}
public Grade getGrade() {
	return grade;
}
public void setGrade(Grade grade) {
	this.grade = grade;
}
public LocalDate getEnrolDate() {
	return EnrolDate;
}
public void setEnrolDate(LocalDate enrolDate) {
	EnrolDate = enrolDate;
}
@Override
public String toString() {
	return "StudentCourse [id=" + id + ", student=" + student + ", course=" + course + ", grade=" + grade
			+ ", EnrolDate=" + EnrolDate + "]";
}


}
