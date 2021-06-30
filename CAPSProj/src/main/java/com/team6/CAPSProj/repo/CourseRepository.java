package com.team6.CAPSProj.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import com.team6.CAPSProj.model.Course;

import com.team6.CAPSProj.model.Student;

public interface CourseRepository extends JpaRepository<Course, Integer> {
	
	Course findByCourseId(int courseId);

	@Query("Select c from Course c where c.courseName =:courseName and YEAR(c.courseStartDate) =:year")
	Course findByCourseNameWithCurrentYear(@Param("courseName")String courseName,@Param("year") int year); 

	@Query("Select c from Course c where YEAR(c.courseStartDate) = ?1")
	List<Course> findCoursesByYear(Integer year);

	@Query("Select c from Course c where YEAR(c.courseStartDate) = :year")
	List<Course> findCourseByCurrentYear(@Param("year") int year);
	
	@Query("Select c from Course c where c.lecturer.lecturerId = :lecId")
	List<Course> findCourseByLecturer(@Param("lecId") Integer lecId);
	
	@Query("Select c from Course c where c.lecturer.lecturerId = :lecId and YEAR(c.courseStartDate) =:year ")
	List<Course> findCourseByYearAndLecturer(@Param("year") int year,@Param("lecId") Integer lecId);
	
	@Query(value = "SELECT * FROM course "
			+ "WHERE course.course_start_date > CURRENT_DATE "
			+ "AND course.course_id NOT IN (SELECT course_id FROM student_course WHERE student_course.student_id = :studentId) "
			+ "AND course.course_occupancy = 0", nativeQuery = true)
	Page<Course> findAllNotEnrolledCoursesByStudentByPage(@Param("studentId") int studentId, Pageable pageable);
	
	@Query("Select c from Course c where c.lecturer.lecturerId = :lecId")
	Page<Course> findCourseByLecturerByPage(@Param("lecId") Integer lecId, Pageable pageable);
	
	@Query(value = "SELECT * FROM course "
			+ "WHERE course.course_start_date > CURRENT_DATE AND course.faculty =:faculty "
			+ "AND course.course_id NOT IN (SELECT course_id FROM student_course WHERE student_course.student_id = :studentId) "
			+ "AND course.course_occupancy = 0", nativeQuery = true)
	Page<Course> findAllNotEnrolledCoursesByStudentAndFacultyByPage(@Param("studentId") int studentId, @Param("faculty")int faculty,
			Pageable pageable);
	
	@Query("Select c from Course c where YEAR(c.courseStartDate) = ?1")
	Page<Course> findCoursesByYearByPage(Integer year, Pageable pageable);
	
}
