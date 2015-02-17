package org.csulb.cecs.dto;

import java.util.List;

import org.csulb.cecs.domain.Course;
import org.hibernate.HibernateException;

public interface CourseDAO {
	public void addCourse(Course course) throws HibernateException;
	public void updateCourse(Course course);
	public List<Course> getAllCourses();
	public void deleteCourse(Course course);
	public boolean isAlreadyExist(String prefix,String courseNo);
	public List<Course> searchCourse(String serchString);
}
