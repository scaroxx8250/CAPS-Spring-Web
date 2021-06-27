package com.team6.CAPSProj.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.team6.CAPSProj.model.Course;
import com.team6.CAPSProj.model.StudentCourse;

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

	Page<Course> findAllPaginatedNotEnrolledCoursesByStudent(int pageNo, int pageSize, Integer studentId);
}
