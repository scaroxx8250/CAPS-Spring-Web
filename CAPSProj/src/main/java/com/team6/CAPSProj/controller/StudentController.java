package com.team6.CAPSProj.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.team6.CAPSProj.model.Course;
import com.team6.CAPSProj.model.LetterGrade;
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
		
		// session not found
		if(s == null) {
			return "redirect:/home"; 
		}
		List<StudentCourse> scList = scservice.findAllCoursesByStudent(s.getStudentId());
		
		// get the current course that student has enrolled
		List<Course> clist = new ArrayList<Course>();
		for(StudentCourse sc : scList) {
			if(sc.getGrade() == null && sc.getCourse().getCourseStartDate().getYear() == LocalDate.now().getYear()) {
				clist.add(sc.getCourse());
			}
			
		}
		// passing the data to view
		model.addAttribute("Courses",clist);
		
		return "studentlist";
	}
	@RequestMapping(value="/enrolCourse")
	public String EnrolCourses( Model model,HttpSession session) {
		Student s = (Student) session.getAttribute("usession");
		
		// session not found
		if(s == null) {
			return "redirect:/home";
		}
		
		List<StudentCourse> scList = scservice.findAllCoursesByStudent(s.getStudentId());
		List<Course> allCourses = cservice.findAllCourseforCurrentYear();
		
		// retrieve the list of courses that student has enrolled
		List<Course> enrolCourses = new ArrayList<Course>();
		for(StudentCourse sc : scList) {
			enrolCourses.add(sc.getCourse());
		}
		
		// filter the courses that student has not enrolled
		List<Course> availableCourses = new ArrayList<Course>();
		availableCourses.addAll(allCourses);
		for(Course ac: allCourses) {
			for(Course ec: enrolCourses) {
				if(ac.getCourseId() == ec.getCourseId()) {
					availableCourses.remove(ac);
				}
			}
		}
		
		// filter the courses that student has not enrolled and is available to start tomorrow onwards
		availableCourses = availableCourses.stream().
				filter(c->c.getCourseStartDate().isAfter(LocalDate.now()))
				.collect(Collectors.toList());
		
		// passing the data to view
		StudentSelectedCourses ssc = new StudentSelectedCourses(new ArrayList<Course>());
		model.addAttribute("notEnrolCourses",availableCourses);
		model.addAttribute("selectCourse", ssc);
		
		return "enrolCourse";
	}
	
	@PostMapping(value="/submitEnrol")
	public String submitEnrolCourse(@ModelAttribute("selectCourse") StudentSelectedCourses clist, HttpSession session, Model model, BindingResult bindingResult) {
		Student s = (Student) session.getAttribute("usession");
		
		// session not found
		if(s == null) {
			return "redirect:/home"; 
		}
		
		// if the courses is/are selected, assign student to course
		if(clist.getCourse().size() != 0)
		{
			for(Course c : clist.course)
			{
				scservice.addStudentToCourse(c, s);
			}
			return "redirect:/student/list";
		}
		return "enrolCourse";	
	}
	@GetMapping(value="/GradesGPA")
	public String gradesAndGPA(Model model,HttpSession session ) {
		Student s = (Student) session.getAttribute("usession");
		
		// session not found
		if(s == null) {
			return "redirect:/home";
		}
		
		// get all the courses enrolled by the student
		List<StudentCourse> scList = scservice.findAllCoursesByStudent(s.getStudentId());	
		List<Course> enrolCourses = new ArrayList<Course>();
		for(StudentCourse sc : scList) {
			enrolCourses.add(sc.getCourse());
		}
		
		// get student's grades for current year and the past years
		int ayCredits =0, cuCredits =0 ;
		double ayGPA = 0, cuGPA =0;
		List<StudentCourse> AyGradedCourses = scservice.findAllGradeByYearAndStudent(enrolCourses, s, LocalDate.now().getYear());
		List<StudentCourse> AllTimeGradedCourses = scservice.findAllGradeByStudent(enrolCourses, s);
	
		
		// calculate the current year graded courses' credits and GPA score
		for (StudentCourse sc: AyGradedCourses) {
			ayCredits += sc.getCourse().getCredits();
			ayGPA += sc.getGrade() * sc.getCourse().getCredits();
		}
		ayGPA = ayGPA/ayCredits;
		
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
		
		// sort the acadYears in descending order
		acadYears.stream().sorted();
		
	
		// passing the data to view
		model.addAttribute("gradedCourse", scList);
		model.addAttribute("ayCredits", ayCredits);
		model.addAttribute("ayGPA", ayGPA);
		model.addAttribute("cuCredits", cuCredits);
		model.addAttribute("cuGPA", cuGPA);
		model.addAttribute("ay",acadYears);
		return "studentGradeCourse";
	}
	

	
}
