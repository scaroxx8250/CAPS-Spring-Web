
package com.team6.CAPSProj.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.team6.CAPSProj.model.Course;
import com.team6.CAPSProj.model.Faculty;
import com.team6.CAPSProj.model.StudentCourse;

public interface CourseService {
	
	public List<Course>findAllCourses(List<Integer> CourseId);
	public List<Course>findAllCourseforCurrentYear();
	public Course findCourseByCourseName(String courseName);
	public void addCourse(Course course);
	public void updateCourse(Course course);
	public void deleteCourse(Course course);
	public List<Course>findCoursesByLecturerId(int lecturerId);
	public List<Course>findAllCourseByYear(int year);
	public List<Course>findAllCourseByYearAndLecturerId(int year, int lecturerId);
	public List<Course>getAllCourses();
	Page<Course> findAllPaginatedNotEnrolledCoursesByStudent(int pageNo, int pageSize, Integer studentId);
	Page<Course> findAllPaginatedNotEnrolledCoursesByStudentAndFaculty(int pageNo, int pageSize, Integer studentId,int faculty);
	Page<Course> findAllPaginatedCourseByYearAndLecturer(int pageNo, int pageSize, int lecturerId);
	public Course findCourseByCourseId(int courseId);
	Page<Course> findAllPaginatedCourseForCurrentYear(int pageNo, int pageSize);
	
}

