package com.team6.CAPSProj.model;

import java.io.Serializable;

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
private Grade grade;

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

public StudentCourse(Student student, Course course, Grade grade) {
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


}
