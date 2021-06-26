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
import com.team6.CAPSProj.model.Lecturer;
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
	public String listStudent(Model model) {
		model.addAttribute("students", stservice.findAllStudents());
		return "student_manage";
	}
	
	@RequestMapping(value = "/addstudent")
	public String addStudent(Model model) {
		model.addAttribute("student", new Student());
		return "student_add";
	}
	
	@RequestMapping(value = "/save")
	public String saveStudent(@ModelAttribute("student") @Valid Student student, 
			BindingResult bindingResult,  Model model) {
		if (bindingResult.hasErrors()) {
			return "student_add";
		}
		stservice.addStudent(student);
		return "forward:/admin/studentlist";
	}
	
	@RequestMapping(value = "/editstudent/{matricNo}")
	public String showEditForm(Model model, @PathVariable("matricNo") String matricNo) {
		model.addAttribute("student", stservice.findStudentByMatricNo(matricNo));
		return "student_edit";
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
	
	
	
	@RequestMapping(value = "/lecturerlist")
	public String listLecturer(Model model) {
		model.addAttribute("lecturers", lservice.GetAllLecturers());
		return "lecturer";
	}
	
	@RequestMapping(value = "/addlecturer")
	public String addLecturer(Model model) {
		model.addAttribute("lecturer", new Lecturer());
		return "lecturer-form";
	}

	@RequestMapping(value = "/saveLecturer")
	public String saveLecturer(@ModelAttribute("lecturer") @Valid Lecturer lecturer, 
			BindingResult bindingResult,  Model model) {
		if (bindingResult.hasErrors()) {
			return "lecturer-form";
		}
		lservice.addLecturer(lecturer);
		return "forward:/admin/lecturerlist";
	}
	
	@RequestMapping(value = "/editLecturer/{lecturerId}")
	public String editLecturer(Model model, @PathVariable("lecturerId") int lecturerId) {
		model.addAttribute("student", lservice.findLecturerById(lecturerId));
		return "editlecturerform";
	}
	
	@RequestMapping(value = "/deletelecturer/{lecturerId}")
	public String deleteLecturer(@PathVariable("lecturerId") Integer lecturerId) {
		lservice.deleteLecturer(lservice.findLecturerById(lecturerId));
		return "forward:/facility/list";
	}
	
}
