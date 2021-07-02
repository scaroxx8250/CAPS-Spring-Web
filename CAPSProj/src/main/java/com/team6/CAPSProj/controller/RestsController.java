package com.team6.CAPSProj.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.team6.CAPSProj.exception.BusinessException;
import com.team6.CAPSProj.model.Course;
import com.team6.CAPSProj.model.Student;
import com.team6.CAPSProj.model.StudentCourse;
import com.team6.CAPSProj.model.StudentGPA;
import com.team6.CAPSProj.service.StudentCourseService;
import com.team6.CAPSProj.service.StudentService;

@RestController
@RequestMapping("/api")
public class RestsController {
	
	@Autowired
	private StudentCourseService scservice;
	
	@Autowired
	private StudentService stservice;
	
	@RequestMapping("/student/{id}/{year}")
	@ResponseBody
	public HashMap<String,Object> getGradedCourse(@PathVariable("id") int id,@PathVariable("year") int year ) throws BusinessException {
		if( id ==0) {
			throw new BusinessException("id is invalid");
		}
		HashMap<String,Object> Items = new HashMap<String, Object>();
		
		// get all the courses enrolled by the student
		List<StudentCourse> scList = scservice.findAllCoursesByStudent(id);	
		
		Student s = stservice.findStudentByStudentId(id);
		List<Course> enrolCourses = new ArrayList<Course>();
		for(StudentCourse sc : scList) {
			enrolCourses.add(sc.getCourse());
		}
		
		// get student's grades for selected year
		List<StudentCourse> AyGradedCourses = scservice.findAllGradeByYearAndStudent(enrolCourses, s, year);
		
		if(AyGradedCourses.isEmpty()) {
			throw new BusinessException("GPA Record not found");
		}
		
		// calculate the given year graded courses' credits and GPA score
		HashMap<String, String> AYcreditGPA = CalculateUtility.calGradeCourse(AyGradedCourses);
		Items.put("ayCredits", AYcreditGPA.get("credits"));
		Items.put("ayGPA", AYcreditGPA.get("gpa"));
		
		// get student's grades for all year
		List<StudentCourse> AllTimeGradedCourses = scservice.findAllGradeByStudent(enrolCourses, s);
		
		if(AllTimeGradedCourses.isEmpty()) {
			throw new BusinessException("GPA Record not found");
		}
				
		// calculate all year graded course' credits and all year GPA score
		HashMap<String, String> ATcreditGPA = CalculateUtility.calGradeCourse(AllTimeGradedCourses);
		Items.put("cuCredits", ATcreditGPA.get("credits"));
		Items.put("cuGPA", ATcreditGPA.get("gpa"));
		
		// retrieve year that student completed the course and store in acadYears list
		List<String> acadYears = CalculateUtility.sortAcadYearsDesc(AllTimeGradedCourses);
		Items.put("ay",acadYears);
		
		// put into Data Transfer object cuurentYearGC
		List<StudentGPA> currentYearGC = new ArrayList<StudentGPA>();
		
		for(StudentCourse agc : AyGradedCourses) {
			StudentGPA sgpa = new StudentGPA();
			sgpa.setCourseId(agc.getCourse().getCourseId());
			sgpa.setCourseName(agc.getCourse().getCourseName());
			sgpa.setCredits(agc.getCourse().getCredits());
			sgpa.setGrade(agc.getGrade());
			sgpa.setYear(String.valueOf(agc.getCourse().getCourseStartDate().getYear()));
			currentYearGC.add(sgpa);
		}

		Items.put("gradedCourse", currentYearGC);
		
		return Items;			
	}
}
