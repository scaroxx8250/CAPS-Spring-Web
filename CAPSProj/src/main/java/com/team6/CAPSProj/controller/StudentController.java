package com.team6.CAPSProj.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

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
import com.team6.CAPSProj.service.CourseService;
import com.team6.CAPSProj.service.StudentCourseService;

@Controller
@RequestMapping("/student")
public class StudentController {
	
	@Autowired
	private StudentCourseService scservice;
		
	@Autowired
	private CourseService cservice;
	

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
		
		// get student's grades for all years
		List<StudentCourse> AllTimeGradedCourses = scservice.findAllGradeByStudent(enrolCourses, s);
		HashMap<String, String> ATcreditGPA = CalculateUtility.calGradeCourse(AllTimeGradedCourses);
		model.addAttribute("cuCredits", ATcreditGPA.get("credits"));
		model.addAttribute("cuGPA", ATcreditGPA.get("gpa"));
		
		// get All Graded Years
		List<String> acadYears = CalculateUtility.sortAcadYearsDesc(AllTimeGradedCourses);
		model.addAttribute("ay",acadYears);
			
		if(!acadYears.isEmpty())
		{
			//get the latest year from the graded courses
			String year = acadYears.stream().max(Comparator.comparing(Integer::valueOf)).get();
			
			
			// get student's grades for latest year		
			List<StudentCourse> AyGradedCourses = scservice.findAllGradeByYearAndStudent(enrolCourses, s, Integer.valueOf(year));
			HashMap<String, String> AYcreditGPA = CalculateUtility.calGradeCourse(AyGradedCourses);
			model.addAttribute("gradedCourse", AyGradedCourses);
			model.addAttribute("ayCredits", AYcreditGPA.get("credits"));
			model.addAttribute("ayGPA", AYcreditGPA.get("gpa"));
		}
		else 
		{
			model.addAttribute("gradedCourse", null);
			model.addAttribute("ayCredits", null);
			model.addAttribute("ayGPA", null);
		}
		
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

