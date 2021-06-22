package com.team6.CAPSProj.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
private Grade grade;
private LocalDate EnrolDate;
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
public StudentCourse(Student student, Course course, Grade grade, LocalDate enrolDate) {
	super();
	this.student = student;
	this.course = course;
	this.grade = grade;
	EnrolDate = enrolDate;
}
public StudentCourse() {
	super();
}

}
