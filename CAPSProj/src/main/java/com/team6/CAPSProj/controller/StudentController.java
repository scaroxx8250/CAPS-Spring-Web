package com.team6.CAPSProj.controller;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
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

@Controller
@RequestMapping("/student")
public class StudentController {
	
	@Autowired
	private StudentCourseInterface scservice;
		
	@Autowired
	private CourseInterface cservice;

	@GetMapping("/list")
	public String ViewCourses(Model model, HttpSession session) {
		
		return ViewCourses(1, session, model);
	}
	
	//method for result of courses being enrolled
   public String ViewCoursesResult(Model model, HttpSession session, Boolean enrol_course_status) {
		
	   if(enrol_course_status) {
		   model.addAttribute("status", true);
	   }
	   else {
		   model.addAttribute("status", false);
	   }
		   
		return ViewCourses(1, session, model);
	}
	
	@GetMapping(value="/list/{pageNo}")
	public String ViewCourses(@PathVariable(value = "pageNo") int pageNo, HttpSession session, Model model) {
		
		Student s = (Student) session.getAttribute("usession");
		
		// session not found
		if(s == null) {
			return "redirect:/home"; 
		}
		
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
		
		// passing the data to view
		StudentSelectedCourses ssc = new StudentSelectedCourses(new ArrayList<Course>());
		model.addAttribute("selectCourse", ssc);
		
		// fill up facultyList with from faculty enum
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
		
		int fac = faculty1.ordinal();
		
		//  retrieve the page object
		Page<Course> page = cservice.findAllPaginatedNotEnrolledCoursesByStudentAndFaculty(pageNo, pageSize, s.getStudentId(),fac);
		List<Course> notEnrolledCourses = page.getContent();
			
		
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
		int totalCourse_enrol_success =0, totalCourseAvail_for_enrol =0;
		
		// session not found
		if(s == null) {
			return "redirect:/home"; 
		}
		
		// if the courses is/are selected, assign student to course
		if(clist.getCourse().size() != 0)
		{
			for(Course c : clist.course)
			{		totalCourseAvail_for_enrol++;
			
				if(scservice.addStudentToCourse(c, s)) {
					totalCourse_enrol_success++;
					
				}
			}
			// result of enrolling course(s) transfer to ViewCourseResult method
			if(totalCourse_enrol_success != totalCourseAvail_for_enrol) {
				return ViewCoursesResult(model, session,false);
			}
			else {
				return ViewCoursesResult(model, session,true);
			}
			
			
			
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
	
		DecimalFormat df = new DecimalFormat("0.00"); 
		// calculate the current year graded courses' credits and GPA score
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
		model.addAttribute("gradedCourse", AyGradedCourses);
		model.addAttribute("ayCredits", ayCredits);
		model.addAttribute("ayGPA", ayGPAFormatted);
		model.addAttribute("cuCredits", cuCredits);
		model.addAttribute("cuGPA", cuGPAFormatted);
		model.addAttribute("ay",acadYears);
		return "studentGradeCourse";
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

