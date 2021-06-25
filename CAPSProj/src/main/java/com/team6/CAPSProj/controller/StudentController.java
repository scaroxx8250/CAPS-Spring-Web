package com.team6.CAPSProj.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.team6.CAPSProj.model.Course;
import com.team6.CAPSProj.model.Student;
import com.team6.CAPSProj.model.StudentCourse;
import com.team6.CAPSProj.model.StudentSelectedCourses;
import com.team6.CAPSProj.service.CourseInterface;
import com.team6.CAPSProj.service.StudentCourseInterface;
import com.team6.CAPSProj.service.StudentInterface;

@Controller
@RequestMapping("/student")
public class StudentController {
	@Autowired
	private StudentInterface stservice;	

	@Autowired
	private StudentCourseInterface scservice;
		
	@Autowired
	private CourseInterface cservice;

	
	@GetMapping(value="/list")
	public String ViewCourses(HttpSession session, Model model) {
		Student s = (Student) session.getAttribute("usession");
		if(s == null) {
			return "redirect:/home"; 
		}
		List<StudentCourse> scList = scservice.findAllCoursesByStudent(s.getStudentId());
		List<Course> clist = new ArrayList<Course>();
		for(StudentCourse sc : scList) {
			clist.add(sc.getCourse());
		}
		
		model.addAttribute("Courses",clist);
		return "studentlist";
	}
	@RequestMapping(value="/enrolCourse")
	public String EnrolCourses(HttpSession session, Model model) {
		Student s = (Student) session.getAttribute("usession");
		
		if(s == null) {
			return "redirect:/home";
		}
		List<StudentCourse> scList = scservice.findAllCoursesByStudent(s.getStudentId());
		List<Course> allCourses = cservice.findAllCourseforCurrentYear();
		
		List<Course> enrolCourses = new ArrayList<Course>();
		for(StudentCourse sc : scList) {
			enrolCourses.add(sc.getCourse());
		}
		
	// avoid java.util.ConcurrentModificationException
		List<Course> availableCourses = new ArrayList<Course>();
		availableCourses.addAll(allCourses);
		for(Course ac: allCourses) {
			for(Course ec: enrolCourses) {
				if(ac.getCourseId() == ec.getCourseId()) {
					availableCourses.remove(ac);
				}
			}
		}
		
		//filter elements in the availableCourses
		availableCourses = availableCourses.stream().
				filter(c->c.getCourseStartDate().isAfter(LocalDate.now()))
				.collect(Collectors.toList());
			
		
		model.addAttribute("notEnrolCourses",availableCourses);
		return "enrolCourse";
	}
	
	@PostMapping(value="/submitEnrol")
	public String submitEnrolCourse(@ModelAttribute("selectCourse") StudentSelectedCourses course, HttpSession session, Model model) {
		Student s = (Student) session.getAttribute("usession");
		return "studentlist";
		
	
	}

	
}
