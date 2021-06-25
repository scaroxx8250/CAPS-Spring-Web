package com.team6.CAPSProj.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.team6.CAPSProj.model.Course;
import com.team6.CAPSProj.model.Student;
import com.team6.CAPSProj.model.StudentCourse;
import com.team6.CAPSProj.service.CourseInterface;
import com.team6.CAPSProj.service.CourseServiceImpl;
import com.team6.CAPSProj.service.LecturerInterface;
import com.team6.CAPSProj.service.LecturerServiceImpl;
import com.team6.CAPSProj.service.StudentCourseInterface;
import com.team6.CAPSProj.service.StudentCourseServiceImpl;
import com.team6.CAPSProj.service.StudentImplementation;
import com.team6.CAPSProj.service.StudentInterface;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private StudentInterface stservice;
	
	@Autowired LecturerInterface lservice;
	
	@Autowired CourseInterface cservice;
	
	@Autowired StudentCourseInterface st_cs_service;
	
	
	public void setStudentInterface(StudentImplementation stImpl) {
		this.stservice = stImpl;
	}
	
	public void setLecturerInterface(LecturerServiceImpl lserviceImpl) {
		this.lservice = lserviceImpl;
	}
	
	public void setCourseInterface(CourseServiceImpl courseserviceImpl) {
		this.cservice = courseserviceImpl;
	}
	
	public void setStudentCourseInterface(StudentCourseServiceImpl st_cs_serviceImpl) {
		this.st_cs_service= st_cs_serviceImpl;
	}
	
	

	@RequestMapping(value = "/studentlist")
	public String list(Model model) {
		model.addAttribute("students", stservice.findAllStudents());
		return "student";
	}
	
	
	@RequestMapping(value = "/deletestudent/{matricNo}")
	public String deleteStudent(@PathVariable("matricNo") String matricNo) {
		//find student
		Student student = stservice.findStudentByMatricNo(matricNo);
		//get list of studentcourses
		List<StudentCourse> studentcourses = st_cs_service.findAllCoursesByStudent(student.getStudentId());
		//get List of courses
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
