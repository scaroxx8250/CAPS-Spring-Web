package com.team6.CAPSProj.service;

import java.util.List;

import com.team6.CAPSProj.model.Course;

public interface CourseInterface {
	
	public List<Course>findAllCourses(List<Integer> CourseId);
	public List<Course>findAllCourseforCurrentYear();
	public Course findCourseByCourseName(String courseName);
	public void addCourse(Course course);
	public void updateCourse(Course course);
	public void deleteCourse(Course course);
	public List<Course>findCoursesByLecturerId(int lecturerId);
	public List<Course>findAllCourseByYear(int year);
	public List<Course>findAllCourseByYearAndLecturerId(int year, int lecturerId);
//	public List<Course>getAllCourses();
}
