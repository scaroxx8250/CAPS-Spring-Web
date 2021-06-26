package com.team6.CAPSProj.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.team6.CAPSProj.model.Course;
import com.team6.CAPSProj.model.Lecturer;
import com.team6.CAPSProj.model.Student;
import com.team6.CAPSProj.model.StudentCourse;
import com.team6.CAPSProj.service.CourseInterface;
import com.team6.CAPSProj.service.LecturerInterface;
import com.team6.CAPSProj.service.LecturerServiceImpl;
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
		int lecturerId = 4;
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
		int lecturerId = 4; 
		List<Course> courselist = cinterface.findCoursesByLecturerId(lecturerId);
		model.addAttribute("courses", courselist);
		
	
		String name = null;
		for(int i = 0; i < courselist.size(); i++) {
			name = courselist.get(i).getCourseName();
		}
		
		int studentId = 0; 
		String firstName = null; 
		String lastName = null; 
		String email = null;
		
		List<StudentCourse> studentslist = scinterface.findAllStudentsByCourse("SA4101");
		List<Student> students  = new ArrayList<Student>(); 
		for (StudentCourse sc: studentslist) {
			students.add(sc.getStudent());
		}
		model.addAttribute("students", students);
		
		return "enrolment"; 
	}
	
//	@RequestMapping(value = "/StudentPerformance")
//	public String listPerformance (Model model) {
		//int lecturerId = lecturer.getLecturerId(); 
//		int lecturerId = 4; 
//		List<Course> courselist = cinterface.findCoursesByLecturerId(lecturerId);
//		model.addAttribute("courses", courselist);
//		
//	
//		String name = null;
//		for(int i = 0; i < courselist.size(); i++) {
//			name = courselist.get(i).getCourseName();
//		}
//		
//		int studentId = 0; 
//		String firstName = null; 
//		String lastName = null; 
//		String grade = null;
//		
//		List<StudentCourse> studentslist = scinterface.findAllStudentsByCourse("SA4101");
//		for (StudentCourse sc: studentslist) {
//			studentId = sc.getStudent().getStudentId();
//			firstName = sc.getStudent().getFirstName();
//			lastName = sc.getStudent().getLastName();
//			grade = sc.getGrade().name();
//		}
//
//		model.addAttribute("students", studentslist);
//		model.addAttribute("studentId", studentId);
//		model.addAttribute("firstname", firstName); 
//		model.addAttribute("lastname", lastName);
//		model.addAttribute("grade", grade); 
//		
//		return "performance"; 
//	}
	
}

		



