package com.team6.CAPSProj.controller;

import javax.validation.Valid;

import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.team6.CAPSProj.model.Course;
import com.team6.CAPSProj.model.Student;
import com.team6.CAPSProj.model.StudentCourse;

import com.team6.CAPSProj.service.CourseInterface;
import com.team6.CAPSProj.service.LecturerInterface;
import com.team6.CAPSProj.service.StudentCourseInterface;
import com.team6.CAPSProj.service.StudentInterface;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private StudentInterface stservice;
	
	@Autowired LecturerInterface lservice;
	
	@Autowired CourseInterface cservice;
	
	@Autowired StudentCourseInterface st_cs_service;
		

	@RequestMapping(value = "/studentlist")
	public String list(Model model) {
		model.addAttribute("students", stservice.findAllStudents());
		return "student";
	}
	
	@RequestMapping(value = "/addstudent")
	public String addStudent(Model model) {
		model.addAttribute("student", new Student());
		return "student-form";
	}
	

	@RequestMapping(value = "/save")
	public String saveStudent(@ModelAttribute("student") @Valid Student student, 
			BindingResult bindingResult,  Model model) {
		if (bindingResult.hasErrors()) {
			return "student-form";
		}
		stservice.addStudent(student);
		return "forward:/admin/studentlist";
	}
	

	@RequestMapping(value = "/editstudent/{matricNo}")
	public String showEditForm(Model model, @PathVariable("matricNo") String matricNo) {
		model.addAttribute("student", stservice.findStudentByMatricNo(matricNo));
		return "editstudentform";
	}
	

	@RequestMapping(value = "/deletestudent/{matricNo}")
	public String deleteStudent(@PathVariable("matricNo") String matricNo) {
		//first we have to remove the student from all assigned courses
		//find student
		Student student = stservice.findStudentByMatricNo(matricNo);
		//get list of studentcourses
		List<StudentCourse> studentcourses = st_cs_service.findAllCoursesByStudent(student.getStudentId());
		//get ist of courses
		List<Course> courseList = new ArrayList<Course>();
 		for(StudentCourse stcourse : studentcourses) {
			courseList.add(stcourse.getCourse());
		}
		//remove student from course
 		for(Course course : courseList) {
 			st_cs_service.removeStudentFromCourse(course, student);
 		}
 		
 		//remove student
		stservice.deleteStudent(stservice.findStudentByMatricNo(matricNo));
		return "forward:/admin/studentlist";
	}
	
}
