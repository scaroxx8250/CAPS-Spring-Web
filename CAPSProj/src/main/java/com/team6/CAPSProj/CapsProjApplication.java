package com.team6.CAPSProj;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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
import com.team6.CAPSProj.service.LecturerService;
import com.team6.CAPSProj.service.LecturerServiceImpl;
import com.team6.CAPSProj.service.StudentCourseService;
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
	LecturerRepository lrepo;
	
	@Autowired
	StudentCourseRepository screpo;
	
	@Autowired
	private LecturerService lservice;
	@Autowired
	public void setLecturerService(LecturerServiceImpl lserviceImpl)
	{
		this.lservice = lserviceImpl;
	}
	
	@Autowired
	private StudentCourseService scservice;
	
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
			Admin ad1 = new Admin("John","Doe","admin@gmail.com","admin");
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
			Student s7 = new Student("A9992B","Tod","Barker", "tb@gmail.com","42319@u.nus.edu", "5678", LocalDate.of(2021, 6, 22));
			Student s8 = new Student("A9991B","Sharon","Tan", "sharonTan@gmail.com","4e319@u.nus.edu", "1234", LocalDate.of(2021, 6, 22));
			Student s9 = new Student("A9990B","Evelyn","Lim", "sharonLim@gmail.com","3c31b@u.nus.edu", "1234", LocalDate.of(2021, 6, 22));

			
			srepo.save(s1);srepo.save(s2);srepo.save(s3);srepo.save(s4);srepo.save(s5);srepo.save(s6);
			srepo.save(s7);srepo.save(s8);srepo.save(s9);
			 
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
			Course c2 = new Course("Beginner Pottery", "The ulitimate pottery lesson for doctors.", Faculty.MEDICINE, 5, LocalDate.of(2021,8,22),l5, 5);
			
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

			// Insert student and course to student_course table
			scservice.addStudentToCourse(c1, s1);
			scservice.addStudentToCourse(c2, s1);
			scservice.addStudentToCourse(c2, s2);
			scservice.addStudentToCourse(c2, s3);
			scservice.addStudentToCourse(c2, s4);
			scservice.addStudentToCourse(c3, s1);
			scservice.addStudentToCourse(c5, s1);
			scservice.addStudentToCourse(c4, s2);
			scservice.addStudentToCourse(c4, s3);

			scservice.addStudentToCourse(c6, s1);
			scservice.addStudentToCourse(c7, s1);
			scservice.addStudentToCourse(c8, s1);
			scservice.addStudentToCourse(c9, s1);
			scservice.addStudentToCourse(c10, s1);
			
			// Update student grades
			StudentCourse sc1 = new StudentCourse(s1, c1, 4.7);
			StudentCourse sc2 = new StudentCourse(s1, c2, 3.8);
			StudentCourse sc3 = new StudentCourse(s1, c3, 3.1);
			StudentCourse sc4 = new StudentCourse(s1, c5, 3.6);
			
			List<StudentCourse> sclist = new ArrayList<StudentCourse>();
			sclist.add(sc1);
			sclist.add(sc2);
			sclist.add(sc3);
			sclist.add(sc4);
			scservice.updateStudentGrade(sclist);
			
		};
}
}

