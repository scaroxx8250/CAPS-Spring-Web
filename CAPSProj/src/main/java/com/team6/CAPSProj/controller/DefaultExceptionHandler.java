package com.team6.CAPSProj.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.team6.CAPSProj.exception.BusinessException;
import com.team6.CAPSProj.exception.InvalidInputException;

@ControllerAdvice
public class DefaultExceptionHandler {
	 private static final Logger logger = LoggerFactory.getLogger(DefaultExceptionHandler.class);
	     
	 
	     @ExceptionHandler(value = { InvalidInputException.class })
	 
	     public String handleInvalidInputException(InvalidInputException ex, Model model) {
	    	
	    	 logger.error("Invalid Input Exception: " + ex.getMessage());
	    	 model.addAttribute("errorMsg", ex.getMessage());
	    	 
	         return "error";
	 
	     }
	 
	     
	 
	     @ExceptionHandler(value = { BusinessException.class })
	 
	     public String handleBusinessException(BusinessException ex, Model model) {
	 
	    	 logger.error("Business Exception: " + ex.getMessage());
	    	 
	    	 
	    	 model.addAttribute("errorMsg", ex.getMessage());
	    	 return "error";
	 
	     }
	 
	     
	 
	     @ExceptionHandler(value = { Exception.class })
	 
	     public String handleException(Exception ex, Model model) {
	 
	    	 logger.error("Exception: " + ex.getMessage());
	 
	    	 model.addAttribute("errorMsg", ex.getMessage());
	    	 return "error";
	 
	 
	     }

}
