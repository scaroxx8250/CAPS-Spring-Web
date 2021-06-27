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
import com.team6.CAPSProj.model.Student;
import com.team6.CAPSProj.repo.CourseRepository;
import com.team6.CAPSProj.repo.StudentRepository;
@Service
public class CourseServiceImpl implements CourseInterface {

	@Autowired
	CourseRepository crepo;
	
	@Autowired
	StudentRepository srepo;
	
	
	public List<Course>findAllCourses(List<Integer> CourseId)
	{
		List<Course> courseList= new ArrayList<Course>();
		
		for(Integer id : CourseId)
		{
			Course course = crepo.findByCourseId(id);
			if(course!=null) {
				courseList.add(course);
			}		
		}
		
		return courseList;
	}
	

	public List<Course>findAllCourseforCurrentYear()
	{
		LocalDate ld = LocalDate.now();
		return crepo.findCoursesByYear(ld.getYear());
	}
	

	public Course findCourseByCourseName(String courseName)
	{
		LocalDate ld = LocalDate.now();
		return crepo.findByCourseNameWithCurrentYear(courseName,ld.getYear());
	}

	public void addCourse(Course course) {
		
		crepo.save(course);
	}

	
	public void updateCourse(Course course) {
		
		int courseId = course.getCourseId();
		
		Course courseFromDB = crepo.findById(courseId).get();
		
		if (courseFromDB !=null) {
			
			courseFromDB.setCourseName(course.getCourseName());
			courseFromDB.setCourseStartDate(course.getCourseStartDate());
			courseFromDB.setCredits(course.getCredits());
			courseFromDB.setDescription(course.getDescription());
			courseFromDB.setFaculty(course.getFaculty());
			courseFromDB.setLecturer(course.getLecturer());
			courseFromDB.setSize(course.getSize());
			courseFromDB.setStudentCourses(course.getStudentCourses());
			courseFromDB.setCourseOccupancy(course.getCourseOccupancy());
			crepo.save(courseFromDB);
		}

	}


	public void deleteCourse(Course course) {
		
		crepo.delete(course);
	}


	public List<Course> findCoursesByLecturerId(int lecturerId) {
		
		return crepo.findCourseByLecturer(lecturerId);
	}


	public List<Course> findAllCourseByYear(int currentYear) {
		
		return crepo.findCourseByCurrentYear(currentYear);
	}


	public List<Course> findAllCourseByYearAndLecturerId(int year, int lecturerId) {	
		
		return crepo.findCourseByYearAndLecturer(year, lecturerId);
	}


	@Override
	public Page<Course> findAllPaginatedNotEnrolledCoursesByStudent(int pageNo, int pageSize, Integer studentId) {
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		return crepo.findAllNotEnrolledCoursesByStudentByPage(studentId, pageable);
	}
	
//	public List<Course>getAllCourses()
//	{
//		return crepo.findAll();
//	}
	
}

