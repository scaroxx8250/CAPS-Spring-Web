package com.team6.CAPSProj.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team6.CAPSProj.model.Course;
import com.team6.CAPSProj.repo.CourseRepository;
@Service
public class CourseServiceImpl implements CourseInterface {

	@Autowired
	CourseRepository crepo;
	

	public List<Course> findAllCourses(List<Integer> CourseId) {
		// TODO Auto-generated method stub
		return null;
	}


	public List<Course> findAllCourseforCurrentYear() {
		// TODO Auto-generated method stub
		return null;
	}


	public Course findCourseByCourseName(String courseName) {
		// TODO Auto-generated method stub
		return null;
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
			courseFromDB.setCredits(course.getCourseId());
			courseFromDB.setDescription(course.getDescription());
			courseFromDB.setFaculty(course.getFaculty());
			courseFromDB.setLecturer(course.getLecturer());
			courseFromDB.setSize(course.getSize());
			courseFromDB.setStudentCourses(course.getStudentCourses());
			crepo.save(courseFromDB);
		}

	}


	public void deleteCourse(Course course) {
		crepo.delete(course);
	}


	public List<Course> findCoursesByLecturerId(int lecturerId) {
		return crepo.findCourseByLecturer(lecturerId);
	}


	public List<Course> findAllCourseByYear(String currentYear) {
		
		return crepo.findCourseByCurrentYear(currentYear);
		

	}


	public List<Course> findAllCourseByYearAndLecturerId(String year, int lecturerId) {
		
		return crepo.findCourseByYearAndLecturer(year, lecturerId);

	}

}
