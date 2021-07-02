package com.team6.CAPSProj;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.team6.CAPSProj.model.Course;
import com.team6.CAPSProj.model.Student;
import com.team6.CAPSProj.model.StudentCourse;
import com.team6.CAPSProj.repo.StudentCourseRepository;
import com.team6.CAPSProj.service.CourseService;
import com.team6.CAPSProj.service.StudentCourseService;
import com.team6.CAPSProj.service.StudentService;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = CapsProjApplication.class)
@TestMethodOrder(OrderAnnotation.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class StudentCourseServiceTest {

	@Autowired
	private StudentCourseRepository screpo;
	
	@Autowired
	private StudentCourseService scservice;
	
	@Autowired
	private StudentService stservice;
	

	@Autowired
	private CourseService cservice;
	
	
	@Test
	@Order(1)
	// Positive Test Case
	public void testAddStudentToCourse() {
		
		Student student1 = stservice.findStudentByStudentId(3);
		Student student2 = stservice.findStudentByStudentId(4);
		Course c1 = cservice.findCourseByCourseId(10);
		
		scservice.addStudentToCourse(c1, student1);
		scservice.addStudentToCourse(c1, student2);
		assertTrue(screpo.findAll().size()==14);
		
	}

	@Test
	@Order(2)
	// Positive Test Case
	public void testFindAllCourseByStudents() {	
		Student s1 = stservice.findStudentByMatricNo("A9996B");
		List<StudentCourse> scList = scservice.findAllCoursesByStudent(s1.getStudentId());
		assertTrue(scList.size() > 0);
	}

	@Test
	@Order(3)
	// Positive Test Case
	public void testFindAllStudentsByCourse() {
		
		List<StudentCourse> scList = scservice.findAllStudentsByCourse("Beginner Pottery");
		assertTrue(scList.size() > 0);
	}
	
	@Test
	@Order(4)
	// Positive Test Case
	public void testFindAllGradesByYearAndStudent() {
		Student s1 = stservice.findStudentByMatricNo("A9996B");
		List<StudentCourse>sclist = scservice.findAllCoursesByStudent(s1.getStudentId());
		List<Course> clist = new ArrayList<Course>();
		for(StudentCourse course: sclist) {
			clist.add(course.getCourse());
		}
		
		assertNotNull(scservice.findAllGradeByYearAndStudent(clist, s1,2021));
		
	}
	@Test
	@Order(5)
	// Positive Test Case
	public void testUpdateStudentGrade() {

		List<StudentCourse> scList = scservice.findAllStudentsByCourse("Beginner Pottery");
			for (StudentCourse sc: scList) {
				sc.setGrade(4.7);
			}
			
		scservice.updateStudentGrade(scList);
		
		StudentCourse retrieveSC = scservice.findAllStudentsByCourse("Beginner Pottery").get(0);
		assertTrue(retrieveSC.getGrade().equals(4.7));
	
	}
	
	@Test
	@Order(6)
	// Positive Test Case
	public void testCountTotalStudentEnrol() {
		
		Course c = cservice.findCourseByCourseName("Beginner Pottery");
	
		assertTrue( scservice.CountTotalStudentEnrol(c.getCourseId())== 4);
	}
	
	@Test
	@Order(7)
	// Positive Test Case
	public void testRemoveStudentCourse() {
			
		StudentCourse studentcourse = scservice.findAllStudentsByCourse("Beginner Pottery").get(0);
		
		scservice.removeStudentCourse(studentcourse);
		
		assertTrue(scservice.findAllStudentsByCourse("Beginner Pottery").size()== 3);
	}
	
	@Test
	@Order(8)
	// Positive Test Case
	public void testFindAllGradeByStudent() {
		
		
		Student s1 = stservice.findStudentByMatricNo("A9996B");
		List<StudentCourse> sclist = scservice.findAllCoursesByStudent(s1.getStudentId());
		
		List<Course> clist =  new ArrayList<Course>();
		for(StudentCourse sc: sclist) {
			clist.add(sc.getCourse());
		}
		
		List<StudentCourse> sc = scservice.findAllGradeByStudent(clist, s1);
	
		assertNotNull(sc);
	}
	
	@Test
	@Order(9)
	// Positive Test Case
	public void testFindGradeByStudentAndCourse() {
		
		Student s1 = stservice.findStudentByMatricNo("A9996B");
		Course c1 = cservice.findCourseByCourseName("Beginner Pottery");
		assertNotNull(scservice.findGradeByStudentAndCourse(s1, c1).getGrade());
		
	}
	
	@Test
	@Order(10)
	// Positive Test Case
	public void testFindAllGradedCoursesByStudent() {
		Student s1 = stservice.findStudentByMatricNo("A9996B");
		List<StudentCourse>sclist = scservice.findAllGradedCoursesByStudent(s1.getStudentId());
		for(StudentCourse sc : sclist) {
			 assertNotNull(sc.getGrade());
		}
		  
	}
	
	@Test
	@Order(11)
	// Negative Test Case - to test whether the student still can be assign to the course that is full
	public void testAdminAddStudentToCourse() {
		
		Course c = cservice.findCourseByCourseName("Beginner Pottery");
		Student s1 = stservice.findStudentByMatricNo("A9994B");
		Student s2 = stservice.findStudentByMatricNo("A9993B");
		Student s3 = stservice.findStudentByMatricNo("A9992B");
		scservice.adminAddStudentToCourse(c, s1);
		scservice.adminAddStudentToCourse(c, s2);
		assertTrue(!scservice.adminAddStudentToCourse(c, s3));
	}
	

	
	
}
