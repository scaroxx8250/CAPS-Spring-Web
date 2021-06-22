package com.team6.CAPSProj.service;

import java.util.List;

import com.team6.CAPSProj.model.Course;

public interface CourseInterface {

	public List<Course>findAllCourseforCurrentYear();
	public Course findCourseByCourseName(String courseName);
	public void addCourse(Course course);
	public void updateCourse(Course course);
	public void deleteCourse(Course course);
	public List<Course>findCoursesByLecturerId(int lecturerId);
	public List<Course>findAllCourseByYear(String year);
	public List<Course>findAllCourseByYearAndLecturerId(String year, int lecturerId);
}
