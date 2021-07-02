package com.team6.CAPSProj;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.team6.CAPSProj.service.AdminService;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = CapsProjApplication.class)
@TestMethodOrder(OrderAnnotation.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AdminServiceTest {
	
	@Autowired
	private AdminService aservice;
	
	
	@Test
	@Order(1)
	//Positive Test Case - find the Email and Password.
	public void testfindByEmailAndPassword() {
		
		assertNotNull(aservice.findByEmailAndPassword("admin@gmail.com", "admin"));
	}

	
	
	
}
