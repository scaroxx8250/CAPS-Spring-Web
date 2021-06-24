package com.team6.CAPSProj.model;

import java.io.Serializable;

public class StudentCourseId implements Serializable {

	private Integer student;
	private Integer course;
	public Integer getStudent() {
		return student;
	}
	public void setStudent(Integer student) {
		this.student = student;
	}
	public Integer getCourse() {
		return course;
	}
	public void setCourse(Integer course) {
		this.course = course;
	}
	
}
