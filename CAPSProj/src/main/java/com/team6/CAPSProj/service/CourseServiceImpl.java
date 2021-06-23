package com.team6.CAPSProj.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team6.CAPSProj.model.Course;
import com.team6.CAPSProj.repo.CourseRepository;
@Service
public class CourseServiceImpl implements  CourseInterface{
	
	@Autowired 
	CourseRepository crepo;
	

	public List<Course>findAllCourses(List<Integer> CourseId)
	{
		List<Course> clist= new ArrayList<Course>();
		
		for(Integer id : CourseId)
		{
			clist.add(crepo.findCourseByid(id));
		}
		
		return clist;
	}
	

	public List<Course>findAllCourseforCurrentYear()
	{
		LocalDate lt = LocalDate.now();
		String year = String.valueOf(lt.getYear());
		return crepo.findCoursesByYear(year);
	}
	

	public Course findCourseByCourseName(String courseName)
	{
		Course c1 = crepo.findCourseByName(courseName);
		return c1;
	}

	public void addCourse(Course course) {
		// TODO Auto-generated method stub
		
	}

	
	public void updateCourse(Course course) {
		// TODO Auto-generated method stub
		
	}


	public void deleteCourse(Course course) {
		// TODO Auto-generated method stub
		
	}

	
	public List<Course> findCoursesByLecturerId(int lecturerId) {
		// TODO Auto-generated method stub
		return null;
	}


	public List<Course> findAllCourseByYear(String year) {
		// TODO Auto-generated method stub
		return null;
	}


	public List<Course> findAllCourseByYearAndLecturerId(String year, int lecturerId) {
		// TODO Auto-generated method stub
		return null;
	}

}
