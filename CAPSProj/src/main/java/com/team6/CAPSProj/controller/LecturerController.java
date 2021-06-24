package com.team6.CAPSProj.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

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
	
	@Autowired
	public void setLecturerInterface(LecturerServiceImpl lserviceImpl) {
		this.linterface = lserviceImpl;
	}
	
	@RequestMapping(value = "/Courses")
	public String listCourses (@ModelAttribute("lecturer") @Valid Lecturer lecturer, BindingResult bindingResult, Model model ) {
		//int lecturerId = lecturer.getLecturerId();
		int lecturerId = 4;
		List<Course> courselist = cinterface.findCoursesByLecturerId(lecturerId);
		model.addAttribute("courses", courselist);
		int studentsEnrolled = 0;
		for (Course c : courselist) {
			studentsEnrolled = scinterface.CountTotalStudentEnrol(c.getCourseId());
		}
		model.addAttribute("students", studentsEnrolled);
		
		return "courses";
	}
	
	@RequestMapping(value = "/CourseEnrolment")
	public String listCourseEnrolment (@ModelAttribute("lecturer") @Valid Lecturer lecturer, BindingResult bindingResult, Model model) {
		int lecturerId = lecturer.getLecturerId(); 
		List<Course> courselist = cinterface.findCoursesByLecturerId(lecturerId); 
		model.addAttribute("courses", courselist); 
		for (Course c : courselist) {
			scinterface.findAllStudentsByCourse(c.getCourseName());
		}
		return "enrolment"; 
	}
	
	@RequestMapping(value = "/StudentPerformance")
	public String listPerformance (@ModelAttribute("lecturer") @Valid Lecturer lecturer, BindingResult bindingResult, Model model) {
		int lecturerId = lecturer.getLecturerId(); 
		List<Course> courselist = cinterface.findCoursesByLecturerId(lecturerId); 
		model.addAttribute("courses", courselist); 
		ArrayList<Student> students = new ArrayList<Student>(); 
		for (Course c : courselist) {
			students.add((Student) scinterface.findAllStudentsByCourse(c.getCourseName()));
			for (Student s : students) {
				scinterface.findAllGradeByYearAndStudent(courselist, s, c.getCourseStartDate().getYear());
			}
		}
		return "performance"; 
		
	}

		
}


