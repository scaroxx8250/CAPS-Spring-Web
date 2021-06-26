package com.team6.CAPSProj.service;

import java.util.List;

import com.team6.CAPSProj.model.Course;
import com.team6.CAPSProj.model.Student;
import com.team6.CAPSProj.model.StudentCourse;

public interface StudentCourseInterface {
	
	public List<StudentCourse>findAllCoursesByStudent(Integer studentId);
	public List<StudentCourse>findAllStudentsByCourse(String CourseName);
	public List<StudentCourse>findAllGradeByYearAndStudent(List<Course> course, Student student, int year);
	public List<StudentCourse>findAllGradeByStudent(List<Course> course, Student student);
	public double findGradeByStudentAndCourse(Student student, Course course);
	public void addStudentToCourse(Course course, Student student);
	public void removeStudentFromCourse(Course course, Student student);
	public void updateStudentGrade(List<StudentCourse> studentCourse);
	public int CountTotalStudentEnrol(Integer courseId );

}
