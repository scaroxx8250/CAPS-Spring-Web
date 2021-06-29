package com.team6.CAPSProj.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import com.team6.CAPSProj.service.CourseInterface;
import com.team6.CAPSProj.service.LecturerInterface;
import com.team6.CAPSProj.service.StudentCourseInterface;
import com.team6.CAPSProj.service.StudentInterface;


@Controller
@RequestMapping("/lecturer")
public class LecturerController {
	
	@Autowired
	private CourseInterface cinterface;
	
	@Autowired 
	private LecturerInterface linterface; 
	
	@Autowired 
	private StudentCourseInterface scinterface;
	
	@Autowired 
	private StudentInterface sinterface; 
	
//	@Autowired
//	public void setLecturerInterface(LecturerServiceImpl lserviceImpl) {
//		this.linterface = lserviceImpl;
//	}
	
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
		
		int pageSize = 5; 

		Page<Course> page = cinterface.findAllPaginatedCoursesByLecturerId(pageNo, pageSize, l.getLecturerId());
		List<Course> courselist = page.getContent();
		int studentsEnrolled = 0;
		// instantiate hash map
		Map<Course, Integer> map = new HashMap<>();
		// for each course in the list of courses
		for (Course c : courselist) {
			// get total number of enrolled students 
			studentsEnrolled = scinterface.CountTotalStudentEnrol(c.getCourseId());
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
		return listCourseEnrolment(1, session, model);
	}
	
	// View Course Enrolment (No. of students enrolled in a course)
	@RequestMapping(value = "/CourseEnrolment/{pageNo}")
	public String listCourseEnrolment (@PathVariable(value="pageNo") int pageNo, HttpSession session, Model model) {
		Lecturer l = (Lecturer) session.getAttribute("usession");	
		if(l == null) {
			return "redirect:/home";
		}
		
		int pageSize = 5; 
		
		// find list of courses that lecturer teaches 
		List<Course> courselist = cinterface.findCoursesByLecturerId(l.getLecturerId());
		model.addAttribute("courses", courselist);
		
		if(courselist.iterator().hasNext()) {
			// iterate through the course list to get a singular course 
			Course course = courselist.iterator().next();
		
		Page<StudentCourse> page = scinterface.findAllPaginatedStudentsByCourse(pageNo, pageSize, course.getCourseName());
		List<StudentCourse> studentslist = page.getContent(); 
		List<Student> students  = new ArrayList<Student>();
		// get all students in the course 
		for (StudentCourse sc: studentslist) {
			// add them into a student list 
			students.add(sc.getStudent());
		}
		model.addAttribute("students", students);
		
		int totalPages = page.getTotalPages(); 
		long totalItems = page.getTotalElements(); 
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages",totalPages);
		model.addAttribute("totalItems",totalItems);
		
		}
		return "enrolment"; 
	}
	
	// View Student Performance for a course 
	@RequestMapping(value="/StudentPerformance")
	public String listPerformance (HttpSession session, Model model) {
		Lecturer l = (Lecturer) session.getAttribute("usession");		
		if(l == null) {
			return "redirect:/home";
		}
		
		// Get list of courses taught by lecturer (user) 
		List<Course> courselist = cinterface.findCoursesByLecturerId(l.getLecturerId()); 
		model.addAttribute("courses", courselist);
		if(courselist.iterator().hasNext()) {
			Course course = courselist.iterator().next();
				List<StudentCourse> studentcourse = scinterface.findAllStudentsByCourse(course.getCourseName());
				List<Student> students = new ArrayList<Student>(); 
				for (StudentCourse sc : studentcourse) {
					// get all students in the course and add them to a list 
					students.add(sc.getStudent());
				}
				model.addAttribute("students", students);
		
				Map<Student, Double> studentGrade = new HashMap<>();
				// for each student in the course, get their grades 
				for (Student s: students) {
				     StudentCourse selectedSc = scinterface.findGradeByStudentAndCourse(s, course);
		
					if( selectedSc.getGrade() !=null) {
						studentGrade.put(s, selectedSc.getGrade());
					}
					
				}			
				
				model.addAttribute("studentgrade", studentGrade);
			}
		
		return "performance"; 
	}
	
