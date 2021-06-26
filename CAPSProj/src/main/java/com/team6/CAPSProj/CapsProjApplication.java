package com.team6.CAPSProj;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.team6.CAPSProj.model.Course;
import com.team6.CAPSProj.model.Faculty;
import com.team6.CAPSProj.model.Lecturer;
import com.team6.CAPSProj.model.Student;
import com.team6.CAPSProj.model.StudentCourse;
import com.team6.CAPSProj.repo.CourseRepository;
import com.team6.CAPSProj.repo.LecturerRepository;
import com.team6.CAPSProj.repo.StudentCourseRepository;
import com.team6.CAPSProj.repo.StudentRepository;
import com.team6.CAPSProj.service.CourseInterface;
import com.team6.CAPSProj.service.CourseServiceImpl;
import com.team6.CAPSProj.service.LecturerInterface;
import com.team6.CAPSProj.service.LecturerServiceImpl;
import com.team6.CAPSProj.service.StudentCourseInterface;
import com.team6.CAPSProj.service.StudentCourseServiceImpl;

@SpringBootApplication
public class CapsProjApplication {

	@Autowired
	StudentRepository srepo;
	@Autowired
	CourseRepository crepo;

	
	@Autowired
	private CourseInterface cservice;
	@Autowired
	public void setCourseService(CourseServiceImpl cserviceImpl) {
		this.cservice = cserviceImpl;
	}
	
	@Autowired
	LecturerRepository lrepo;
	
	@Autowired
	StudentCourseRepository screpo;
	
	@Autowired
	private LecturerInterface lservice;
	@Autowired
	public void setLecturerService(LecturerServiceImpl lserviceImpl)
	{
		this.lservice = lserviceImpl;
	}
	
	@Autowired
	private StudentCourseInterface scservice;
	
	@Autowired
	public void setStudentCourseService(StudentCourseServiceImpl scserviceImpl) {
		this.scservice = scserviceImpl;
	}
	
	public static void main(String[] args) {
		SpringApplication.run(CapsProjApplication.class, args);
		
	}
	public DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	@Bean
	@Transactional
	CommandLineRunner runner() {
		return args ->{
			LocalDate ld = LocalDate.parse("22/05/2021",df);
			Student s1 = new Student("e123456","Wong","Jireh", "jirehWong@gmail.com","e123456@u.nus.edu", "5678", LocalDate.of(2021, 6, 22));
			srepo.save(s1);
			 
			Lecturer l1 = new Lecturer("Francis", "Tan", Faculty.BUSINESS, "francis@gmail.com", null, null);
			Lecturer l2 = new Lecturer("Tin", "Ng", Faculty.COMPUTING, "tin@gmail.com",null, null);
			
			lservice.addLecturer(l1);
			lservice.addLecturer(l2);
			
			Course c1 = new Course("ADProject", "ADProject", Faculty.COMPUTING, 5, ld, 10);
			Course c2 = new Course("SCI101", "Anontomy", Faculty.MEDICINE, 5, ld, 80);
			
			Course c3 = new Course("SA4101", "design", Faculty.COMPUTING, 5,LocalDate.of(2019, 03, 10),l2, 80);
			Course c4 = new Course("SA4105", "SQL", Faculty.COMPUTING, 5,LocalDate.of(2021, 07, 11), 80);
			Course c5 = new Course("SA4108", "Python", Faculty.COMPUTING, 5,LocalDate.of(2020, 07, 11),l2, 80);
			crepo.save(c1);crepo.save(c2);crepo.save(c3);crepo.save(c4);crepo.save(c5);
			
			
			
			l1  = lrepo.findById(1).get();	
			l2 = lrepo.findById(2).get();
			
			
			c1 = cservice.findCourseByCourseName("ADProject");		
			c1.setLecturer(l1);
			cservice.updateCourse(c1);
			
			scservice.addStudentToCourse(c1, s1);
			
			c2 = cservice.findCourseByCourseName("SCI101");		
			c2.setLecturer(l2);
			cservice.updateCourse(c2);
		
			
			c4 = cservice.findCourseByCourseName("SA4105");		
			c4.setLecturer(l2);
			cservice.updateCourse(c4);
			
			StudentCourse sc1 = new StudentCourse(s1, c1, 4.7);
			StudentCourse sc2 = new StudentCourse(s1, c2, 4.3);
			StudentCourse sc3 = new StudentCourse(s1, c3, 3.1);
			StudentCourse sc4 = new StudentCourse(s1, c5, 3.6);
			screpo.save(sc1);
			screpo.save(sc2);
			screpo.save(sc3);
			screpo.save(sc4);
			
			
//			
//			Grade B = Grade.B;
//			
//			StudentCourse sc1 = new StudentCourse(s1, c1, B );
//			screpo.save(sc1);
			
//			Book b1 = new Book("Spring Boot");
//	        Book b2 = new Book("Spring Data JPA");
//	        bookRepository.saveAll(Arrays.asList(b1, b2));
//
//	        Publisher p1 = new Publisher("HelloKoding 1");
//	        Publisher p2 = new Publisher("HelloKoding 2");
//	        publisherRepository.saveAll(Arrays.asList(p1, p2));
//
//	        BookPublisher bp1 = new BookPublisher(b1, p1, new Date());
//	        BookPublisher bp2 = new BookPublisher(b1, p2, new Date());
//	        BookPublisher bp3 = new BookPublisher(b2, p1, new Date());
//	        BookPublisher bp4 = new BookPublisher(b2, p2, new Date());
//	        bookPublisherRepository.saveAll(Arrays.asList(bp1, bp2, bp3, bp4));
			
//			DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//			LocalDate dt = LocalDate.parse("23/05/2021",df);
//			
//			Student s1 = new Student("Tan","Sharon", "sharonTan@gmail.com","e100024@u.nus.edu", "1234", dt);
//			Student s2 = new Student("Lim","Sharon", "sharonLim@gmail.com","e112024@u.nus.edu", "1234", dt);
//			srepo.save(s1); srepo.save(s2);
//			
//			Course c1 = new Course("SA4101", "design", "COM", 5, 80);
//			Course c2 = new Course("SCI101", "Anontomy", "SCI", 5, 80);
//			crepo.save(c1); crepo.save(c2);
//			
//			Grade A = Grade.A;
//			Grade B = Grade.B;
//			
//			StudentCourse sc1 = new StudentCourse(s1, c1, A, LocalDate.of(2021, 6, 22));
//
//			StudentCourse sc2 = new StudentCourse(s2, c2, B, LocalDate.of(2021, 6, 22));
//			
//			screpo.save(sc1);
//			screpo.save(sc2);
		
		//Lecturer l1 = new Lecturer("Tin", "Nguyen", "COM", null, null, null)
		
		};
}
}

