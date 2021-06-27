package com.team6.CAPSProj.service;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.team6.CAPSProj.model.Course;
import com.team6.CAPSProj.model.CourseOccupancy;
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
	
	@Autowired
	CourseInterface cservice;
	
	public List<StudentCourse> findAllCoursesByStudent(Integer studentId) {
		Student student = srepo.findByStudentId(studentId);
		return screpo.findAllCoursesByStudent(student);
	}

	
	public List<StudentCourse> findAllStudentsByCourse(String CourseName) {
		Course course = crepo.findByCourseNameWithCurrentYear(CourseName,LocalDate.now().getYear());
		return screpo.findAllStudentsByCourse(course);
	}

	
	public List<StudentCourse> findAllGradeByYearAndStudent(List<Course> course, Student student, int year) {
		List<StudentCourse> sc = new ArrayList<StudentCourse>(); 
		for (Course c: course) {
			if(c.getCourseStartDate().getYear() == year) {
				StudentCourse retrieved = screpo.findByCourseIdAndStudentIdAndYear(c, student,year).get(0);
				if (retrieved != null && retrieved.getGrade()!=null) {
					sc.add(retrieved);
				}
			}
			
		}
		return sc; 	
		
	}
	public List<StudentCourse>findAllGradeByStudent(List<Course> course, Student student){
		List<StudentCourse> sc = new ArrayList<StudentCourse>(); 
		for (Course c: course) {
			StudentCourse retrieved = screpo.findByCourseIdAndStudentId(c, student).get(0);
			if (retrieved != null && retrieved.getGrade()!=null) {
				sc.add(retrieved);
			}
		}
		return sc; 	
	}
	
	public double findGradeByStudentAndCourse(Student student, Course course) {
		StudentCourse retrieved = screpo.findByCourseIdAndStudentId(course, student).get(0);
		double grade = retrieved.getGrade();
		return grade; 
	}


	
	public void addStudentToCourse(Course course, Student student) {
		// Can only add student to a course if the course has not yet started
		// i.e. courseStartDate is later than the current date
		if (course.getCourseStartDate().isAfter(LocalDate.now()))
		{
			List<StudentCourse> studentsInCourse = screpo.findAllStudentsByCourse(course);
			int noOfStudentsInCourse = studentsInCourse.size();
			// If the students in the course is less than the course size limit, then can add the student to the course
			if(noOfStudentsInCourse < course.getSize())
			{
				StudentCourse sc = new StudentCourse(course, student); 
				screpo.save(sc); 
				// After adding the student, if course has reached max capacity
				if(noOfStudentsInCourse == course.getSize() - 1)
				{
					course.setCourseOccupancy(CourseOccupancy.FULL);
					cservice.updateCourse(course);
				}
			}	
		}
	}

	
	public void removeStudentFromCourse(Course course, Student student) {
		StudentCourse toDelete = screpo.findByCourseIdAndStudentIdAndYear(course, student,2021).get(0);
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
  
  public void removeStudentCourse(StudentCourse studentcourse) {
	screpo.delete(studentcourse);
  	}

	public Page<StudentCourse> findAllPaginatedCoursesByStudent(int pageNo, int pageSize, Integer studentId) {
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		Student student = srepo.findByStudentId(studentId);
		return screpo.findAllCoursesByStudentByPage(student, LocalDate.now().getYear(), pageable);
	}

}
