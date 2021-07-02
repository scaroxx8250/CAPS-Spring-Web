package com.team6.CAPSProj;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.team6.CAPSProj.model.Student;
import com.team6.CAPSProj.repo.StudentRepository;
import com.team6.CAPSProj.service.StudentServiceImpl;
import com.team6.CAPSProj.service.StudentService;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = CapsProjApplication.class)
@TestMethodOrder(OrderAnnotation.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class StudentServiceTest {
	
	@Autowired
	private StudentRepository srepo;
	
	@Autowired
	private StudentService stservice;
	
	@Autowired
	public void setStudentService(StudentServiceImpl stserviceImpl) {
		this.stservice = stserviceImpl;
	}
	

	
	@Test
	@Order(1)
	public void testAddStudents() {
		
		List<Student> beforeAddStudents = stservice.findAllStudents();
		
		stservice.addStudent(new Student("A2345B","Kevin", "Lin", "kevin@mail.com","1233@nus.edu.sg", "pw123", LocalDate.of(2021, 07, 22)));
		stservice.addStudent(new Student("A2346B","Robert", "Foster", "robert@mail.com","1234@nus.edu.sg", "pw123", LocalDate.of(2021, 07, 23)));
		stservice.addStudent(new Student("A2347B","Kevin", "Foster", "samuel@mail.com","1235@nus.edu.sg", "pw123", LocalDate.of(2021, 07, 24)));
		
		List<Student> afterAddStudents = stservice.findAllStudents();
		assertTrue(afterAddStudents.size() - beforeAddStudents.size() == 3);
	}
	
	@Test
	@Order(2)
	public void testFindAllStudents() {
		List<Student> students = stservice.findAllStudents();
		assertTrue(students.size() > 0);
	}
	
	
	@Test
	@Order(3)
	public void testFindAllStudentsById()
	{
		//Given a list of StudentIds
		List<Integer>Id = createListOfStudentIds();
		//When we find all students
		List<Integer> retrievedIds = new ArrayList<Integer>();
		List<Student> retrievedStudents = stservice.findAllStudentsById(Id);
		for(Student s : retrievedStudents)
		{
			retrievedIds.add(s.getStudentId());
		}
		assertTrue(Id.equals(retrievedIds));

	}
	
	public List<Integer> createListOfStudentIds() {
	List<Integer> numList = new ArrayList<Integer>();
	numList.add(2);
	numList.add(3);
	return numList;
	}
	
	
	@Test
	@Order(4)
	public void testFindStudentByMatricNo() {
		String matricNo = "A2345B";
		Student studentx = stservice.findStudentByMatricNo(matricNo);
		assertTrue(studentx.getMatricNo().equals(matricNo));
	}
	
	
	@Test
	@Order(5)
	public void testFindStudentByEmail() {
		String email = "1233@nus.edu.sg";
		Student studentx = stservice.findStudentByEmail(email);
		assertTrue(studentx.getEmail().equals(email));
	}
	
	@Test
	@Order(6)
	public void testFindStudentsByMatric_FirstName() {
		String matricNo = "A2345B";
		
		List<Student> matricResults = stservice.findStudentByMatric_FirstName(matricNo);

		String firstName = "Kevin";
		List<Student> firstNameResults = stservice.findStudentByMatric_FirstName(firstName);
		
		assertTrue(matricResults.size() == 1 && firstNameResults.size() == 2);
	}
	
	
	@Test
	@Order(7)
	public void testUpdateStudent() {
		Student studentx = stservice.findStudentByMatricNo("A2347B");
		studentx.setFirstName("Xudong");
		studentx.setLastName("Tan");
		studentx.setPersonEmail("xudong@gmail.com");
		studentx.setMatrDate(LocalDate.of(2021, 07, 24));
		stservice.updateStudent(studentx);
		
		//Get student object again after update

		Student studenty = stservice.findStudentByMatricNo("A2347B");
		assertTrue(studenty.equals(studentx));
	}
	
	
	@Test
	@Order(8)
	public void testDeleteStudent() {	
		//Create new Student
		stservice.addStudent(new Student("A1111B","Karin", "Baumann", "km@mail.com","5247@nus.edu.sg", "pw123", LocalDate.of(2021, 07, 24)));
		
		//Delete Student
		List<Student> beforeDeleteStudents = stservice.findAllStudents();
		stservice.deleteStudent(stservice.findStudentByMatricNo("A1111B"));
		List<Student> afterDeleteStudents = stservice.findAllStudents();
		
		assertTrue(beforeDeleteStudents.size() - afterDeleteStudents.size() == 1);
	}
	
	@Test
	@Order(9)
	public void testFindStudentByEmailAndPassword() {
		
		//Find a student by email and PW
		assertNotNull(stservice.findByEmailAndPassword("1233@nus.edu.sg", "pw123"));
	}
	
	
	@Test
	@Order(10)
	public void testFindFirstNameByStudentId() {
		
		//get ID by of one of the students (Kevin Lin)
		int studentId = stservice.findStudentByMatricNo("A2345B").getStudentId();
		
		// get first name by Student id
		assertTrue(stservice.findFirstNameByStudentId(studentId).equals("Kevin"));
		
	}
	
	@Test
	@Order(11)
	public void testFindLastNameByStudentId() {
	//get ID by of one of the students (Kevin Lin)
	int studentId = stservice.findStudentByMatricNo("A2345B").getStudentId();
	
	// get first name by Student id
	assertTrue(stservice.findLastNameByStudentId(studentId).equals("Lin"));
		
	}
	
	@Test
	@Order(12)
	public void testFindStudentByStudentId() {
		//get ID by of one of the students (Kevin Lin)
		int studentId = stservice.findStudentByMatricNo("A2345B").getStudentId();
		
		//Find student by this Id
		assertNotNull(stservice.findStudentByStudentId(studentId));
	}
	

}
