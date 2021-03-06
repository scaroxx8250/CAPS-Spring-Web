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
public class StudentCourseServiceImpl implements StudentCourseService {
	
	@Autowired
	StudentCourseRepository screpo; 
	
	@Autowired 
	CourseRepository crepo; 
	
	@Autowired
	StudentRepository srepo; 
	
	@Autowired
	CourseService cservice;
	
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
	
	public StudentCourse findGradeByStudentAndCourse(Student student, Course course) {
		if(screpo.findByCourseIdAndStudentId(course, student)!=null) {
		return screpo.findByCourseIdAndStudentId(course, student). get(0);
		}
		return null;
		 
	}

	public boolean addStudentToCourse(Course course, Student student) {
		// Can only add student to a course if the course has not yet started
		// i.e. courseStartDate is later than the current date
		
		// Student can only enrol in courses that have not started at time of enrolment
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
				return true;
			}	
		}
		return false;
	}
	
	public boolean adminAddStudentToCourse(Course course, Student student) {
		// Can only add student to a course if the course has not yet started
		// i.e. courseStartDate is later than the current date
		
		// Admin can add student to course within a 14-day window after course has started
		LocalDate date = LocalDate.now();
		if (course.getCourseStartDate().isAfter(date.minusDays(14)))
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
				return true;
			}	
		}
		return false;
	}

	
	public void removeStudentFromCourse(Course course, Student student) {
		StudentCourse toDelete = screpo.findByCourseIdAndStudentIdAndYear(course, student,2021).get(0);
		if (toDelete != null) {
			screpo.delete(toDelete);
			if(course.getCourseOccupancy() == CourseOccupancy.FULL)
			{
				course.setCourseOccupancy(CourseOccupancy.NOTFULL);
				cservice.updateCourse(course);
			}
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
	

	public Page<StudentCourse> findAllPaginatedStudentsByLecturer(int pageNo, int pageSize, Integer lecturerId, Course course) {
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		return screpo.findAllStudentsByLecturerByPage(course,lecturerId, LocalDate.now().getYear(), pageable);
	}
	
	public Page<StudentCourse>findAllPaginatedStudentsByGradedCourse(int pageNo, int pageSize, String CourseName){
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		Course course = crepo.findByCourseNameWithCurrentYear(CourseName,LocalDate.now().getYear());
		return screpo.findAllStudentsByGradedCourseByPage(course, LocalDate.now().getYear(), pageable);
	}
	
	public List<StudentCourse> findAllGradedCoursesByStudent(Integer studentId) {
		Student student = srepo.findByStudentId(studentId);
		return screpo.findAllGradedCoursesByStudent(student);
	}


	@Override
	public Page<StudentCourse> findAllPaginatedStudentsByCourse(int pageNo, int pageSize, String CourseName) {
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		Course course = crepo.findByCourseNameWithCurrentYear(CourseName,LocalDate.now().getYear());
		return screpo.findAllStudentsByCourseByPage(course, LocalDate.now().getYear(), pageable);
	}
}
