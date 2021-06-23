package com.team6.CAPSProj.repo;

import org.springframework.data.jpa.repository.JpaRepository;


import com.team6.CAPSProj.model.Course;

public interface CourseRepository extends JpaRepository<Course, Integer> {
	
		Course findByCourseId(int courseId);

		Course findByCourseName(String courseName); 

}
