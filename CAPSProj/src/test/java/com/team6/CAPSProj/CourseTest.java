package com.team6.CAPSProj;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
	private LecturerInterface lservice;
	
	@Autowired
	public void setCourseService(CourseServiceImpl cserviceImpl) {
		this.cservice = cserviceImpl;
	}
	@Autowired
	public void setLecturerService(LecturerServiceImpl lserviceImpl) {
		this.lservice = lserviceImpl;
	}
	@Autowired
	private CourseRepository crepo;
	
	@Autowired
	private LecturerRepository lrepo;

	
	public DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	@Test
	@Order(1)
	public void testCourseCreation() {
		
	
		LocalDate dt = LocalDate.parse("22/05/2021",df);
		Course c = new Course("ADProject", "ADProject", Faculty.COMPUTING, 5, dt, 0);
		cservice.addCourse(c);
		assertNotNull(cservice.findCourseByCourseName("ADProject"));
		
	}
	@Test
	@Order(2)
	public void testCourseUpdate() {
	Lecturer l1 = new Lecturer("Francis", "Tan", Faculty.BUSINESS, "francis@gmail.com", null, null);
	lrepo.save(l1);
		
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
	@Order(3)
	public void testCourseDelete() {
		
	   Course c = cservice.findCourseByCourseName("ADProject");
	   cservice.deleteCourse(c);
	   
	   assertNull(cservice.findCourseByCourseName("ADProject"));
		
	}

	
}
