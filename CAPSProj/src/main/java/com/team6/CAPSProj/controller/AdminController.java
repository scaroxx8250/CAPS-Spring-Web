package com.team6.CAPSProj.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;

import com.team6.CAPSProj.model.Admin;
import com.team6.CAPSProj.model.Course;
import com.team6.CAPSProj.model.Faculty;
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

	public String listStudent(Model model, HttpSession session, Boolean delete_student_status) {
		Admin ad = (Admin) session.getAttribute("usession");
		if (ad == null)
		{
			return "redirect:/home";
		}
		else 
		{
			model.addAttribute("studentlist", stservice.findAllStudents());
			
			if(delete_student_status != null)
				model.addAttribute("status", delete_student_status);
			
			return "admin_student_manage";
		}
	}
	
	@RequestMapping(value = "/addstudent")
	public String addStudent(Model model , HttpSession session) {
		Admin ad = (Admin) session.getAttribute("usession");
		if (ad == null)
		{
			return "redirect:/home";
		}
		else {
		model.addAttribute("student", new Student());
		return "admin_student_add";
		}
	}
	
	@RequestMapping(value = "/addstudent/save")
	public String saveStudent(@ModelAttribute("student") @Valid Student student, 
			BindingResult bindingResult,  Model model, HttpSession session) {
		Admin ad = (Admin) session.getAttribute("usession");
		if (ad == null)
		{
			return "redirect:/home";
		}
		else {
		if (bindingResult.hasErrors()) {
			return "admin_student_add";
		}
		stservice.addStudent(student);
		return "forward:/admin/studentlist";
		}
	}
	
	@RequestMapping(value = "/editstudent/save")
	public String saveEditedStudent(@ModelAttribute("student") @Valid Student student, 
			BindingResult bindingResult,  Model model) {
		if (bindingResult.hasErrors()) {
			return "admin_student_edit";
		}
		stservice.addStudent(student);
		return "forward:/admin/studentlist";
	}
	
	
	
	
	@RequestMapping(value = "/editstudent/{matricNo}")
	public String showEditForm(Model model, @PathVariable("matricNo") String matricNo, HttpSession session) {
		Admin ad = (Admin) session.getAttribute("usession");
		if (ad == null)
		{
			return "redirect:/home";
		}
		else {
		model.addAttribute("student", stservice.findStudentByMatricNo(matricNo));
		return "admin_student_edit";
		}
	}
	
	@RequestMapping(value = "/deletestudent/{matricNo}")
	public String deleteStudent(@PathVariable("matricNo") String matricNo, HttpSession session, Model model) {
		Admin ad = (Admin) session.getAttribute("usession");
		if (ad == null)
		{
			return "redirect:/home";
		}
		else {
		//first we have to remove the student from all assigned courses
		//find student
		Student student = stservice.findStudentByMatricNo(matricNo);
		//get list of studentcourses
		List<StudentCourse> studentcourses = st_cs_service.findAllCoursesByStudent(student.getStudentId());
		//get list of courses
//		List<Course> courseList = new ArrayList<Course>();
// 		for(StudentCourse stcourse : studentcourses) {
//			courseList.add(stcourse.getCourse());
//		}
 		
 		for(StudentCourse sc: studentcourses)
 		{
 			st_cs_service.removeStudentFromCourse(sc.getCourse(), sc.getStudent());
 		}
 		
		//remove student from course
// 		for(Course course : courseList) {
// 			st_cs_service.removeStudentFromCourse(course, student);
// 		}
 		
 		//remove student
		stservice.deleteStudent(student);
		return listStudent(model, session, true);
		}
	}
	
	
	
	@RequestMapping(value = "/lecturerlist")
	public String listLecturer(Model model, HttpSession session, Boolean delete_lecturer_status) {
		Admin ad = (Admin) session.getAttribute("usession");
		if (ad == null)
		{
			return "redirect:/home";
		}
		else
		{
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
	}
	
	@RequestMapping(value = "/addlecturer")
	public String addLecturer(Model model, HttpSession session) {
		Admin ad = (Admin) session.getAttribute("usession");
		if (ad == null)
		{
			return "redirect:/home";
		}
		else {
		model.addAttribute("lecturer", new Lecturer());
		return "admin_lecturer_add";
		}
	}

	@RequestMapping(value = "/addlecturer/save")
	public String saveLecturer(@ModelAttribute("lecturer") @Valid Lecturer lecturer, 
			BindingResult bindingResult,  Model model, HttpSession session) {
		Admin ad = (Admin) session.getAttribute("usession");
		if (ad == null)
		{
			return "redirect:/home";
		}
		else {
		if (bindingResult.hasErrors()) {
			return "admin_lecturer_add";
		}
		lservice.addLecturer(lecturer);
		return "forward:/admin/lecturerlist";
		}
	}
	
	@RequestMapping(value = "/editlecturer/save")
	public String saveEditedLecturer(@ModelAttribute("lecturer") @Valid Lecturer lecturer, 
			BindingResult bindingResult,  Model model) {
		if (bindingResult.hasErrors()) {
			return "admin_lecturer_edit";
		}
		lservice.addLecturer(lecturer);
		return "forward:/admin/lecturerlist";
	}
	
	

	@RequestMapping(value = "/editLecturer/{lecturerId}")
	public String editLecturer(Model model, @PathVariable("lecturerId") int lecturerId, HttpSession session) {
		Admin ad = (Admin) session.getAttribute("usession");
		if (ad == null)
		{
			return "redirect:/home";
		}
		else {
		model.addAttribute("lecturer", lservice.findLecturerById(lecturerId));
		return "admin_lecturer_edit";
		}
	}
	
	@RequestMapping(value = "/deletelecturer/{lecturerId}")
	public String deleteLecturer(@PathVariable("lecturerId") Integer lecturerId,  HttpSession session, Model model) {
		Admin ad = (Admin) session.getAttribute("usession");
		if (ad == null)
		{
			return "redirect:/home";
		}
		else 
		{
			List <Course> course_delete = cservice.findCoursesByLecturerId(lecturerId);
			// Can only delete a lecturer when he has no more courses assigned to him
			if (course_delete.size() == 0)
			{
				lservice.deleteLecturer(lservice.findLecturerById(lecturerId));
				// true means delete is successful
				return listLecturer(model, session, true);
			}
			else // There are still courses assigned to this lecturer, cannot delete lecturer
			{
				// false means delete is unsuccessful
				return listLecturer(model, session, false);
			}
		}
	}
	
	
	@RequestMapping(value = "/enrolmentlist")
	public String listEnrolment(Model model, Integer id, Boolean enrol_student_course_status)
	{
		//List<Course> courselist = cservice.getAllCourses();
		List<Course> courselist = cservice.findAllCourseforCurrentYear();
		model.addAttribute("courses", courselist);
		
		Course course1;
		
		if(id == null)
		{
			course1 = cservice.findAllCourseByYear(LocalDate.now().getYear()).get(0);
		}
		else
		{
			course1 = cservice.findAllCourseByYear(LocalDate.now().getYear()).get(id);
		}
		
		List<StudentCourse> sclist = st_cs_service.findAllStudentsByCourse(course1.getCourseName());
//		model.addAttribute("student_course", sclist);
		
		List<Student> students = new ArrayList<Student>();
		
		for (StudentCourse sc: sclist)
		{
			students.add(sc.getStudent());
		}
		
//		Student teststudent = stservice.findStudentByStudentId(1);
//		students.add(teststudent);
		
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
		
		if(enrol_student_course_status != null)
		{
			model.addAttribute("status", enrol_student_course_status);
		}
		
		return "admin_enrolment_manage";
	}
	
	@RequestMapping(value = "/enrolmentlist/{id}")
	public String listEnrolment(@PathVariable("id") Integer id, Model model)
	{	
		return listEnrolment(model, id, null);
	}
	
	@RequestMapping(value = "/enrolmentlist/{id}/{statusId}")
	public String listEnrolment(@PathVariable("id") Integer id, Model model, @PathVariable("statusId") Integer statusId)
	{	
		if(statusId == 1)
			return listEnrolment(model, id, true);
		else
			return listEnrolment(model, id, false);
	}
	
//	@GetMapping("/addenrollment/{coursename}")
//	public String listEnrollment(Model model, @PathVariable("coursename") String coursename) {
//		
//		return addEnrollment(1, model, coursename);
//	}
	
	

	
	@RequestMapping(value = "/addenrolment/{id}/{pageNo}")
	public String addEnrolment(@PathVariable(value = "pageNo") int pageNo, Model model, @PathVariable("id") int id) {
		
		//get course
		Course course = cservice.findAllCourseByYear(LocalDate.now().getYear()).get(id);
		
//		List<StudentCourse> stlist = st_cs_service.findAllStudentsByCourse(coursename);
//		
//		Student onestudent= stservice.findAllStudents().get(0);
//		model.addAttribute("student", onestudent);
//
//		Student s8 = new Student("e123456","Wong","Jireh", "jirehWong@gmail.com","e123456@u.nus.edu", "5678", LocalDate.of(2021, 6, 22));
//	
//		Course c8 = new Course("ADProject", "ADProject", Faculty.COMPUTING, 5, LocalDate.of(2021, 07, 11), 2);
//		StudentCourse sc = new StudentCourse(c8, s8);
//		model.addAttribute("studentcourse", sc);
		
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
	public String enrollStudent(@PathVariable("matricNo") String matricNo, @PathVariable("coursename") String coursename, @PathVariable("id") int id, Model model) {
	
		//get Student
		Student student = stservice.findStudentByMatricNo(matricNo);
		//get course
		Course course = cservice.findCourseByCourseName(coursename);
		//do assignment
		boolean status = st_cs_service.adminAddStudentToCourse(course, student);
		if(status) // status == true means student has been added to course
		{
			return "redirect:/admin/enrolmentlist/{id}/1";
		}
		else // status == false means student could not be added to course
		{
			return "redirect:/admin/enrolmentlist/{id}/0";
		}
		
	}

//	@RequestMapping(value = "/enrolstudent")
//	public String enrolStudent(@ModelAttribute("studentcourse") @Valid StudentCourse studentcourse, 
//			BindingResult bindingResult,  Model model) {
//		
//		if (bindingResult.hasErrors()) {
//			return "admin_enrollment_add";
//		}
//		System.out.print(studentcourse);
//		st_cs_service.addStudentToCourse(studentcourse.getCourse(), studentcourse.getStudent());
//		return "redirect:/admin/enrolmentlist";
//	}
//	
	
	
	
	
	
	
	@RequestMapping(value = "/courselist")
	public String listCourse(Model model, HttpSession session, Boolean delete_course_status) {
		Admin ad = (Admin) session.getAttribute("usession");
		if (ad == null)
		{
			return "redirect:/home";
		}
		else 
		{
			List<Course> courses = cservice.findAllCourseforCurrentYear();
			model.addAttribute("courses", courses);
			if(delete_course_status != null)
				model.addAttribute("status", delete_course_status);
			
			return "admin_course_manage";
		}
	}
	
	
	@RequestMapping(value = "/addcourse")
	public String addCourse(Model model, HttpSession session) {
		Admin ad = (Admin) session.getAttribute("usession");
		if (ad == null)
		{
			return "redirect:/home";
		}
		else {
		model.addAttribute("course", new Course());
		List<Lecturer> lecturerList = lservice.GetAllLecturers();
		model.addAttribute("lecturerList", lecturerList);
		return "admin_course_add";
		}
	}
	
	
	@RequestMapping(value = "/addcourse/save")
	public String saveCourse(@ModelAttribute("course") @Valid Course course, 
		BindingResult bindingResult,  Model model, HttpSession session) {
		Admin ad = (Admin) session.getAttribute("usession");
		if (ad == null)
		{
			return "redirect:/home";
		}
		else {
		if (bindingResult.hasErrors()) {
			List<Lecturer> lecturerList = lservice.GetAllLecturers();
			model.addAttribute("lecturerList", lecturerList);
			return "admin_course_add";
		}
		cservice.addCourse(course);
		return "redirect:/admin/courselist";
		}
	}
	
	@RequestMapping(value = "/editcourse/save")
	public String saveEditedCourse(@ModelAttribute("course") @Valid Course course, 
			BindingResult bindingResult,  Model model) {
		
		if (bindingResult.hasErrors()) {
			List<Lecturer> lecturerList = lservice.GetAllLecturers();
			model.addAttribute("lecturerList", lecturerList);
			return "admin_course_edit";
		}
		cservice.addCourse(course);
		return "redirect:/admin/courselist";
	}
	
	

	@RequestMapping(value = "/editcourse/{courseId}")
	public String showCourseEditForm(Model model, @PathVariable("courseId") int courseId, HttpSession session) {
		Admin ad = (Admin) session.getAttribute("usession");
		if (ad == null)
		{
			return "redirect:/home";
		}
		else {
		model.addAttribute("course", cservice.findCourseByCourseId(courseId));
		List<Lecturer> lecturerList = lservice.GetAllLecturers();
		model.addAttribute("lecturerList", lecturerList);
		return "admin_course_edit";
		}
	}
	

	@RequestMapping(value = "/deletecourse/{courseId}")
	public String deleteCourse(@PathVariable("courseId") int courseId, HttpSession session, Model model) {
	
		Admin ad = (Admin) session.getAttribute("usession");
		if (ad == null)
		{
			return "redirect:/home";
		}
		else {
		List<StudentCourse> studentcourses = st_cs_service.findAllStudentsByCourse(cservice.findCourseByCourseId(courseId).getCourseName());

		for(StudentCourse stucourse : studentcourses) {
			st_cs_service.removeStudentCourse(stucourse);
		}
		
		cservice.deleteCourse(cservice.findCourseByCourseId(courseId));
		return listCourse(model, session, true);
		}
	}
	
}