	@RequestMapping(value="/GradeCourse")
	public String listGrades (Model model, HttpSession session) {
		return listGrades(1, session, model);
		
	}
	
	@RequestMapping(value="/GradeCourse/{pageNo}")
	public String listGrades (@PathVariable(value="pageNo") int pageNo, HttpSession session, Model model) {
		
		Lecturer l = (Lecturer) session.getAttribute("usession");
		if(l == null) {
			return "redirect:/home";
		}
		
		// 5 records displayed on one page 
		int pageSize = 5;
		// get all courses taught by lecturer of current year 
		List<Course> courselist = cinterface.findAllCourseByYearAndLecturerId(LocalDate.now().getYear(),l.getLecturerId()); 
		model.addAttribute("courses", courselist);
		
		if(courselist.iterator().hasNext()) {
			Course course = courselist.iterator().next();
			
			// find all students according to page number and size 
			Page<StudentCourse>page = scinterface.findAllPaginatedStudentsByLecturer(pageNo, pageSize, l.getLecturerId(), course);
			List<StudentCourse> studentGrade =  page.getContent();
			int totalPages = page.getTotalPages();
			long totalItems = page.getTotalElements();
			model.addAttribute("currentPage", pageNo);
			model.addAttribute("totalPages",totalPages);
			model.addAttribute("totalItems",totalItems);
			
			CourseGrades cg = new CourseGrades(new ArrayList<StudentCourse>());
			
			model.addAttribute("selectedGrade", cg);
			model.addAttribute("studentGrade",studentGrade);
			
			
		}

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
			scinterface.updateStudentGrade(courseGrade.getStudentCourse());
		}
		
		return "success";
		
	}
	
	@RequestMapping(value="/StudentPerformance/detail/{id}")
	public String listPerformanceDetails (@PathVariable("id")int id, Model model, HttpSession session) {
		Lecturer l = (Lecturer) session.getAttribute("usession");	
		if(l == null) {
			return "redirect:/home";
		}
		
		// find all courses that selected student is enrolled in 
		List<StudentCourse> scourses = scinterface.findAllGradedCoursesByStudent(id);
				
		String firstName = sinterface.findFirstNameByStudentId(id);
		model.addAttribute("firstName", firstName); 
		
		String lastName = sinterface.findLastNameByStudentId(id); 
		model.addAttribute("lastName", lastName); 
				
		Student student = sinterface.findStudentByStudentId(id);
		model.addAttribute("student", student);
				
		List<Course> courses = new ArrayList<Course>(); 
		Map<Course, Double> studentGrade = new HashMap<>();
		
		for (StudentCourse sc : scourses) {
			courses.add(sc.getCourse());
		}
		model.addAttribute("courses", courses);
				
		for (StudentCourse sc: scourses) {
			studentGrade.put(sc.getCourse(), sc.getGrade());
		}
				
		model.addAttribute("studentGrade", studentGrade);
		int size = studentGrade.size();
		model.addAttribute("size", size); 
				
		int ayCredits=0, cuCredits=0; 
		double ayGPA=0, cuGPA=0;
		List<StudentCourse>AyGradedCourses = scinterface.findAllGradeByYearAndStudent(courses, student, LocalDate.now().getYear());
		List<StudentCourse>AllTimeGradedCourses = scinterface.findAllGradeByStudent(courses, student);
		
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
		
		model.addAttribute("gradedCourse", AyGradedCourses);
		model.addAttribute("ayCredits", ayCredits);
		model.addAttribute("ayGPA", ayGPA);
		model.addAttribute("cuCredits", cuCredits);
		model.addAttribute("cuGPA", cuGPA);
		model.addAttribute("ay",acadYears);
		
		return "performance_detail";
	}
	
}

		



