package com.team6.CAPSProj.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.team6.CAPSProj.model.Course;

import com.team6.CAPSProj.model.Faculty;
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

	@GetMapping("/list")
	public String ViewCourses(Model model, HttpSession session) {
		
		return ViewCourses(1, session, model);
	}
	
	
	@GetMapping(value="/list/{pageNo}")
	public String ViewCourses(@PathVariable(value = "pageNo") int pageNo, HttpSession session, Model model) {
		
		Student s = (Student) session.getAttribute("usession");
		
		// session not found
		if(s == null) {
			return "redirect:/home"; 
		}
//		List<StudentCourse> scList = scservice.findAllCoursesByStudent(s.getStudentId());
//		List<StudentCourse> scList2 = new ArrayList<StudentCourse>();
//		// get the current course that student has enrolled
//		List<Course> clist = new ArrayList<Course>();
//		for(StudentCourse sc : scList) {
//			if(sc.getGrade() == null && sc.getCourse().getCourseStartDate().getYear() == LocalDate.now().getYear()) {
//				scList2.add(sc);
//			}
//		}
//		// passing the data to view
//		model.addAttribute("Courses",clist);
//		model.addAttribute("scList2", scList2);
		
		int pageSize = 5;
		Page<StudentCourse> page = scservice.findAllPaginatedCoursesByStudent(pageNo, pageSize, s.getStudentId());
		List<StudentCourse> listStudentCourses = page.getContent();
		int totalPages = page.getTotalPages();
		long totalItems = page.getTotalElements();
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("totalItems", totalItems);
		model.addAttribute("listStudentCourses", listStudentCourses);
		return "studentlist";
		
	}
	
	@GetMapping("/enrolCourse")
	public String EnrolCourses(Model model, HttpSession session) {
		
		return EnrolCourses(0,1, session, model);
	}
	
	@RequestMapping(value="/enrolCourse/{facultyId}/{pageNo}")
	public String EnrolCourses(@PathVariable(value = "facultyId") int facultyId, @PathVariable(value = "pageNo") int pageNo, HttpSession session, Model model) {
		Student s = (Student) session.getAttribute("usession");
		
		// session not found
		if(s == null) {
			return "redirect:/home";
		}
		int pageSize = 5;
//		List<StudentCourse> scList = scservice.findAllCoursesByStudent(s.getStudentId());
//		List<Course> allCourses = cservice.findAllCourseforCurrentYear();
//		
//		// retrieve the list of courses that student has enrolled
//		List<Course> enrolCourses = new ArrayList<Course>();
//		for(StudentCourse sc : scList) {
//			enrolCourses.add(sc.getCourse());
//		}
//		
//		// filter the courses that student has not enrolled
//		List<Course> availableCourses = new ArrayList<Course>();
//		availableCourses.addAll(allCourses);
//		for(Course ac: allCourses) {
//			for(Course ec: enrolCourses) {
//				if(ac.getCourseId() == ec.getCourseId()) {
//					availableCourses.remove(ac);
//				}
//			}
//		}
//
//		// filter the courses that student has not enrolled and is available to start tomorrow onwards
//		availableCourses = availableCourses.stream().
//				filter(c->c.getCourseStartDate().isAfter(LocalDate.now()) && scservice.CountTotalStudentEnrol(c.getCourseId())<c.getSize())
//				.collect(Collectors.toList());
		
		// passing the data to view
		StudentSelectedCourses ssc = new StudentSelectedCourses(new ArrayList<Course>());
		//model.addAttribute("notEnrolCourses",availableCourses);
		model.addAttribute("selectCourse", ssc);
		
		// fill up facultyList with from faculty enums
		List<Faculty> facultyList = Arrays.asList(Faculty.values());
		model.addAttribute("facultyList", facultyList);	
		
		
		// get the faculty enum
		Faculty faculty1;
		if(facultyId == 0) {
				faculty1 = facultyList.get(0);
		}
		else {
			faculty1 = facultyList.get(facultyId);
		}	
		//page = cservice.findAllPaginatedNotEnrolledCoursesByStudent(pageNo, pageSize, s.getStudentId());
		
		int fac = faculty1.ordinal();
		
		//  retrieve the page object
		Page<Course> page = cservice.findAllPaginatedNotEnrolledCoursesByStudentAndFaculty(pageNo, pageSize, s.getStudentId(),fac);
		List<Course> notEnrolledCourses = page.getContent();
			
	//facultyList.addAll( notEnrolledCourses.stream().map(c->c.getFaculty().toString()).distinct().sorted().collect(Collectors.toList()));
		
		
		int totalPages = page.getTotalPages();
		long totalItems = page.getTotalElements();
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("totalItems", totalItems);
		model.addAttribute("notEnrolledCourses", notEnrolledCourses);	
		
		
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
	@GetMapping(value="/gradesGPA")
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
		acadYears = acadYears.stream().sorted((p1,p2) -> {
			if(Integer.valueOf(p1) > Integer.valueOf(p2))
				return -1;
			else if (Integer.valueOf(p1)< Integer.valueOf(p2))
				return 1;
			else
				return 0;
		}).collect(Collectors.toList());
		
	
		// passing the data to view
		model.addAttribute("gradedCourse", AyGradedCourses);
		model.addAttribute("ayCredits", ayCredits);
		model.addAttribute("ayGPA", ayGPA);
		model.addAttribute("cuCredits", cuCredits);
		model.addAttribute("cuGPA", cuGPA);
		model.addAttribute("ay",acadYears);
		return "studentGradeCourse";
	}
	

	
}
