package com.team6.CAPSProj.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.team6.CAPSProj.model.Admin;
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
	
	@Autowired StudentInterface stservice;
	
	@Autowired LecturerInterface lservice;
	
	@Autowired CourseInterface cservice;
	
	@Autowired StudentCourseInterface st_cs_service;
	
	public boolean authenticateAdmin(HttpSession session)
	{
		Admin ad = (Admin) session.getAttribute("usession");
		if (ad == null)
			return false;
		return true;
	}

	@RequestMapping(value = "/studentlist")
	public String listStudent(Model model, HttpSession session, Boolean delete_student_status) 
	{
		if(!authenticateAdmin(session))
			return "redirect:/home";
		
		model.addAttribute("studentlist", stservice.findAllStudents());
		
		if(delete_student_status != null)
			model.addAttribute("status", delete_student_status);
		
		return "admin_student_manage";
	}
	
	@RequestMapping(value = "/addstudent")
	public String addStudent(Model model , HttpSession session) 
	{
		if(!authenticateAdmin(session))
			return "redirect:/home";
		
		model.addAttribute("student", new Student());
		return "admin_student_add";
	}
	
	@RequestMapping(value = "/addstudent/save")
	public String saveStudent(@ModelAttribute("student") @Valid Student student, 
			BindingResult bindingResult,  Model model, HttpSession session) 
	{
		if(!authenticateAdmin(session))
			return "redirect:/home";

		if (bindingResult.hasErrors()) 
			return "admin_student_add";
		
		stservice.addStudent(student);
		return "forward:/admin/studentlist";
	}
	
	@RequestMapping(value = "/editstudent/save")
	public String saveEditedStudent(@ModelAttribute("student") @Valid Student student, 
			BindingResult bindingResult,  Model model, HttpSession session) 
	{
		if(!authenticateAdmin(session))
			return "redirect:/home";
		
		if (bindingResult.hasErrors()) 
			return "admin_student_edit";

		stservice.addStudent(student);
		return "forward:/admin/studentlist";
	}
	
	@RequestMapping(value = "/editstudent/{matricNo}")
	public String showEditForm(Model model, @PathVariable("matricNo") String matricNo, HttpSession session) 
	{
		if(!authenticateAdmin(session))
			return "redirect:/home";

		model.addAttribute("student", stservice.findStudentByMatricNo(matricNo));
		return "admin_student_edit";

	}
	
	@RequestMapping(value = "/deletestudent/{matricNo}")
	public String deleteStudent(@PathVariable("matricNo") String matricNo, HttpSession session, Model model) 
	{
		if(!authenticateAdmin(session))
			return "redirect:/home";

		// First we have to remove the student from all assigned courses
		// Find student
		Student student = stservice.findStudentByMatricNo(matricNo);
		
		// Get list of studentcourses
		List<StudentCourse> studentcourses = st_cs_service.findAllCoursesByStudent(student.getStudentId());
 		
 		for(StudentCourse sc: studentcourses)
 		{
 			st_cs_service.removeStudentFromCourse(sc.getCourse(), sc.getStudent());
 		}
 		
 		// Remove student
		stservice.deleteStudent(student);
		return listStudent(model, session, true);
	}

	@RequestMapping(value = "/lecturerlist")
	public String listLecturer(Model model, HttpSession session, Boolean delete_lecturer_status) 
	{
		if(!authenticateAdmin(session))
			return "redirect:/home";

		List<Lecturer> lecturers = lservice.GetAllLecturers();
			
		// status will be null when accessing this page for the first time (not coming from admin/deletelecturer)
		if(delete_lecturer_status != null)
		{
			// If user is coming from admin/deletelecturer, show feedback whether delete is successful or not
			model.addAttribute("status", delete_lecturer_status);
		}
			
		model.addAttribute("lecturers", lecturers);
		
		return "admin_lecturer_manage";
	}
	
	@RequestMapping(value = "/addlecturer")
	public String addLecturer(Model model, HttpSession session) 
	{
		if(!authenticateAdmin(session))
			return "redirect:/home";
		
		model.addAttribute("lecturer", new Lecturer());
		return "admin_lecturer_add";
	}

	@RequestMapping(value = "/addlecturer/save")
	public String saveLecturer(@ModelAttribute("lecturer") @Valid Lecturer lecturer, 
			BindingResult bindingResult,  Model model, HttpSession session) 
	{
		if(!authenticateAdmin(session))
			return "redirect:/home";

		if (bindingResult.hasErrors()) 
			return "admin_lecturer_add";

		lservice.addLecturer(lecturer);
		return "forward:/admin/lecturerlist";

	}
	
	@RequestMapping(value = "/editlecturer/save")
	public String saveEditedLecturer(@ModelAttribute("lecturer") @Valid Lecturer lecturer, 
			BindingResult bindingResult,  Model model, HttpSession session) 
	{
		if(!authenticateAdmin(session))
			return "redirect:/home";
		
		if (bindingResult.hasErrors()) 
			return "admin_lecturer_edit";
		
		lservice.addLecturer(lecturer);
		return "forward:/admin/lecturerlist";
	}
	
	

	@RequestMapping(value = "/editLecturer/{lecturerId}")
	public String editLecturer(Model model, @PathVariable("lecturerId") int lecturerId, HttpSession session) 
	{
		if(!authenticateAdmin(session))
			return "redirect:/home";

		model.addAttribute("lecturer", lservice.findLecturerById(lecturerId));
		return "admin_lecturer_edit";

	}
	
	@RequestMapping(value = "/deletelecturer/{lecturerId}")
	public String deleteLecturer(@PathVariable("lecturerId") Integer lecturerId,  HttpSession session, Model model) 
	{
		if(!authenticateAdmin(session))
			return "redirect:/home";

		List <Course> course_delete = cservice.findCoursesByLecturerId(lecturerId);
		// Can only delete a lecturer when he has no more courses assigned to him
		if (course_delete.size() == 0)
		{
			lservice.deleteLecturer(lservice.findLecturerById(lecturerId));
			// True means delete is successful
			return listLecturer(model, session, true);
		}
		else // There are still courses assigned to this lecturer, cannot delete lecturer
		{
			// False means delete is unsuccessful
			return listLecturer(model, session, false);
		}
	}
	
	
	@RequestMapping(value = "/enrolmentlist")
	public String listEnrolment(Model model, Integer id, Integer statusId, HttpSession session, Boolean enrol_student_status, boolean unenrol_student_status)
	{
		if(!authenticateAdmin(session))
			return "redirect:/home";

		List<Course> courselist = cservice.findAllCourseforCurrentYear();
		
		// If there are no courses in the database, we redirect the admin to the course overview page so that courses can be added
		if(courselist.size() > 0)
		{
			model.addAttribute("courses", courselist);
			
			Course course1;
			
			if(id == null)
				course1 = cservice.findAllCourseByYear(LocalDate.now().getYear()).get(0);
			else
				course1 = cservice.findAllCourseByYear(LocalDate.now().getYear()).get(id);

			List<StudentCourse> sclist = st_cs_service.findAllStudentsByCourse(course1.getCourseName());
			
			List<Student> students = new ArrayList<Student>();
			for (StudentCourse sc: sclist)
			{
				students.add(sc.getStudent());
			}
			
			model.addAttribute("studentlist", students);
			model.addAttribute("course", new Course());
			
			if(id != null)
			{
				model.addAttribute("id", id);
				Course selectedCourse = courselist.get(id);
				model.addAttribute("selectedCourse", selectedCourse);
			}
			else
			{
				model.addAttribute("id", 0);
				Course selectedCourse = courselist.get(0);
				model.addAttribute("selectedCourse", selectedCourse);
			}
			
			// This status comes from controller method enrollStudent
			// If true, the student has been successfully added to the course
			if(enrol_student_status!= null)
				model.addAttribute("status", enrol_student_status);

			// This status comes from controller method removeEnrollment
			// If true, the student has been successfully removed from the course
			if(unenrol_student_status == true) 
				model.addAttribute("enrolment_delete_success", unenrol_student_status);
			
			return "admin_enrolment_manage";
		}
		return "redirect:/admin/courselist";
	}
	
	@RequestMapping(value = "/enrolmentlist/{id}")
	public String listEnrolment(@PathVariable("id") Integer id, Model model, HttpSession session)
	{	
		if(!authenticateAdmin(session))
			return "redirect:/home";
		
		return listEnrolment(model, id, null, session, null, false);
	}
	
	@RequestMapping(value = "/enrolmentlist/{id}/{statusId}")
	public String listEnrolment(@PathVariable("id") Integer id, Model model, @PathVariable("statusId") Integer statusId, HttpSession session)
	{	
		if(!authenticateAdmin(session))
			return "redirect:/home";
    
		if(statusId == 1)
			return listEnrolment(model, id, statusId, session, true, false);
		else
			return listEnrolment(model, id, statusId, session, false, false);
	}
	
	@RequestMapping(value = "/addenrolment/{id}/{pageNo}")
	public String addEnrolment(@PathVariable(value = "pageNo") int pageNo, Model model, @PathVariable("id") int id, HttpSession session) 
	{
		if(!authenticateAdmin(session))
			return "redirect:/home";

		// Get course
		Course course = cservice.findAllCourseByYear(LocalDate.now().getYear()).get(id);
		
		model.addAttribute("course", course);
		int pageSize = 4;
		Page<Student> page = stservice.findAllPaginatedNotEnrolledStudentsByCourse(pageNo, pageSize, course.getCourseId());
		List<Student> notEnrolledStudents = page.getContent();
		int totalPages = page.getTotalPages();
		long totalItems = page.getTotalElements();
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("totalItems", totalItems);
		model.addAttribute("notEnrolledStudents", notEnrolledStudents);
		model.addAttribute("id", id);
		return "admin_enrolment_add";
	}
	
	@RequestMapping(value = "/enrolstudent/{matricNo}/{coursename}/{id}")
	public String enrollStudent(@PathVariable("matricNo") String matricNo, @PathVariable("coursename") String coursename, @PathVariable("id") int id, Model model, HttpSession session) 
	{
		if(!authenticateAdmin(session))
			return "redirect:/home";

		// Get Student
		Student student = stservice.findStudentByMatricNo(matricNo);
		// Get course
		Course course = cservice.findCourseByCourseName(coursename);
		// Assign student to course
		boolean status = st_cs_service.adminAddStudentToCourse(course, student);
		
		if(status) // status == true means student has been added to course
			return "redirect:/admin/enrolmentlist/{id}/1";
		else // status == false means student could not be added to course
			return "redirect:/admin/enrolmentlist/{id}/0";
	}
	
	@RequestMapping(value = "/removeenrolment/{matricNo}/{coursename}/{id}")
	public String removeEnrollment(@PathVariable("matricNo") String matricNo, @PathVariable("coursename") String coursename, @PathVariable("id") int id, Model model, HttpSession session) 
	{
		if(!authenticateAdmin(session))
			return "redirect:/home";
		
		// Get Student
		Student student = stservice.findStudentByMatricNo(matricNo);
		// Get course
		Course course = cservice.findCourseByCourseName(coursename);
		// Remove student from course
		st_cs_service.removeStudentFromCourse(course, student);
		
		return listEnrolment(model, id, null, session, null, true);
	}

	@RequestMapping(value = "/courselist")
	public String listCourse(Model model, HttpSession session, Boolean delete_course_status) 
	{
		if(!authenticateAdmin(session))
			return "redirect:/home";
		
		List<Course> courses = cservice.findAllCourseforCurrentYear();
		model.addAttribute("courses", courses);
		
		// If delete_course_status is not null, means the admin tried to delete a course, then we will display a success message
		// Null means that they didn't even try to delete a course
		if(delete_course_status != null)
			model.addAttribute("status", delete_course_status);
		
		return "admin_course_manage";
	}
	
	@RequestMapping(value = "/addcourse")
	public String addCourse(Model model, HttpSession session) 
	{
		if(!authenticateAdmin(session))
			return "redirect:/home";
		
		model.addAttribute("course", new Course());
		List<Lecturer> lecturerList = lservice.GetAllLecturers();
		model.addAttribute("lecturerList", lecturerList);
		return "admin_course_add";
	}
	
	@RequestMapping(value = "/addcourse/save")
	public String saveCourse(@ModelAttribute("course") @Valid Course course, 
		BindingResult bindingResult,  Model model, HttpSession session) 
	{
		if(!authenticateAdmin(session))
			return "redirect:/home";
		
		if (bindingResult.hasErrors()) 
		{
			List<Lecturer> lecturerList = lservice.GetAllLecturers();
			model.addAttribute("lecturerList", lecturerList);
			return "admin_course_add";
		}
		
		cservice.addCourse(course);
		return "redirect:/admin/courselist";
	}
	
	@RequestMapping(value = "/editcourse/save")
	public String saveEditedCourse(@ModelAttribute("course") @Valid Course course, 
			BindingResult bindingResult,  Model model, HttpSession session) 
	{
		if(!authenticateAdmin(session))
			return "redirect:/home";
		
		if (bindingResult.hasErrors()) 
		{
			List<Lecturer> lecturerList = lservice.GetAllLecturers();
			model.addAttribute("lecturerList", lecturerList);
			return "admin_course_edit";
		}
		
		cservice.addCourse(course);
		return "redirect:/admin/courselist";
	}
	
	@RequestMapping(value = "/editcourse/{courseId}")
	public String showCourseEditForm(Model model, @PathVariable("courseId") int courseId, HttpSession session) 
	{
		if(!authenticateAdmin(session))
			return "redirect:/home";

		model.addAttribute("course", cservice.findCourseByCourseId(courseId));
		List<Lecturer> lecturerList = lservice.GetAllLecturers();
		model.addAttribute("lecturerList", lecturerList);
		return "admin_course_edit";
	}
	

	@RequestMapping(value = "/deletecourse/{courseId}")
	public String deleteCourse(@PathVariable("courseId") int courseId, HttpSession session, Model model) 
	{
		if(!authenticateAdmin(session))
			return "redirect:/home";

		List<StudentCourse> studentcourses = st_cs_service.findAllStudentsByCourse(cservice.findCourseByCourseId(courseId).getCourseName());

		for(StudentCourse stucourse : studentcourses) {
			st_cs_service.removeStudentCourse(stucourse);
		}
		
		cservice.deleteCourse(cservice.findCourseByCourseId(courseId));
		return listCourse(model, session, true);
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
