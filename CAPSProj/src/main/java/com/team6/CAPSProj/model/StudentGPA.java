package com.team6.CAPSProj.model;

public class StudentGPA {
	private int courseId;
	private String courseName;
	private int credits;
	private double grade;
	public String year;
	
	
	public StudentGPA() {
		super();
	}
	public StudentGPA(int courseId, String courseName, int credits, double grade, String year) {
		super();
		this.courseId = courseId;
		this.courseName = courseName;
		this.credits = credits;
		this.grade = grade;
		this.year = year;
	}
	public int getCourseId() {
		return courseId;
	}
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public int getCredits() {
		return credits;
	}
	public void setCredits(int credits) {
		this.credits = credits;
	}
	public double getGrade() {
		return grade;
	}
	public void setGrade(double grade) {
		this.grade = grade;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
}
