package com.team6.CAPSProj.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

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
import com.team6.CAPSProj.service.StudentCourseInterface;
import com.team6.CAPSProj.service.StudentInterface;

@RestController
@RequestMapping("/api")
public class RestsController {
	@Autowired
	private StudentCourseInterface scservice;
	@Autowired
	private StudentInterface stservice;
	
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
				// get student's grades for current year and all years
				int ayCredits =0, cuCredits =0 ;
				double ayGPA = 0, cuGPA =0;
				List<StudentCourse> AllTimeGradedCourses = scservice.findAllGradeByStudent(enrolCourses, s);
				List<StudentCourse> AyGradedCourses = scservice.findAllGradeByYearAndStudent(enrolCourses, s, year);
				
				if(AyGradedCourses.isEmpty()) {
					throw new BusinessException("GPA Record not found");
				}
				
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
				
				
				DecimalFormat df = new DecimalFormat("0.00"); 
				
				// calculate the given year graded courses' credits and GPA score
				for (StudentCourse sc: AyGradedCourses) {
					ayCredits += sc.getCourse().getCredits();
					ayGPA += sc.getGrade() * sc.getCourse().getCredits();
				}
				ayGPA = ayGPA/ayCredits;
				String ayGPAFormatted = df.format(ayGPA);
				
				 // calculate all year graded course' credits and all year GPA score
				List<String> acadYears = new ArrayList<String>();
				for (StudentCourse sc: AllTimeGradedCourses) {
				cuCredits += sc.getCourse().getCredits();
				cuGPA += sc.getGrade() * sc.getCourse().getCredits();
				
				// retrieve year that student completed the course and store in acadYears list
				String courseYear = String.valueOf(sc.getCourse().getCourseStartDate().getYear());
				if(!acadYears.contains(courseYear)){
					acadYears.add(String.valueOf(sc.getCourse().getCourseStartDate().getYear()));
					}
				}
				cuGPA = cuGPA/cuCredits;
				String cuGPAFormatted = df.format(cuGPA);
				
				// sort the acadYears in descending order
				acadYears = acadYears.stream().sorted((p1,p2) -> {
					if(Integer.valueOf(p1) > Integer.valueOf(p2))
						return -1;
					else if (Integer.valueOf(p1)< Integer.valueOf(p2))
						return 1;
					else
						return 0;
				}).collect(Collectors.toList());
		
			
				// passing the data to view
				Items.put("gradedCourse", currentYearGC);
				Items.put("ayCredits", ayCredits);
				Items.put("ayGPA", ayGPAFormatted);
				Items.put("cuCredits", cuCredits);
				Items.put("cuGPA", cuGPAFormatted);
				Items.put("ay",acadYears);
				return Items;
	}
}
