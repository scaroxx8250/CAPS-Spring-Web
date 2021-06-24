package com.team6.CAPSProj.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
public class test {
	
	
	//Only used to display current HTML template, to be deleted later
	@RequestMapping(value = "/test")
	public String DisplayHTMLTemplate() {
		return "template";
		}
}
