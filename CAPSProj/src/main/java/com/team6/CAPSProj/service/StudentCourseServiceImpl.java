package com.team6.CAPSProj.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team6.CAPSProj.model.Course;
import com.team6.CAPSProj.model.Student;
import com.team6.CAPSProj.model.StudentCourse;
import com.team6.CAPSProj.repo.CourseRepository;
import com.team6.CAPSProj.repo.StudentCourseRepository;
import com.team6.CAPSProj.repo.StudentRepository;

@Service
public class StudentCourseServiceImpl implements StudentCourseInterface {
	
	@Autowired
	StudentCourseRepository screpo; 
	
	@Autowired 
	CourseRepository crepo; 
	
	@Autowired
	StudentRepository srepo; 
	
	public List<StudentCourse> findAllCoursesByStudent(Integer studentId) {
		Student student = srepo.findByStudentId(studentId);
		return screpo.findAllCoursesByStudent(student);
	}

	
	public List<StudentCourse> findAllStudentsByCourse(String CourseName) {
		Course course = crepo.findByCourseName(CourseName);
		return screpo.findAllStudentsByCourse(course);
	}

	
	public List<StudentCourse> findAllGradeByYearAndStudent(List<Course> course, Student student) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void addStudentToCourse(Course course, Student student) {
		// TODO Auto-generated method stub

	}

	
	public void removeStudentFromCourse(Course course, Student student) {
		// TODO Auto-generated method stub

	}

	
	public void updateStudentGrade(List<StudentCourse> studentCourse) {
		// TODO Auto-generated method stub

	}

	
	public int CountTotalStudentEnrol(Integer courseId) {
		Course course = crepo.findByCourseId(courseId);
		return screpo.countStudents(course);
	}

}
