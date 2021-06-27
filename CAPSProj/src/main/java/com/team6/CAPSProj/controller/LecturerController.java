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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.team6.CAPSProj.model.Course;
import com.team6.CAPSProj.model.CourseGrades;
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
	
	@RequestMapping(value = "/Courses")
	public String listCourses (HttpSession session, Model model ) {
		//Lecturer l = (Lecturer) session.getAttribute("usession");
		//int lecturerId = l.getLecturerId();
		int lecturerId = 2;
		List<Course> courselist = cinterface.findCoursesByLecturerId(lecturerId);
		int studentsEnrolled = 0;
		Map<Course, Integer> map = new HashMap<>();
		for (Course c : courselist) {
			studentsEnrolled = scinterface.CountTotalStudentEnrol(c.getCourseId());
			map.put(c, studentsEnrolled);
		}
		model.addAttribute("courses", map);
		
		return "courses";
	}
	
	@RequestMapping(value = "/CourseEnrolment")
	public String listCourseEnrolment (Model model) {
		//int lecturerId = lecturer.getLecturerId(); 
			
		int lecturerId = 2; 
		List<Course> courselist = cinterface.findCoursesByLecturerId(lecturerId);
		model.addAttribute("courses", courselist);
		
		List<StudentCourse> studentslist = scinterface.findAllStudentsByCourse("SCI101");
		List<Student> students  = new ArrayList<Student>(); 
		for (StudentCourse sc: studentslist) {
			students.add(sc.getStudent());
		}
		model.addAttribute("students", students);
		
		return "enrolment"; 
	}
	
	@RequestMapping(value="/StudentPerformance")
	public String listPerformance (Model model) {
		List<Course> courselist = cinterface.findCoursesByLecturerId(2); 
		model.addAttribute("courses", courselist);
		String coursename = null;
		for (Course c: courselist)
		{
			if (c.getCourseName().equals("SCI101")) {
				coursename = "SCI101";
				List<StudentCourse> studentcourse = scinterface.findAllStudentsByCourse(coursename);
				List<Student> students = new ArrayList<Student>(); 
				Map<Student, Double> studentGrade = new HashMap<>();
				for (StudentCourse sc : studentcourse) {
					students.add(sc.getStudent());
				}
				model.addAttribute("students", students);
				
				for (Student s: students) {
					double grade = scinterface.findGradeByStudentAndCourse(s, c);
					studentGrade.put(s, grade);
				}			
				
				model.addAttribute("studentgrade", studentGrade);
			}
		}

		return "performance"; 
	}
	
	
	@RequestMapping(value="/GradeCourse")
	public String listGrades (Model model) {
		List<Course> courselist = cinterface.findCoursesByLecturerId(2); 
		model.addAttribute("courses", courselist);
		
		if(courselist.iterator().hasNext()) {
			String courseName = courselist.iterator().next().getCourseName();
			ArrayList<StudentCourse> studentGrade = (ArrayList<StudentCourse>) scinterface.findAllStudentsByCourse(courseName);
			
			CourseGrades cg = new CourseGrades();
			cg.setGradeList(studentGrade);
			
			model.addAttribute("wrapper", cg);
			
			
		}
		
		
		
		
		
//		for (Course c: courselist)
//		{
//			if (c.getCourseName().equals("SCI101")) {
//				coursename = "SCI101";
//				List<StudentCourse> studentcourse = scinterface.findAllStudentsByCourse(coursename);
//				List<Student> students = new ArrayList<Student>(); 
//				Map<Student, Double> studentGrade = new HashMap<>();
//				for (StudentCourse sc : studentcourse) {
//					students.add(sc.getStudent());
//				}
//				model.addAttribute("students", students);
//				
//				for (Student s: students) {
//					double grade = scinterface.findGradeByStudentAndCourse(s, c);
//					studentGrade.put(s, grade);
//				}			
//				
//				model.addAttribute("studentgrade", studentGrade);
//			}
//			
//		}

		return "gradeCourse"; 
	}
	
	@RequestMapping(value="/GradeCourse/save")
	public String saveGrade(@ModelAttribute("studentGrade") @Valid StudentCourse scourse, BindingResult bindingResult, Model model) {
		
		if(bindingResult.hasErrors()) {
			return "gradeCourse"; 
		}
		
		System.out.println(scourse);
		
		return "forward:/lecturer/gradeCourse";
		
	}
	
	@RequestMapping(value="/StudentPerformance/detail/{id}")
	public String listPerformanceDetails (@PathVariable("id")int id, Model model) {

		List<StudentCourse> scourses = scinterface.findAllCoursesByStudent(id);
				
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

		



