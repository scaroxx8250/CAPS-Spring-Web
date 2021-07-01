
package com.team6.CAPSProj.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.team6.CAPSProj.model.Course;
import com.team6.CAPSProj.model.Student;
import com.team6.CAPSProj.model.StudentCourse;

public interface StudentCourseInterface {
	
	public List<StudentCourse>findAllCoursesByStudent(Integer studentId);
	public List<StudentCourse>findAllStudentsByCourse(String CourseName);
	public List<StudentCourse>findAllGradeByYearAndStudent(List<Course> course, Student student, int year);
	public List<StudentCourse>findAllGradeByStudent(List<Course> course, Student student);
	public StudentCourse findGradeByStudentAndCourse(Student student, Course course);
	public boolean addStudentToCourse(Course course, Student student);
	public void removeStudentFromCourse(Course course, Student student);
	public void updateStudentGrade(List<StudentCourse> studentCourse);
	public int CountTotalStudentEnrol(Integer courseId );
	public void removeStudentCourse(StudentCourse studentcourse);
	Page<StudentCourse> findAllPaginatedCoursesByStudent(int pageNo, int pageSize, Integer studentId);
	Page<StudentCourse>findAllPaginatedStudentsByLecturer(int pageNo, int pageSize,Integer lecturerId, Course course);
	Page<StudentCourse>findAllPaginatedStudentsByCourse(int pageNo, int pageSize, String CourseName);
	public List<StudentCourse> findAllGradedCoursesByStudent(Integer studentId);
	public boolean adminAddStudentToCourse(Course course, Student student);
	Page<StudentCourse>findAllPaginatedStudentsByGradedCourse(int pageNo, int pageSize, String CourseName);

}

