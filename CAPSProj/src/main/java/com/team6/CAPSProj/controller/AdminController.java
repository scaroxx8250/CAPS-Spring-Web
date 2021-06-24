package com.team6.CAPSProj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.team6.CAPSProj.service.CourseInterface;
import com.team6.CAPSProj.service.CourseServiceImpl;
import com.team6.CAPSProj.service.LecturerInterface;
import com.team6.CAPSProj.service.LecturerServiceImpl;
import com.team6.CAPSProj.service.StudentImplementation;
import com.team6.CAPSProj.service.StudentInterface;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private StudentInterface stservice;
	
	@Autowired LecturerInterface lservice;
	
	@Autowired CourseInterface cservice;
	
	
	public void setStudentInterface(StudentImplementation stImpl) {
		this.stservice = stImpl;
	}
	
	public void setLecturerInterface(LecturerServiceImpl lserviceImpl) {
		this.lservice = lserviceImpl;
	}
	
	public void setCourseInterface(CourseServiceImpl courseserviceImpl) {
		this.cservice = courseserviceImpl;
	}
	

	@RequestMapping(value = "/studentlist")
	public String list(Model model) {
		model.addAttribute("students", stservice.findAllStudents());
		return "student";
	}
	
	
	
	


	
	
}
