package com.team6.CAPSProj.service;


import java.util.ArrayList;
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
		List<StudentCourse> sc = new ArrayList<StudentCourse>(); 
		for (Course c: course) {
			StudentCourse retrieved = screpo.findByCourseIdAndStudentId(c, student).get(0);
			if (retrieved != null) {
				sc.add(retrieved);
			}
		}
		return sc; 	
		
	}

	
	public void addStudentToCourse(Course course, Student student) {
		StudentCourse sc = new StudentCourse(course, student); 
		screpo.save(sc); 

	}

	
	public void removeStudentFromCourse(Course course, Student student) {
		StudentCourse toDelete = screpo.findByCourseIdAndStudentId(course, student).get(0);
		if (toDelete != null) {
			screpo.delete(toDelete);
		}

	}

	
	public void updateStudentGrade(List<StudentCourse> studentCourse) {
		for (StudentCourse sc : studentCourse) {
			if(screpo.findStudentCourseByCourseAndStudent(sc.getCourse(), sc.getStudent()) != null) {
				StudentCourse sc1 = screpo.findStudentCourseByCourseAndStudent(sc.getCourse(), sc.getStudent());
				sc1.setGrade(sc.getGrade());
				screpo.save(sc1);
			}
			
		}

	}

	
	public int CountTotalStudentEnrol(Integer courseId) {
		Course course = crepo.findByCourseId(courseId);
		return screpo.countStudents(course);
	}

}