package com.team6.CAPSProj.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
		return "admin_student_manage";
	}
	
	@RequestMapping(value = "/addstudent")
	public String addStudent(Model model) {
		model.addAttribute("student", new Student());
		return "admin_student_add";
	}
	
	@RequestMapping(value = "/save")
	public String saveStudent(@ModelAttribute("student") @Valid Student student, 
			BindingResult bindingResult,  Model model) {
		if (bindingResult.hasErrors()) {
			return "admin_student_add";
		}
		stservice.addStudent(student);
		return "forward:/admin/studentlist";
	}
	
	@RequestMapping(value = "/editstudent/{matricNo}")
	public String showEditForm(Model model, @PathVariable("matricNo") String matricNo) {
		model.addAttribute("student", stservice.findStudentByMatricNo(matricNo));
		return "admin_student_edit";
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
		return "admin_lecturer_manage";
	}
	
	@RequestMapping(value = "/addlecturer")
	public String addLecturer(Model model) {
		model.addAttribute("lecturer", new Lecturer());
		return "admin_lecturer_add";
	}

	@RequestMapping(value = "/saveLecturer")
	public String saveLecturer(@ModelAttribute("lecturer") @Valid Lecturer lecturer, 
			BindingResult bindingResult,  Model model) {
		if (bindingResult.hasErrors()) {
			return "admin_lecturer_add";
		}
		lservice.addLecturer(lecturer);
		return "forward:/admin/lecturerlist";
	}
	

	@RequestMapping(value = "/editLecturer/{lecturerId}")
	public String editLecturer(Model model, @PathVariable("lecturerId") int lecturerId) {
		model.addAttribute("lecturer", lservice.findLecturerById(lecturerId));
		return "admin_lecturer_edit";
	}
	
	@RequestMapping(value = "/deletelecturer/{lecturerId}")
	public String deleteLecturer(@PathVariable("lecturerId") Integer lecturerId) {
		List <Course> course_delete = cservice.findCoursesByLecturerId(lecturerId);
		for(Course course: course_delete) {
			course.setLecturer(null);
			cservice.updateCourse(course);
		}
		lservice.deleteLecturer(lservice.findLecturerById(lecturerId));
		return "forward:/admin/lecturerlist";
	}
	
	
	@RequestMapping(value = "/enrollmentlist")
	public String listEnrollment(Model model, @Valid Course course)
	{
		List<Course> courselist = cservice.getAllCourses();
		model.addAttribute("courses", courselist);
		
		List<StudentCourse> sclist = st_cs_service.findAllStudentsByCourse(course.getCourseName());
		model.addAttribute("student_course", sclist);
		return "managementEnrolment";
	}
	
	@RequestMapping(value = "/addenrollment/{coursename}")
	public String addEnrollment(Model model, @PathVariable("coursename") String coursename) {
		
		Course course = cservice.findCourseByCourseName(coursename);
		
		//List<StudentCourse> stlist = st_cs_service.findAllStudentsByCourse(coursename);
		
		
		model.addAttribute("course", course);
		return "admin_enrollment_add";
	}

	
	

	
	@RequestMapping(value = "/courselist")
	public String listCourse(Model model) {
		model.addAttribute("courses", cservice.findAllCourseforCurrentYear());
		return "admin_course_manage";
	}
	
	
	@RequestMapping(value = "/addcourse")
	public String addCourse(Model model) {
		model.addAttribute("course", new Course());
		List<Lecturer> lecturerList = lservice.GetAllLecturers();
		model.addAttribute("lecturerList", lecturerList);
		return "admin_course_add";
	}
	
	
	@RequestMapping(value = "/savecourse")
	public String saveCourse(@ModelAttribute("course") @Valid Course course, 
			BindingResult bindingResult,  Model model) {
		if (bindingResult.hasErrors()) {
			return "course_add";
		}
		cservice.addCourse(course);
		return "redirect:/admin/courselist";
	}
	
	
	@RequestMapping(value = "/editcourse/{coursename}")
	public String showCourseEditForm(Model model, @PathVariable("coursename") String coursename) {
		
		model.addAttribute("course", cservice.findCourseByCourseName(coursename));
		List<Lecturer> lecturerList = lservice.GetAllLecturers();
		model.addAttribute("lecturerList", lecturerList);
		return "admin_course_edit";
	}
	
	@RequestMapping(value = "/deletecourse/{courseName}")
	public String deleteCourse(@PathVariable("courseName") String courseName) {
	
		List<StudentCourse> studentcourses = st_cs_service.findAllStudentsByCourse(cservice.findCourseByCourseName(courseName).getCourseName());
		
		for(StudentCourse stucourse : studentcourses) {
			st_cs_service.removeStudentCourse(stucourse);
		}
		
		cservice.deleteCourse(cservice.findCourseByCourseName(courseName));
		return "forward:/admin/courselist";
	}
	
	@RequestMapping(value = "/logout")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession(false); 
		if(session != null) {
			session.invalidate();
		}
		return "logout";
	}
}
