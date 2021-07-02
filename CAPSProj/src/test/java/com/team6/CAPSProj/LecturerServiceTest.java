package com.team6.CAPSProj;

import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.team6.CAPSProj.model.Faculty;
import com.team6.CAPSProj.model.Lecturer;
import com.team6.CAPSProj.repo.LecturerRepository;
import com.team6.CAPSProj.service.LecturerService;
import com.team6.CAPSProj.service.LecturerServiceImpl;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = CapsProjApplication.class)
@TestMethodOrder(OrderAnnotation.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class LecturerServiceTest {
	
	@Autowired
	private LecturerService lservice; 
	
	@Autowired
	LecturerRepository lrepo; 
	
	@Autowired 
	public void setLecturerInterface(LecturerServiceImpl lserviceImpl) {
		this.lservice = lserviceImpl; 
	}
		
	
	  @Test
	  @Order(1)
	  // Positive Test Case - add lecturer functionality
	  public void testAddLecturer() {
		  List<Lecturer> beforeAdd = lrepo.findAll();
		  Lecturer lecturer1 = new Lecturer("Shona", "Ng", Faculty.BUSINESS, "shonang@gmail.com", "shonang@gmail.com", "1234");
		  Lecturer lecturer2 = new Lecturer("Tin", "Nguyen", Faculty.COMPUTING, "tin.nguyen@gmail.com", "tin.nguyen@gmail.com", "1234");
		  Lecturer lecturer3 = new Lecturer("Cher Wah", "Tan", Faculty.COMPUTING, "cherwah.tan@gmail.com","cherwah.tan@gmail.com", "1234"); 
		  Lecturer lecturer4 = new Lecturer("Esther", "Tan", Faculty.COMPUTING, "esther.tan@gmail.com", "esther.tan@gmail.com", "1234");
		  lservice.addLecturer(lecturer1);
		  lservice.addLecturer(lecturer2);
		  lservice.addLecturer(lecturer3);
		  lservice.addLecturer(lecturer4);
		  List<Lecturer> afterAdd = lrepo.findAll();
		  assertTrue(afterAdd.size() - beforeAdd.size() == 4);
	  }
	 
	  @Test
	  @Order(2) 
	  // Positive Test Case - find lecturer by id functionality
	  public void testFindLecturerById() {
		  Lecturer l1 = lservice.findLecturerById(1);
		  Lecturer l2 = lservice.findLecturerById(2);
		  Lecturer l3 = lservice.findLecturerById(3);
		  Lecturer l4 = lservice.findLecturerById(4);
		  Lecturer l5 = lservice.findLecturerById(5);
		  Lecturer l6 = lservice.findLecturerById(6);
		  Lecturer l7 = lservice.findLecturerById(7);
		  Lecturer l8 = lservice.findLecturerById(8);
		  Lecturer l9 = lservice.findLecturerById(9);
		  assertNotNull(l1);
		  assertNotNull(l2);
		  assertNotNull(l3);
		  assertNotNull(l4);
		  
		  // comment out this block if the startup codes in capsprojapplication.java are removed
		  assertNotNull(l5);
		  assertNotNull(l6);
		  assertNotNull(l7);
		  assertNotNull(l8);
		  assertNotNull(l9);
	  }
	  
	  @Test
	  @Order(3) 
	  // Positive Test Case - find lecturer by email and password functionality
	  public void testFindByEmailAndPassword() {
		  Lecturer l1 = lservice.findByEmailAndPassword("benjamin.franklin@u.nus.edu", "1234");
		  Lecturer l2 = lservice.findByEmailAndPassword("buzz.hickey@u.nus.edu", "5678");
		  Lecturer l3 = lservice.findByEmailAndPassword("craig.pelton@u.nus.edu","1234");
		  Lecturer l4 = lservice.findByEmailAndPassword("ian.duncon@u.nus.edu", "1234");
		  Lecturer l5 = lservice.findByEmailAndPassword("michelle.slator@u.nus.edu", "1234");
		  Lecturer l6 = lservice.findByEmailAndPassword("shonang@gmail.com", "1234");
		  Lecturer l7 = lservice.findByEmailAndPassword("tin.nguyen@gmail.com", "1234");
		  Lecturer l8 = lservice.findByEmailAndPassword("cherwah.tan@gmail.com", "1234");
		  Lecturer l9 = lservice.findByEmailAndPassword("esther.tan@gmail.com", "1234");
		  
		  assertNotNull(l6);
		  assertNotNull(l7);
		  assertNotNull(l8);
		  assertNotNull(l9);
		  
		  // comment out this block if the startup codes in capsprojapplication.java are removed
		  assertNotNull(l1);
		  assertNotNull(l2);
		  assertNotNull(l3);
		  assertNotNull(l4);
		  assertNotNull(l5);
		 
	  }
	
	  @Test
	  @Order(4) 
	  // Positive Test Case - test update lecturer functionality
	  public void testUpdateLecturer() { 
		  Lecturer lecturer = lrepo.findByLecturerId(2); 
		  lecturer.setFirstName("Grace");
		  lecturer.setLastName("Huang");
		  lecturer.setPersonalEmail("gracehuang@gmail.com");
		  lservice.updateLecturer(lecturer);
		  Lecturer updated = lrepo.findByLecturerId(2); 
		  assertTrue(updated.equals(lecturer)); 
		  
	}
	 
	
	@Test
	@Order(5)
	// Positive Test Case - test delete lecturer functionality
	// Lecturer must not be assigned to any courses
	public void testDeleteLecturer() {
		List<Lecturer> beforeDel = lrepo.findAll(); 
		Lecturer lecturer = lrepo.findByLecturerId(6); 
		lservice.deleteLecturer(lecturer); 
		List<Lecturer> afterDel = lrepo.findAll(); 
		assertTrue(beforeDel.size() - afterDel.size() == 1); 
	}
	
	@Test
	@Order(6)
	// Positive Test Case = test get all lecturers functionality
	public void testGetAllLecturers() {
		lservice.GetAllLecturers();
		List<Lecturer> lecturers = lrepo.findAll(); 
		
		// swap the commented code depending on whether the startup code has been removed
//		assertTrue(lecturers.size() == 3); 
		assertTrue(lecturers.size() == 8); 
	}
	
}
