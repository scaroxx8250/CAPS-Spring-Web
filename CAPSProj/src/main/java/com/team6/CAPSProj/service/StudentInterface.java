package com.team6.CAPSProj.service;

import java.util.List;

import com.team6.CAPSProj.model.Student;

public interface StudentInterface {
	public List<Student>findAllStudents();
	public  List<Student>findAllStudentsById(List<Integer>Id);
	public Student findStudentByMatricNo(int StudentNo);
	public Student findStudentByEmail(String Email);
	public List<Student> findStudentByMatric_FirstName(String input);
	public void addStudent(Student student);
	public void updateStudent(Student student);
	public void deleteStudent(Student student);

}
