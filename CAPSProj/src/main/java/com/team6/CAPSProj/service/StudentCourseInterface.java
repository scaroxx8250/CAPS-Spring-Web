package com.team6.CAPSProj.service;

import java.util.List;

import com.team6.CAPSProj.model.Course;
import com.team6.CAPSProj.model.Student;
import com.team6.CAPSProj.model.StudentCourse;

public interface StudentCourseInterface {
	
	public List<StudentCourse>findAllStudentsByCourse(String CourseName);
	public void addStudentToCourse(Course course, Student student);
	public void removeStudentFromCourse(Course course, Student student);
	public int CountTotalStudentEnrol(int courseId );

}
