package com.team6.CAPSProj.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.team6.CAPSProj.model.Student;

public interface StudentService {
	public List<Student>findAllStudents();
	public  List<Student>findAllStudentsById(List<Integer>Id);
	public Student findStudentByMatricNo(String matrNo);
	public Student findStudentByEmail(String Email);
	public List<Student> findStudentByMatric_FirstName(String input);
	public void addStudent(Student student);
	public void updateStudent(Student student);
	public void deleteStudent(Student student);
	public Student findByEmailAndPassword(String email, String password);

	public String findFirstNameByStudentId(Integer id);
	public String findLastNameByStudentId(Integer id);
	public Student findStudentByStudentId(Integer id);
	Page<Student> findAllPaginatedNotEnrolledStudentsByCourse(int pageNo, int pageSize, Integer courseId);
	Page<Student> findAllPaginatedStudents(int pageNo, int pageSize);
	Page<Student> findAllPaginatedStudentsByCourse(int pageNo, int pageSize, Integer courseId);

}
