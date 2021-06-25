package com.team6.CAPSProj.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.team6.CAPSProj.model.Role;
import com.team6.CAPSProj.model.Student;
import com.team6.CAPSProj.model.User;
import com.team6.CAPSProj.service.StudentInterface;



@Controller
@RequestMapping("/")
public class HomeController {

	@Autowired
	StudentInterface stservice;
	
	@Autowired
	public void setStudent(StudentInterface st) {
		this.stservice = st;
	}
	@RequestMapping("/home")
	public String login(Model model) {
		User user = new User();
		model.addAttribute("user",user);
		 return "index";
		
	}
	
	@PostMapping(path = "/authenticate")
	public String authenticate(@ModelAttribute("user")User user, Model model, HttpSession session) {
		
		Role role = user.getRole();
		if(role == Role.STUDENT)
		{
			Student s = stservice.findByEmailAndPassword(user.getEmailAdd(), user.getPassword());
			
			if( s!=null) {
				session.setAttribute("usession", s);
				return "redirect:/student/list";
			}
			else {
				return "index";
			}
			
		}
		else if(role == Role.LECTURER)
		{
			return "index";
		}
		else
		{
			// Admin
			return "index";
		}
		
//		if(uservice.authenticate(user)) 
//		{
//			User u = uservice.findByName(user.getUserName());
//			session.setAttribute("usession", u);
//			return "welcome";
//		}
//		else
//			return "login";
	}
	
}
