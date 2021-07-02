package com.team6.CAPSProj;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
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
import com.team6.CAPSProj.service.CourseService;
import com.team6.CAPSProj.service.CourseServiceImpl;
import com.team6.CAPSProj.service.LecturerService;
import com.team6.CAPSProj.service.LecturerServiceImpl;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = CapsProjApplication.class)
@TestMethodOrder(OrderAnnotation.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CourseServiceTest {

	@Autowired
	private CourseService cservice;
	
	@Autowired
	private LecturerService lservice;
	
	@Autowired
	public void setCourseService(CourseServiceImpl cserviceImpl) {
		this.cservice = cserviceImpl;
	}

	@Autowired
	private CourseRepository crepo;
	
	@Autowired
	private LecturerRepository lrepo;

	
	public DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	
	@Autowired
	public void setLecturerService(LecturerServiceImpl lserviceImpl)
	{
		this.lservice = lserviceImpl;
	}
	

	@Test
	@Order(1)
	public void testCourseCreation() {
		
	
		LocalDate ld = LocalDate.parse("22/05/2021",df);
		Course c1 = new Course("ADProject", "ADProject", Faculty.COMPUTING, 5, ld, 0);
		Course c2 = new Course("ADProject2", "ADProject2", Faculty.COMPUTING, 5, ld, 0);
		cservice.addCourse(c1);
		cservice.addCourse(c2);
		assertNotNull(cservice.findCourseByCourseName("ADProject"));
	}
	


	@Test
	@Order(2)
	public void testfindAllCourses() {
		List<Course> clist = crepo.findAll();
		List<Integer> list = new ArrayList<Integer>();
		
		for(Course c: clist) {
		   list.add(c.getCourseId());	
		}
		
		List<Course> test = cservice.findAllCourses(list);
		assertTrue(test.size() > 0);

	}
	
	@Test
	@Order(3)
	public void testfindAllCourseforCurrentYear() {	
		List<Course> test = cservice.findAllCourseforCurrentYear();
		assertNotNull(test);
	}
	
	@Test
	@Order(4)
	public void findCourseByCourseName() {	
		String courseName = "ADProject";
		Course c = cservice.findCourseByCourseName(courseName);
		assertNotNull(c);
	}
	@Test
	@Order(5)
	public void testCourseUpdate() {
	Lecturer l1 = new Lecturer("Francis", "Tan", Faculty.BUSINESS, "francis@gmail.com", null, null);
	Lecturer l2 = new Lecturer("Tin", "Ng", Faculty.COMPUTING, "tin@gmail.com", null, null);
	lservice.addLecturer(l1);
	lservice.addLecturer(l2);
		
		l1  = lrepo.findById(1).get();
		
		Course c = cservice.findCourseByCourseName("ADProject");
		
		c.setCourseStartDate(LocalDate.parse("02/03/2021",df));
		c.setCredits(50);
		c.setDescription("testing1");
		c.setFaculty(Faculty.BUSINESS);
		c.setSize(12);
		c.setLecturer(l1);
		cservice.updateCourse(c);
		Course d = cservice.findCourseByCourseName("ADProject");
		assertTrue(c.equals(d));
		
	}


	@Test
	@Order(6)
	public void testCourseDelete() {
		
	   Course c = cservice.findCourseByCourseName("ADProject");
	   cservice.deleteCourse(c);
	   
	   assertNull(cservice.findCourseByCourseName("ADProject"));
	}
	
	@Test
	@Order(7)
	public void testFindCoursesByLecturer() {
		List<Course> clist = cservice.findCoursesByLecturerId(1);
		assertNotNull(clist);
		
	}

	@Test
	@Order(8)
	public void findAllCourseByYear() {	
		
		List<Course> test = cservice.findAllCourseByYear(LocalDate.now().getYear());
		assertNotNull(test);
	}
	
	@Test
	@Order(9)
	public void findAllCourseByYearAndLecturer() {	
		List<Course> test = cservice.findAllCourseByYearAndLecturerId(LocalDate.now().getYear(),1);
		assertNotNull(test);
	}
	
	
	
}
