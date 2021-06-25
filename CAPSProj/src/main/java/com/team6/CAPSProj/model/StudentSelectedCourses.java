package com.team6.CAPSProj.model;

import java.util.List;

public class StudentSelectedCourses {
	public List<Course> course;

	public StudentSelectedCourses(List<Course> course) {
		super();
		this.course = course;
	}

	public List<Course> getCourse() {
		return course;
	}

	public void setCourse(List<Course> course) {
		this.course = course;
	}

}
