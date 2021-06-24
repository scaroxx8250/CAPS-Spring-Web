package com.team6.CAPSProj;

import org.junit.jupiter.api.TestMethodOrder;

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
import com.team6.CAPSProj.service.LecturerInterface;
import com.team6.CAPSProj.service.LecturerServiceImpl;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = CapsProjApplication.class)
@TestMethodOrder(OrderAnnotation.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class LecturerTest {
	
	@Autowired
	private LecturerInterface lservice; 
	
	@Autowired
	LecturerRepository lrepo; 
	
	@Autowired 
	public void setLecturerInterface(LecturerServiceImpl lserviceImpl) {
		this.lservice = lserviceImpl; 
	}
		
	
//	  @Test
//	  @Order(1) 
//	  public void testAddLecturer() { 
//		  Lecturer lecturer1 = new Lecturer("Shona", "Ng", Faculty.BUSINESS, "shonang@gmail.com");
//		  Lecturer lecturer2 = new Lecturer("Tin", "Nguyen", Faculty.COMPUTING, "tin.nguyen@gmail.com");
//		  Lecturer lecturer3 = new Lecturer("Cher Wah", "Tan", Faculty.COMPUTING, "cherwah.tan@gmail.com"); 
//		  Lecturer lecturer4 = new Lecturer("Esther", "Tan", Faculty.COMPUTING, "esther.tan@gmail.com");
//		  lservice.addLecturer(lecturer1);
//		  lservice.addLecturer(lecturer2);
//		  lservice.addLecturer(lecturer3);
//		  lservice.addLecturer(lecturer4);
//		  List<Lecturer> lecturers = lrepo.findAll();
//		  assertTrue(lecturers.size() == 4);
//	  }
	 
	
	
	  @Test
	  @Order(2) 
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
	@Order(3)
	public void testDeleteLecturer() {
		Lecturer lecturer = lrepo.findByLecturerId(2); 
		lservice.deleteLecturer(lecturer); 
		List<Lecturer> lecturers = lrepo.findAll(); 
		assertTrue(lecturers.size() == 3); 
	}
	
	@Test
	@Order(4)
	public void testGetAllLecturers() {
		lservice.GetAllLecturers();
		List<Lecturer> lecturers = lrepo.findAll(); 
		assertTrue(lecturers.size() == 3); 
	}
	
}
