package com.team6.CAPSProj;

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
public class StudentTest {
	
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
		Student student1 = new Student("A2345B","Kevin", "Lin", "kevin@mail.com","1233@nus.edu.sg", "pw123", LocalDate.of(2021, 07, 22));
		stservice.addStudent(student1);
		Student student2 = new Student("A2346B","Kevin", "Foster", "paul@mail.com","1234@nus.edu.sg", "pw123", LocalDate.of(2021, 07, 23));
		stservice.addStudent(student2);
		Student student3 = new Student("A2347B","Linda", "Cheng", "linda@mail.com","1235@nus.edu.sg", "pw123", LocalDate.of(2021, 07, 24));
		stservice.addStudent(student3);
		
		List<Student> students = srepo.findAll();
		assertTrue(students.size() == 3);
		
	}
	
	@Test
	@Order(2)
	public void testFindAllStudents() {	
		List<Student> students = srepo.findAll();
		assertTrue(students.size() > 0);
	}
	
	@Test
	@Order(3)
	public void testFindAllStudentsById()
	{
		//Given a list of StudentIds
		List<Integer>Id = createListOfStudentIds();
		//When we find all students
		assertTrue(findAllStudentsById(Id));
	}
	
	public List<Integer> createListOfStudentIds() {
		List<Integer> numList = new ArrayList<Integer>();
		numList.add(Integer.valueOf(2));
		numList.add(3);
		return numList;
	}
	
	public boolean findAllStudentsById(List<Integer>Id) {
		
		List<Student> studentlist = new ArrayList<Student>();
		List<Integer> retrievedIds = new ArrayList<Integer>();
		for(Integer id : Id)
		{
			if(srepo.findById(id).get() != null)
				studentlist.add(srepo.findById(id).get());
		}
		for(Student s : studentlist)
		{
			retrievedIds.add(s.getStudentId());
		}
		//Then the retrieved IDs have to be equal to the given IDs
		if(Id.equals(retrievedIds))
			return true;
		else
			return false;
	}
	
	@Test
	@Order(4)
	public void testFindStudentByMatricNo() {
		Student studentx = stservice.findStudentByMatricNo("A2345B");
		assertTrue(studentx.getMatricNo().equalsIgnoreCase("A2345B"));
		
	}
	
	@Test
	@Order(5)
	public void testFindStudentByEmail() {
		Student studentx = stservice.findStudentByEmail("1233@nus.edu.sg");
		assertTrue(studentx.getEmail().equalsIgnoreCase("1233@nus.edu.sg"));
	}
	
	@Test
	@Order(6)
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
	@Order(7)
	public void testFindStudentByMatric() {
		String s1 = "A2345B";
		
		List<Student> sList = stservice.findStudentByMatric_FirstName(s1);
		assertTrue(sList.size() == 1);
	}
	
	
	@Test
	@Order(8)
	public void testFindStudentByFirstName() { 
		String s1 = "Kevin";
		
		List<Student> sList = stservice.findStudentByMatric_FirstName(s1);
		assertTrue(sList.size() == 2);
	}
	
	
//	@Test
//	@Order(9)
//	public void testDeleteStudent() {
//		List<Student> students = srepo.findAll();
//		for(Student s : students) {
//			stservice.deleteStudent(s);
//		}
//		assertTrue(srepo.findAll().size() == 0);
//	}
	

}
