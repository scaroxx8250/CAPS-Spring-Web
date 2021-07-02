package com.team6.CAPSProj.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.team6.CAPSProj.model.Course;
import com.team6.CAPSProj.model.CourseGrades;
import com.team6.CAPSProj.model.Lecturer;
import com.team6.CAPSProj.model.Student;
import com.team6.CAPSProj.model.StudentCourse;
import com.team6.CAPSProj.service.CourseService;
import com.team6.CAPSProj.service.StudentCourseService;


@Controller
@RequestMapping("/lecturer")
public class LecturerController {
	
	@Autowired
	private CourseService cservice;

	@Autowired 
	private StudentCourseService scservice;

	@RequestMapping(value="/Courses")
	public String listCourses(Model model, HttpSession session) {
		return listCourses(1, session, model);
	}
	
	// View list of courses 
	@RequestMapping(value = "/Courses/{pageNo}")
	public String listCourses (@PathVariable(value="pageNo") int pageNo, HttpSession session, Model model ) {
		// get session
		Lecturer l = (Lecturer) session.getAttribute("usession");

		// session not found
		if(l == null) {
			return "redirect:/home";
		}
		String name = l.getFirstName();
		model.addAttribute("name", name);
		
		int pageSize = 5; 

		Page<Course> page = cservice.findAllPaginatedCourseByYearAndLecturer(pageNo, pageSize, l.getLecturerId());
		List<Course> courselist = page.getContent();
		int studentsEnrolled = 0;
		// instantiate hash map
		Map<Course, Integer> map = new HashMap<>();
		// for each course in the list of courses
		for (Course c : courselist) {
			// get total number of enrolled students 
			studentsEnrolled = scservice.CountTotalStudentEnrol(c.getCourseId());
			// put course(key) and number of enrolled students(value) into hash map 
			map.put(c, studentsEnrolled);
		}
		model.addAttribute("courses", map);
		
		int totalPages = page.getTotalPages(); 
		long totalItems = page.getTotalElements(); 
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages",totalPages);
		model.addAttribute("totalItems",totalItems);
		
		return "courses";
	}
	
	@RequestMapping(value="/CourseEnrolment")
	public String listCourseEnrolment(Model model, HttpSession session) {
		return listCourseEnrolment(0, 1, session, model);
	}
	
	// View Course Enrolment (No. of students enrolled in a course)
	@RequestMapping(value = "/CourseEnrolment/{id}/{pageNo}")
	public String listCourseEnrolment (@PathVariable(value="id") int id, @PathVariable(value="pageNo") int pageNo, HttpSession session, Model model) {
		Lecturer l = (Lecturer) session.getAttribute("usession");	
		if(l == null) {
			return "redirect:/home";
		}
		String name = l.getFirstName();
		model.addAttribute("name", name);
		
		int pageSize = 5; 
		
		// find list of courses that lecturer teaches for current year 
		List<Course> courselist = cservice.findAllCourseByYearAndLecturerId(LocalDate.now().getYear(), l.getLecturerId());
		model.addAttribute("courses", courselist);
		
		Course course1; int totalPages=0; long totalItems =0;
		
		if(!courselist.isEmpty()) {
			if (id == 0)
			{
				course1 = courselist.get(0);
			}
			else
			{
				course1 = courselist.get(id);
			}
			
			Page<StudentCourse> page = scservice.findAllPaginatedStudentsByCourse(pageNo, pageSize, course1.getCourseName());
			List<StudentCourse> studentslist = page.getContent(); 
			model.addAttribute("studentslist", studentslist);
			
			if (id != 0)
			{
				model.addAttribute("id", id); 
				Course selectedCourse = courselist.get(id); 
				model.addAttribute("selectedCourse", selectedCourse); 
			}
			else
			{
				model.addAttribute("id", 0);
				Course selectedCourse = courselist.get(0); 
				model.addAttribute("selectedCourse", selectedCourse); 
			}
			 totalPages = page.getTotalPages(); 
			totalItems = page.getTotalElements(); 
		}
		else {
			model.addAttribute("selectedCourse", null); 
		}

		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages",totalPages);
		model.addAttribute("totalItems",totalItems);
		
		return "enrolment"; 
	}
	
