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
import com.team6.CAPSProj.repo.CourseRepository;
import com.team6.CAPSProj.repo.LecturerRepository;
import com.team6.CAPSProj.service.CourseInterface;
import com.team6.CAPSProj.service.CourseServiceImpl;
import com.team6.CAPSProj.service.LecturerInterface;
import com.team6.CAPSProj.service.LecturerServiceImpl;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = CapsProjApplication.class)
@TestMethodOrder(OrderAnnotation.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CourseTest {

	@Autowired
	private CourseInterface cservice;
	
	@Autowired
	public void setCourseService(CourseServiceImpl cserviceImpl) {
		this.cservice = cserviceImpl;
	}
	@Autowired
	private CourseRepository crepo;

	
	public DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	@Autowired
	private LecturerInterface lservice;
	@Autowired
	private LecturerRepository lrepo;
	@Autowired
	public void setLecturerService(LecturerServiceImpl lserviceImpl)
	{
		this.lservice = lserviceImpl;
	}
	
//	@Test
//	@Order(1)
//	public void testCourseCreation() {
//		
//	
//		LocalDate dt = LocalDate.parse("22/05/2021",df);
//		Course c = new Course("ADProject", "ADProject", Faculty.COMPUTING, 5, dt, 0);
//		cservice.addCourse(c);
//		assertNotNull(crepo.findByCourseName("ADProject"));
//	}
	

//	
//	@Test
//	@Order(2)
//	public void testfindAllCourses() {
//		
//		List<Integer> list = new ArrayList<Integer> (1);
//		List<Course> test = cservice.findAllCourses(list);
//		assertNotNull(test);
//
//	}
//	
//	@Test
//	@Order(3)
//	public void testfindAllCourseforCurrentYear() {	
//		List<Course> test = cservice.findAllCourseforCurrentYear();
//		assertNotNull(test);
//	}
//	
//	@Test
//	@Order(4)
//	public void findCourseByCourseName() {	
//		String courseName = "ADProject";
//		Course c = cservice.findCourseByCourseName(courseName);
//		assertNotNull(c);
//	}

	@Test
	@Order(5)
	public void addLecturer() {
	Lecturer l1 = new Lecturer("Tin", "Ng", Faculty.COMPUTING, "tin@gmail.com");
	lservice.addLecturer(l1);
	}

//	@Test
//	@Order(6)
//	public void findCoursesByLecturerId() {	
//
//	}
//	
//	@Test
//	@Order(7)
//	public void findAllCourseByYear() {	
//		List<Course> test = cservice.findAllCourseByYear(2021);
//		assertNotNull(test);
//	}
//	
}
