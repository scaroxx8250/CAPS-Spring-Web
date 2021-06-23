package com.team6.CAPSProj.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team6.CAPSProj.model.Course;
import com.team6.CAPSProj.repo.CourseRepository;
@Service
public class CourseServiceImpl implements CourseInterface {

	@Autowired
	CourseRepository crepo;
	
	public List<Course>findAllCourses(List<Integer> CourseId)
	{
		List<Course> clist= new ArrayList<Course>();
		
		for(Integer id : CourseId)
		{
			clist.add(crepo.findByCourseId(id));
		}
		
		return clist;
	}
	

	public List<Course>findAllCourseforCurrentYear()
	{
		LocalDate lt = LocalDate.now();
		return crepo.findCoursesByYear(lt.getYear());
	}
	

	public Course findCourseByCourseName(String courseName)
	{
		Course c1 = crepo.findByCourseNameWithCurrentYear(courseName,LocalDate.now().getYear());
		return c1;
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
	
	


}