	@RequestMapping(value="/StudentPerformance") 
	public String listPerformance(Model model, HttpSession session) {
		return listPerformance(0, 1, session, model); 
	}
	
	// View Student Performance for a course 
	@RequestMapping(value="/StudentPerformance/{id}/{pageNo}")
	public String listPerformance (@PathVariable(value="id") int id, @PathVariable(value="pageNo") int pageNo, HttpSession session, Model model) {
		Lecturer l = (Lecturer) session.getAttribute("usession");		
		if(l == null) {
			return "redirect:/home";
		}
		String name = l.getFirstName();
		model.addAttribute("name", name);
		
		int pageSize = 5; 
		
		// Get list of courses taught by lecturer (user) 
		List<Course> courselist = cservice.findAllCourseByYearAndLecturerId(LocalDate.now().getYear(), l.getLecturerId()); 
		model.addAttribute("courses", courselist);
		
		Course course1; 
		int totalPages = 0; 
		long totalItems = 0; 
		
		if(!courselist.isEmpty()) {
			if (id == 0)
			{
				course1 = courselist.get(0); 
			}
			else
			{
				course1 = courselist.get(id); 
			}
			
			Page<StudentCourse> page = scservice.findAllPaginatedStudentsByGradedCourse(pageNo, pageSize, course1.getCourseName());
			List<StudentCourse> studentslist = page.getContent(); 
			List<Student> students = new ArrayList<Student>(); 
			for (StudentCourse sc : studentslist) {
				// get all students in the course and add them to a list 
				students.add(sc.getStudent());
			}
		
			model.addAttribute("students", students);
			
			Map<Student, Double> studentGrade = new HashMap<>();
			// for each student in the course, get their grades 
			for (Student s: students) {
				StudentCourse selectedSc = scservice.findGradeByStudentAndCourse(s, course1);
			
				if( selectedSc.getGrade() !=null) {
					studentGrade.put(s, selectedSc.getGrade());
				}
			}
			
			model.addAttribute("studentgrade", studentGrade);
			
			if (id != 0)
			{
				model.addAttribute("id", id); 
				Course selectedCourse = courselist.get(id); 
				model.addAttribute("selectedCourse", selectedCourse); 
			}
			else
			{
				model.addAttribute("id", 0); 
				Course selectedCourse = courselist.get(0); 
				model.addAttribute("selectedCourse", selectedCourse); 
			}
			totalPages = page.getTotalPages(); 
			totalItems = page.getTotalElements(); 
		}
		else
		{
			model.addAttribute("selectedCourse", null); 

		}
	
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages",totalPages);
		model.addAttribute("totalItems",totalItems);			
		
		return "performance"; 
	}
	
	@RequestMapping(value="/GradeCourse")
	public String listGrades (Model model, HttpSession session) {
		return listGrades(0, 1, session, model);
	}
	
	@RequestMapping(value="/GradeCourse/{id}/{pageNo}")
	public String listGrades (@PathVariable(value="id") int id, @PathVariable(value="pageNo") int pageNo, HttpSession session, Model model) {
		
		Lecturer l = (Lecturer) session.getAttribute("usession");
		if(l == null) {
			return "redirect:/home";
		}
		String name = l.getFirstName();
		model.addAttribute("name", name);
		
		// 5 records displayed on one page 
		int pageSize = 5;
		// get all courses taught by lecturer of current year 
		List<Course> courselist = cservice.findAllCourseByYearAndLecturerId(LocalDate.now().getYear(),l.getLecturerId()); 
		model.addAttribute("courses", courselist);
		
		Course course1; 
		int totalPages = 0; 
		long totalItems = 0; 
		
		if(!courselist.isEmpty()) {
			if (id == 0) {
				course1 = courselist.get(0);
			}
	
			else {
			course1 = courselist.get(id);
			}
		
		// find all students according to page number and size 
		Page<StudentCourse>page = scservice.findAllPaginatedStudentsByLecturer(pageNo, pageSize, l.getLecturerId(), course1);
		List<StudentCourse> studentslist =  page.getContent();
		List<Student> students = new ArrayList<Student>(); 
		
		for (StudentCourse sc : studentslist) {
			students.add(sc.getStudent());
		}
		
		model.addAttribute("students", students); 
		
		if (id != 0)
		{
			model.addAttribute("id", id); 
			Course selectedCourse = courselist.get(id);
			model.addAttribute("selectedCourse", selectedCourse); 
		}
		else
		{
			model.addAttribute("id", 0); 
			Course selectedCourse = courselist.get(0); 
			model.addAttribute("selectedCourse", selectedCourse);
		}
		
		totalPages = page.getTotalPages();
		totalItems = page.getTotalElements();
		model.addAttribute("studentGrade",studentslist);
		} 
		else {
			model.addAttribute("selectedCourse", null);
			model.addAttribute("studentGrade",null);

		}
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages",totalPages);
		model.addAttribute("totalItems",totalItems);
			
		CourseGrades cg = new CourseGrades(new ArrayList<StudentCourse>());
			
		model.addAttribute("selectedGrade", cg);
			
		return "gradeCourse"; 
	}
	
