package com.team6.CAPSProj.model;

import java.io.Serializable;

public class StudentCourseId implements Serializable {

	private int student;
	private int course;
	public int getStudent() {
		return student;
	}
	public void setStudent(int student) {
		this.student = student;
	}
	public int getCourse() {
		return course;
	}
	public void setCourse(int course) {
		this.course = course;
	}
	
}
