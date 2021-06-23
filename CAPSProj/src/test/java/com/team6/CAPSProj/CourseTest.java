package com.team6.CAPSProj;

import static org.junit.jupiter.api.Assertions.assertNotNull;

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
import com.team6.CAPSProj.repo.CourseRepository;
import com.team6.CAPSProj.service.CourseInterface;
import com.team6.CAPSProj.service.CourseServiceImpl;

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
	
	@Test
	@Order(1)
	public void testCourseCreation() {
		
	
		LocalDate dt = LocalDate.parse("22/05/2021",df);
		Course c = new Course("ADProject", "ADProject", Faculty.COMPUTING, 5, dt, 0);
		cservice.addCourse(c);
		assertNotNull(crepo.findByCourseName("ADProject"));
		
	}

	
}