	@RequestMapping(value="/GradeCourse/save")
	public String saveGrade(@ModelAttribute("studentGrade") @Valid CourseGrades courseGrade, BindingResult bindingResult, HttpSession session, Model model) {
		Lecturer l = (Lecturer) session.getAttribute("usession");	
		if(l == null) {
			return "redirect:/home";
		}
		if(bindingResult.hasErrors()) {
			return "gradeCourse"; 
		}
		
		if(courseGrade.getStudentCourse() !=null) {
			scservice.updateStudentGrade(courseGrade.getStudentCourse());
		}
		return "success";
	}
	
	@RequestMapping(value="/StudentPerformance/detail/{id}")
	public String listPerformanceDetails (@PathVariable("id")int id, Model model, HttpSession session) {
		Lecturer l = (Lecturer) session.getAttribute("usession");	
		if(l == null) {
			return "redirect:/home";
		}
		String name = l.getFirstName();
		model.addAttribute("name", name);
		
		// find all courses that selected student is enrolled in 
		List<StudentCourse> scourses = scservice.findAllGradedCoursesByStudent(id);
		
		// go to error page when student does not have his/her course graded
		if(scourses.size()<0) {
			return "error";
		}
		
		// get student object
		Student student = scourses.stream().map(s-> s.getStudent()).findFirst().get();
		
		// pass into model
		String studentName = student.getFirstName() + ' ' + student.getLastName();
		model.addAttribute("studentName", studentName);
		model.addAttribute("student", student);
				
		List<Course> courses = new ArrayList<Course>(); 
		
		// get a list of courses
		for (StudentCourse sc: scourses) {
			courses.add(sc.getCourse());
		}		
		
		List<StudentCourse>AyGradedCourses = scservice.findAllGradeByYearAndStudent(courses, student, LocalDate.now().getYear());
		List<StudentCourse>AllTimeGradedCourses = scservice.findAllGradeByStudent(courses, student);
		
		// calculate the current year graded courses' credits and GPA score
		HashMap<String, String> AYcreditGPA = CalculateUtility.calGradeCourse(AyGradedCourses);
		
		 // calculate all year graded course' credits and all year GPA score
		HashMap<String, String> ATcreditGPA = CalculateUtility.calGradeCourse(AllTimeGradedCourses);
		
		// retrieve year that student completed the course and store in acadYears list
		List<String> acadYears = CalculateUtility.sortAcadYearsDesc(AllTimeGradedCourses);
		
		model.addAttribute("gradedCourse", AyGradedCourses);
		model.addAttribute("ayCredits", AYcreditGPA.get("credits"));
		model.addAttribute("ayGPA", AYcreditGPA.get("gpa"));
		model.addAttribute("cuCredits", ATcreditGPA.get("credits"));
		model.addAttribute("cuGPA", ATcreditGPA.get("gpa"));
		model.addAttribute("ay",acadYears);
		
		return "performance_detail";
	}
	
	@RequestMapping(value = "/logout")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession(false); 
		if(session != null) {
			session.invalidate();
		}
		return "logout";
	}
}

		



