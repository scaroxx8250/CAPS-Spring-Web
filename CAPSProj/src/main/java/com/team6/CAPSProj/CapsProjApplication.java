package com.team6.CAPSProj;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.team6.CAPSProj.repo.CourseRepository;
import com.team6.CAPSProj.repo.LecturerRepository;
import com.team6.CAPSProj.repo.StudentRepository;

@SpringBootApplication
public class CapsProjApplication {

	@Autowired
	StudentRepository srepo;
	@Autowired
	CourseRepository crepo;
	
	@Autowired
	LecturerRepository lrepo;
	
	public static void main(String[] args) {
		SpringApplication.run(CapsProjApplication.class, args);

	}
	
	@Bean
	CommandLineRunner runner() {
		return args ->{
			
			DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			LocalDate dt = LocalDate.parse("23/05/2021",df);
			
//			Student s1 = new Student("Tan","Sharon", "sharonTan@gmail.com","e100024@u.nus.edu", "1234", dt);
//			Student s2 = new Student("Lim","Sharon", "sharonLim@gmail.com","e112024@u.nus.edu", "1234", dt);
//			srepo.save(s1); srepo.save(s2);
			
//			Course c1 = new Course("SA4101", "design", "COM", 5, 80);
//			Course c2 = new Course("SCI101", "Anontomy", "SCI", 5, 80);
//			crepo.save(c1); crepo.save(c2);
		
		//Lecturer l1 = new Lecturer("Tin", "Nguyen", "COM", null, null, null)
		
		};
}

}
