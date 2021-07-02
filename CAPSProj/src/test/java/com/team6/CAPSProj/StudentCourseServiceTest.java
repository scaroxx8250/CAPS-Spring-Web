package com.team6.CAPSProj;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import com.team6.CAPSProj.model.Faculty;
import com.team6.CAPSProj.model.Lecturer;
import com.team6.CAPSProj.model.Student;
import com.team6.CAPSProj.model.StudentCourse;
import com.team6.CAPSProj.repo.CourseRepository;
import com.team6.CAPSProj.repo.LecturerRepository;
import com.team6.CAPSProj.repo.StudentCourseRepository;
import com.team6.CAPSProj.service.CourseService;
import com.team6.CAPSProj.service.CourseServiceImpl;
import com.team6.CAPSProj.service.LecturerService;
import com.team6.CAPSProj.service.LecturerServiceImpl;
import com.team6.CAPSProj.service.StudentCourseService;
import com.team6.CAPSProj.service.StudentCourseServiceImpl;
import com.team6.CAPSProj.service.StudentServiceImpl;
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
	public void setStudentCourseService(StudentCourseServiceImpl scserviceImpl) {
		this.scservice = scserviceImpl;
	}
	
	@Autowired
	private StudentService stservice;
	
	@Autowired
	public void setStudentService(StudentServiceImpl stserviceImpl) {
		this.stservice = stserviceImpl;
	}
	@Autowired
	private CourseService cservice;
	
	@Autowired
	private LecturerRepository lrepo;
	@Autowired
	private LecturerService lservice;
	
	@Autowired
	public void setCourseService(CourseServiceImpl cserviceImpl) {
		this.cservice = cserviceImpl;
	}

	@Autowired
	private CourseRepository crepo;
	
	public DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	
	@Autowired
	public void setLecturerService(LecturerServiceImpl lserviceImpl)
	{
		this.lservice = lserviceImpl;
	}
	
//	@Test
//	@Order(1)
//	public void testAddStudentToCourse() {
//		Student student1 = new Student("A2345B","Kevin", "Lin", "kevin@mail.com","1233@nus.edu.sg", "pw123", LocalDate.of(2021, 07, 22));
//		stservice.addStudent(student1);
//		Student student2 = new Student("A2347B","Linda", "Cheng", "linda@mail.com","1235@nus.edu.sg", "pw123", LocalDate.of(2021, 07, 24));
//		stservice.addStudent(student2);
//		Lecturer l1 = new Lecturer("Francis", "Tan", Faculty.BUSINESS, "francis@gmail.com", null, null);
//		lservice.addLecturer(l1);
//		LocalDate ld = LocalDate.parse("22/05/2021",df);
//		Course c1 = new Course("ADProject", "ADProject", Faculty.BUSINESS, 5, ld, l1, 10);
//		cservice.addCourse(c1);
//
//		scservice.addStudentToCourse(c1, student1);
//		scservice.addStudentToCourse(c1, student2);
//		
//		assertTrue(screpo.findAll().size()==2);
//		
//	}
//	
//	
//	@Test
//	@Order(2)
//	public void testFindAllCourseByStudents() {	
//		Student s1 = stservice.findStudentByMatricNo("A2345B");
//		List<StudentCourse> scList = scservice.findAllCoursesByStudent(s1.getStudentId());
//		assertTrue(scList.size() > 0);
//	}
//	
//	@Test
//	@Order(3)
//	public void testFindAllStudentsByCourse() {
//		Course c1 = cservice.findCourseByCourseName("ADProject");
//		
//		List<StudentCourse> scList = screpo.findAllStudentsByCourse(c1);
//		assertTrue(scList.size() > 0);
//	}
//	
//	@Test
//	@Order(4)
//	public void testFindAllGradesByYearAndStudent() {
//		
//		
//		
//		Student s1 = stservice.findStudentByMatricNo("A2345B");
//		List<StudentCourse>sclist = scservice.findAllCoursesByStudent(s1.getStudentId());
//		List<Course> clist = new ArrayList<Course>();
//		for(StudentCourse course: sclist) {
//			clist.add(course.getCourse());
//		}
//		
//		assertNotNull(scservice.findAllGradeByYearAndStudent(clist, s1,2021));
//		
//	}
//	@Test
//	@Order(5)
//	public void testUpdateStudentGrade() {
//		Lecturer l1 = lrepo.findByLecturerId(1);
//		List<Course> courseList = crepo.findCourseByLecturer(l1.getLecturerId());
//		List<StudentCourse>scList = null;
//		for(Course course : courseList) {
//			scList = scservice.findAllStudentsByCourse(course.getCourseName());
//			for (StudentCourse sc: scList) {
//				sc.setGrade(4.7);
//			}
//			
//		}	
//		scservice.updateStudentGrade(scList);
//		
//		List<StudentCourse>retrieveList = screpo.findAll();
//		assertTrue(scList.equals(retrieveList));
//	
//	}
//	
//	@Test
//	@Order(6)
//	public void testCountTotalStudentEnrol() {
//		
//		Course c = cservice.findCourseByCourseName("ADProject");
//	
//		assertTrue( scservice.CountTotalStudentEnrol(c.getCourseId())== 2);
//	}
	
	@Test
	@Order(7)
	public void testRemoveStudentCourse() {
		Student student3 = new Student("A2345B","Kevin", "Lin", "kevin@mail.com","1233@nus.edu.sg", "pw123", LocalDate.of(2021, 07, 22));
		stservice.addStudent(student3);
		Lecturer l2 = new Lecturer("Francis2", "Tan2", Faculty.BUSINESS, "francis@gmail.com", null, null);
		lservice.addLecturer(l2);
		Course c3 = new Course("ADProject2", "ADProject", Faculty.BUSINESS, 5, LocalDate.of(2021, 07, 24), l2, 10);
		cservice.addCourse(c3);
		
		scservice.addStudentToCourse(c3, student3);
		
		StudentCourse studentcourse = scservice.findAllStudentsByCourse("ADProject2").get(0);
		
		scservice.removeStudentCourse(studentcourse);
		
		assertTrue(scservice.findAllStudentsByCourse("ADProject2").isEmpty());
	}
	
//	@Test
//	@Order(7)
//	public void testFindStudentCourseByCourse() {
//		
//		Course c = cservice.findCourseByCourseName("ADProject");
//		StudentCourse sc = scservice.findByCourse(c);
//	
//		assertNotNull(sc);
//	}
	
//	@Test
//	@Order(8)
//	public void testRemoveStudentFromCourse() {
//		Course c = cservice.findCourseByCourseName("ADProject");
//		Student s1 = stservice.findStudentByMatricNo("A2345B");
//		
//		scservice.removeStudentFromCourse(c, s1);
//		assertTrue(screpo.findAll().size()==1);
//	}
//	
	
	
}
