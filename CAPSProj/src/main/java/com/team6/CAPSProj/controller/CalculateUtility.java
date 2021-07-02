package com.team6.CAPSProj.controller;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.team6.CAPSProj.model.StudentCourse;

public final class CalculateUtility {

	
	public final static HashMap<String, String> calGradeCourse(List<StudentCourse> sclist) {
		
		DecimalFormat df = new DecimalFormat("0.00");
		HashMap<String, String> creditAndGPA = new HashMap<String, String>();
		int credits =0;
		double gpa = 0;
		
		for(StudentCourse sc: sclist) {
			 credits += sc.getCourse().getCredits();
			 gpa += sc.getGrade()* sc.getCourse().getCredits();
		   
		}
		gpa = gpa/credits;
		creditAndGPA.put("credits",String.valueOf(credits));
		creditAndGPA.put("gpa", df.format(gpa));
		
		return creditAndGPA;
		
	}
	public final static List<String> sortAcadYearsDesc(List<StudentCourse> sclist){
		
		List<String> acadYears = sclist.stream().map(c->String.valueOf( c.getCourse().getCourseStartDate().getYear())).distinct().sorted((p1,p2) -> {
			if(Integer.valueOf(p1) > Integer.valueOf(p2))
				return -1;
			else if (Integer.valueOf(p1)< Integer.valueOf(p2))
				return 1;
			else
				return 0;
		}).collect(Collectors.toList());
		
		return acadYears;
	
	}
	

	
}
