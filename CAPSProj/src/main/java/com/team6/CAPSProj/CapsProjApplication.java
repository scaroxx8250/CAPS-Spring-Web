package com.team6.CAPSProj;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.team6.CAPSProj.model.Admin;
import com.team6.CAPSProj.model.Course;
import com.team6.CAPSProj.model.Faculty;
import com.team6.CAPSProj.model.Lecturer;
import com.team6.CAPSProj.model.Student;
import com.team6.CAPSProj.model.StudentCourse;
import com.team6.CAPSProj.repo.AdminRepository;
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
	AdminRepository adrepo;
	
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

	public DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
  
	@Bean
	@Transactional
	CommandLineRunner runner() {
		return args ->{
			LocalDate ld = LocalDate.parse("2021-05-22",df);

			// Test Administrators 
			Admin ad1 = new Admin("admin","admin","admin@gmail.com","admin");
			Admin ad2 = new Admin("Dan","Harmon","dan.harmon@u.nus.edu","1234");
			adrepo.save(ad1);
			adrepo.save(ad2);

			// Test Students
			Student s1 = new Student("A9998B","Alex","Osbourne", "ao@gmail.com","47319@u.nus.edu", "1234", LocalDate.of(2021, 6, 22));
			Student s2 = new Student("A9997B","Leonard","Rodriguez", "lr@gmail.com","24634@u.nus.edu", "5678", LocalDate.of(2021, 6, 22));
			Student s3 = new Student("A9996B","Vaughn","Miller", "vm@gmail.com","434eb@u.nus.edu", "1234", LocalDate.of(2021, 6, 22));
			Student s4 = new Student("A9995B","Rich","Stephenson", "rs@gmail.com","bc8c5@u.nus.edu", "5678", LocalDate.of(2021, 6, 22));
			Student s5 = new Student("A9994B","Garett","Lambert", "gl@gmail.com","4213c@u.nus.edu", "1234", LocalDate.of(2021, 6, 22));
			Student s6 = new Student("A9993B","Todd","Jacobson", "tj@gmail.com","96bb9@u.nus.edu", "1234", LocalDate.of(2021, 6, 22));
			Student s7 = new Student("A9992B","Wong","Jireh", "jirehWong@gmail.com","42319@u.nus.edu", "5678", LocalDate.of(2021, 6, 22));
			Student s8 = new Student("A9991B","Tan","Sharon", "sharonTan@gmail.com","4e319@u.nus.edu", "1234", LocalDate.of(2021, 6, 22));
			Student s9 = new Student("A9990B","Lim","Sharon", "sharonLim@gmail.com","3c31b@u.nus.edu", "1234", LocalDate.of(2021, 6, 22));
			Student s10 = new Student("A9989B","Huang","Grace", "gracehuang@gmail.com","e13y6@u.nus.edu", "5678", LocalDate.of(2021, 6, 22));
			Student s11 = new Student("A9988B","koch","Stephan", "stephanKoch@gmail.com","12h43@u.nus.edu", "678", LocalDate.of(2021, 6, 22));
			Student s12 = new Student("A9987B","Luo","ChengHao", "sharonLim@gmail.com","93u65@u.nus.edu", "678", LocalDate.of(2021, 6, 22));
			
			srepo.save(s1);srepo.save(s2);srepo.save(s3);srepo.save(s4);srepo.save(s5);srepo.save(s6);
			srepo.save(s7);srepo.save(s8);srepo.save(s9);srepo.save(s10);srepo.save(s11);srepo.save(s12);
			 
			// Test Lecturers
			Lecturer l1 = new Lecturer("Benjamin", "Franklin", Faculty.BUSINESS, "benjfr@gmail.com", "benjamin.franklin@u.nus.edu", "1234");
			Lecturer l2 = new Lecturer("Buzz", "Hickey", Faculty.COMPUTING, "buzhey@gmail.com","buzz.hickey@u.nus.edu", "5678");
			Lecturer l3 = new Lecturer("Craig", "Pelton", Faculty.ENGINEERING, "craigpel@gmail.com","craig.pelton@u.nus.edu", "1234");
			Lecturer l4 = new Lecturer("Ian", "Duncon", Faculty.LAW, "ian.d@gmail.com","ian.duncon@u.nus.edu", "1234");
			Lecturer l5 = new Lecturer("Michelle", "Slator", Faculty.MEDICINE, "mslator@gmail.com","michelle.slator@u.nus.edu", "1234");

			lservice.addLecturer(l1);
			lservice.addLecturer(l2);
			lservice.addLecturer(l3);
			lservice.addLecturer(l4);
			lservice.addLecturer(l5);

			// Test Courses
			Course c1 = new Course("Advanced Criminal Law", "The body of law that relates to crime", Faculty.LAW, 5,LocalDate.of(2021,8,22),l4, 40);
			Course c2 = new Course("Beginner Pottery", "The ulitimate pottery lesson for doctors.", Faculty.MEDICINE, 5, LocalDate.of(2021,8,22),l5, 20);
			
			Course c3 = new Course("Communication Studies", "Learn how to influence people with ease.", Faculty.BUSINESS, 5,LocalDate.of(2019, 3, 10),l1, 60);
			Course c4 = new Course("Debate 109", "Learn how to debate!", Faculty.LAW, 5,LocalDate.of(2021, 8, 11), l4, 40);
			Course c5 = new Course("English as a Second Language", "English for non-native speakers.", Faculty.BUSINESS, 5,LocalDate.of(2020, 8, 27),l1, 20);
			Course c6 = new Course("Environmental Science", "Integrates physical, biological and information sciences.", Faculty.COMPUTING, 5,LocalDate.of(2021, 8, 27),l5, 80);
			Course c7 = new Course("Football, Feminism and You", "Learn about equal rights and opportunities", Faculty.COMPUTING, 5,LocalDate.of(2021, 8, 27), l4, 60);
			Course c8 = new Course("Interpretive Dance", "Dance, dance, dance!", Faculty.BUSINESS, 5,LocalDate.of(2021, 8, 27),l3, 10);
			Course c9 = new Course("Modern Warfare", "New Military concepts, methods, and technology", Faculty.LAW, 5,LocalDate.of(2021, 8, 27),l2, 60);
			Course c10 = new Course("Pascal's Triangle Revisited", "The infinite triangular arrangement of numbers.", Faculty.ENGINEERING, 5,LocalDate.of(2021, 8, 27), l3, 40);
			
			crepo.save(c1);crepo.save(c2);crepo.save(c3);crepo.save(c4);crepo.save(c5);
			crepo.save(c6);crepo.save(c7);crepo.save(c8);crepo.save(c9);crepo.save(c10);

			
			
			// Test Student Course Assignments
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
			
			
//			Course c11 = new Course("SA4109", "Fundamentals", Faculty.COMPUTING, 5,LocalDate.of(2021, 07, 11),l2, 2);
//			Course c12 = new Course("SA4110", "JPQL", Faculty.COMPUTING, 5,LocalDate.of(2021, 07, 11),l2, 2);

			
			// Student 1 not enrolled courses (total 6)

//			Course c13 = new Course("SA4111", "JPA", Faculty.BUSINESS, 5,LocalDate.of(2021, 07, 11),l2, 2);
//			Course c14 = new Course("SA4112", "Composition", Faculty.BUSINESS, 5,LocalDate.of(2021, 07, 11),l2, 2);
//			Course c15 = new Course("SA4113", "Inheritance", Faculty.MEDICINE, 5,LocalDate.of(2021, 07, 11),l2, 2);
//
//			Course c16 = new Course("SA4114", "Polymorphism", Faculty.COMPUTING, 5,LocalDate.of(2021, 07, 11),l2, 2);
//			Course c17 = new Course("SA4115", "Abstract Classes and Interfaces", Faculty.COMPUTING, 5,LocalDate.of(2021, 07, 11),l2, 2);
//			Course c18 = new Course("SA4116", "Functional Programming", Faculty.COMPUTING, 5,LocalDate.of(2021, 07, 11),l2, 2);
			

//			crepo.save(c11);crepo.save(c12);crepo.save(c13);crepo.save(c14);crepo.save(c15);
//			crepo.save(c16);crepo.save(c17);crepo.save(c18);
			
			
			l1  = lrepo.findById(1).get();	
			l2 = lrepo.findById(2).get();
			
			
			c1 = cservice.findCourseByCourseName("Advanced Criminal Law");		
			c1.setLecturer(l1);
			cservice.updateCourse(c1);
			
			scservice.addStudentToCourse(c1, s1);
			
			c2 = cservice.findCourseByCourseName("Beginner Pottery");		
			c2.setLecturer(l2);
			cservice.updateCourse(c2);
		
			
			c4 = cservice.findCourseByCourseName("Debate 109");		
			c4.setLecturer(l2);
			cservice.updateCourse(c4);
			

//			StudentCourse sc12 = new StudentCourse(s1, c11,null);
//			StudentCourse sc13 = new StudentCourse(s1, c12,null);
			
//			StudentCourse sc14 = new StudentCourse(s2, c2,null);
//			StudentCourse sc15 = new StudentCourse(s3, c2,null);
//			StudentCourse sc16 = new StudentCourse(s4, c2,null);
//			StudentCourse sc17 = new StudentCourse(s5, c2,null);
//			StudentCourse sc18 = new StudentCourse(s6, c2,null);
			


//			scservice.addStudentToCourse(c11, s1);
//			scservice.addStudentToCourse(c12, s1);
			
//			screpo.save(sc1);
//			screpo.save(sc2);
//			screpo.save(sc14);screpo.save(sc15);screpo.save(sc16);screpo.save(sc17);screpo.save(sc18);
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
//			
//			scservice.addStudentToCourse(c13, s3);
//			scservice.addStudentToCourse(c13, s2);
//			scservice.addStudentToCourse(c13, s1);
//			
//			Grade B = Grade.B;
//			
//			StudentCourse sc1 = new StudentCourse(s1, c1, B );
//			screpo.save(sc1);
//			
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
//			
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

