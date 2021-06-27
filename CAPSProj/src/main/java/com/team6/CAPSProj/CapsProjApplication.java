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
			Student s2 = new Student("e100001","Tan","Sharon", "sharonTan@gmail.com","e100024@u.nus.edu", "1234", LocalDate.of(2021, 6, 22));
			Student s3 = new Student("e100002","Lim","Sharon", "sharonLim@gmail.com","e112024@u.nus.edu", "1234", LocalDate.of(2021, 6, 22));
			srepo.save(s1);srepo.save(s2);srepo.save(s3);
			 
			Lecturer l1 = new Lecturer("Francis", "Tan", Faculty.BUSINESS, "francis@gmail.com", null, null);
			Lecturer l2 = new Lecturer("Tin", "Ng", Faculty.COMPUTING, "tin@gmail.com",null, null);
			
			lservice.addLecturer(l1);
			lservice.addLecturer(l2);
			
			Course c1 = new Course("ADProject", "ADProject", Faculty.COMPUTING, 5, ld, 2);
			Course c2 = new Course("SCI101", "Anontomy", Faculty.MEDICINE, 5, ld, 2);
			
			Course c3 = new Course("SA4101", "design", Faculty.COMPUTING, 5,LocalDate.of(2019, 03, 10),l2, 2);
			Course c4 = new Course("SA4105", "SQL", Faculty.COMPUTING, 5,LocalDate.of(2021, 07, 11), 2);
			Course c5 = new Course("SA4108", "Python", Faculty.COMPUTING, 5,LocalDate.of(2020, 07, 11),l2, 2);
			Course c6 = new Course("SA4106", "Java", Faculty.COMPUTING, 5,LocalDate.of(2019, 03, 10),l2, 2);
			
			// Student 1 enrolled courses (total 6)
			Course c7 = new Course("SA4107", "C#", Faculty.COMPUTING, 5,LocalDate.of(2021, 07, 11), l2, 2);
			Course c8 = new Course("SA4102", "OOP", Faculty.COMPUTING, 5,LocalDate.of(2021, 07, 11),l2, 2);
			Course c9 = new Course("SA4103", "ORM", Faculty.COMPUTING, 5,LocalDate.of(2021, 03, 10),l2, 2);
			Course c10 = new Course("SA4104", "Machine Learning", Faculty.COMPUTING, 5,LocalDate.of(2021, 07, 11), l2, 2);
			Course c11 = new Course("SA4109", "Fundamentals", Faculty.COMPUTING, 5,LocalDate.of(2021, 07, 11),l2, 2);
			Course c12 = new Course("SA4110", "JPQL", Faculty.COMPUTING, 5,LocalDate.of(2021, 07, 11),l2, 2);
			
			// Student 1 not enrolled courses (total 6)
			Course c13 = new Course("SA4111", "JPA", Faculty.COMPUTING, 5,LocalDate.of(2021, 07, 11),l2, 2);
			Course c14 = new Course("SA4112", "Composition", Faculty.COMPUTING, 5,LocalDate.of(2021, 07, 11),l2, 2);
			Course c15 = new Course("SA4113", "Inheritance", Faculty.COMPUTING, 5,LocalDate.of(2021, 07, 11),l2, 2);
			Course c16 = new Course("SA4114", "Polymorphism", Faculty.COMPUTING, 5,LocalDate.of(2021, 07, 11),l2, 2);
			Course c17 = new Course("SA4115", "Abstract Classes and Interfaces", Faculty.COMPUTING, 5,LocalDate.of(2021, 07, 11),l2, 2);
			Course c18 = new Course("SA4116", "Functional Programming", Faculty.COMPUTING, 5,LocalDate.of(2021, 07, 11),l2, 2);
			
			crepo.save(c1);crepo.save(c2);crepo.save(c3);crepo.save(c4);crepo.save(c5);
			crepo.save(c6);crepo.save(c7);crepo.save(c8);crepo.save(c9);crepo.save(c10);
			crepo.save(c11);crepo.save(c12);crepo.save(c13);crepo.save(c14);crepo.save(c15);
			crepo.save(c16);crepo.save(c17);crepo.save(c18);
			
			
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
			StudentCourse sc2 = new StudentCourse(s1, c2, 3.8);
			StudentCourse sc3 = new StudentCourse(s1, c3, 3.1);
			StudentCourse sc4 = new StudentCourse(s1, c5, 3.6);
			StudentCourse sc5 = new StudentCourse(s2, c4,null);
			StudentCourse sc6 = new StudentCourse(s3, c4,null);
			StudentCourse sc7 = new StudentCourse(s1, c6,null);
			StudentCourse sc8 = new StudentCourse(s1, c7,null);
			StudentCourse sc9 = new StudentCourse(s1, c8,null);
			StudentCourse sc10 = new StudentCourse(s1, c9,null);
			StudentCourse sc11 = new StudentCourse(s1, c10,null);
			StudentCourse sc12 = new StudentCourse(s1, c11,null);
			StudentCourse sc13 = new StudentCourse(s1, c12,null);
			
			
			StudentCourse sc14 = new StudentCourse(s2, c2,null);
			
			// Whenever you add a studentCourse, do not add directly using screpo.save()
			// Add through addStudentToCourse()
			
			scservice.addStudentToCourse(c1, s1);
			scservice.addStudentToCourse(c2, s1);
			scservice.addStudentToCourse(c3, s1);
			scservice.addStudentToCourse(c5, s1);
			scservice.addStudentToCourse(c4, s2);
			scservice.addStudentToCourse(c4, s3);
			
			scservice.addStudentToCourse(c6, s1);
			scservice.addStudentToCourse(c7, s1);
			scservice.addStudentToCourse(c8, s1);
			scservice.addStudentToCourse(c9, s1);
			scservice.addStudentToCourse(c10, s1);
			scservice.addStudentToCourse(c11, s1);
			scservice.addStudentToCourse(c12, s1);
			
//			screpo.save(sc1);
			screpo.save(sc2);screpo.save(sc14);
//			screpo.save(sc3);
//			screpo.save(sc4);
//			screpo.save(sc5);
//			screpo.save(sc6);
//			
//			screpo.save(sc7);
//			screpo.save(sc8);
//			screpo.save(sc9);
//			screpo.save(sc10);
//			screpo.save(sc11);
//			screpo.save(sc12);
//			screpo.save(sc13);
			
//			scservice.addStudentToCourse(c13, s3);
//			scservice.addStudentToCourse(c13, s2);
//			scservice.addStudentToCourse(c13, s1);
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

