package com.team6.CAPSProj;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.team6.CAPSProj.model.Course;
import com.team6.CAPSProj.model.Grade;
import com.team6.CAPSProj.model.Student;
import com.team6.CAPSProj.model.StudentCourse;
import com.team6.CAPSProj.repo.CourseRepository;
import com.team6.CAPSProj.repo.LecturerRepository;
import com.team6.CAPSProj.repo.StudentCourseRepository;
import com.team6.CAPSProj.repo.StudentRepository;

@SpringBootApplication
public class CapsProjApplication {

	@Autowired
	StudentRepository srepo;
	@Autowired
	CourseRepository crepo;
	
	@Autowired
	LecturerRepository lrepo;
	
	@Autowired
	StudentCourseRepository screpo;
	
	public static void main(String[] args) {
		SpringApplication.run(CapsProjApplication.class, args);
		
	}
	
//	@Bean
//	@Transactional
//	CommandLineRunner runner() {
//		return args ->{
//			
//			Student s1 = new Student("e123456","Wong","Jireh", "jirehWong@gmail.com","e123456@u.nus.edu", "5678", LocalDate.of(2021, 6, 22));
//			srepo.save(s1);
//			
//			Course c1 = new Course("SA4102", "C#", "COM", 5,LocalDate.of(2021, 6, 22), 100);
//			crepo.save(c1);
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
		
//		};
}


