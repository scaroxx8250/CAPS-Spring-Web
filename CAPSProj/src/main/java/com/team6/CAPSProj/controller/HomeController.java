package com.team6.CAPSProj.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.team6.CAPSProj.model.Admin;
import com.team6.CAPSProj.model.Lecturer;
import com.team6.CAPSProj.model.Role;
import com.team6.CAPSProj.model.Student;
import com.team6.CAPSProj.model.User;
import com.team6.CAPSProj.service.AdminService;
import com.team6.CAPSProj.service.LecturerInterface;
import com.team6.CAPSProj.service.StudentInterface;



@Controller
@RequestMapping("/")
public class HomeController {

	@Autowired
	private StudentInterface stservice;
	
	@Autowired 
	private LecturerInterface lservice; 
	
	@Autowired 
	private AdminService aservice; 


	@RequestMapping("/home")
	public String login(Model model) {
		User user = new User();
		model.addAttribute("user",user);
		 return "index";
		
	}
	
	@RequestMapping(path = "/authenticate", method=RequestMethod.POST)
	public String authenticate(@Valid @ModelAttribute("user")User user, BindingResult bindingResult, HttpSession session,Model model) {
		
		if(bindingResult.hasErrors()) {
			return "index";
		}
		
		Role role = user.getRole();
		if(role == Role.STUDENT)
		{
			Student s = stservice.findByEmailAndPassword(user.getEmailAdd(), user.getPassword());
			
			if( s!=null) {
				session.setAttribute("usession", s);
				return "redirect:/student/gradesGPA";
			}
			
				return "index";
		}
		else if(role == Role.LECTURER)
		{
			Lecturer l = lservice.findByEmailAndPassword(user.getEmailAdd(), user.getPassword());
			if( l!=null) {
				session.setAttribute("usession", l);
				return "redirect:/lecturer/Courses";
			}
			
			return "index";
		}
		else
		{
			Admin a = aservice.findByEmailAndPassword(user.getEmailAdd(), user.getPassword());
			if( a!=null) {
				session.setAttribute("usession", a);
				return "redirect:/admin/studentlist";
			}
			return "index";
		}
		
	}
	
}
