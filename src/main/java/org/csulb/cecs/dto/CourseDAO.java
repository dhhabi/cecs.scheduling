package org.csulb.cecs.dto;

import java.util.List;

import org.csulb.cecs.domain.Course;
import org.hibernate.HibernateException;

public interface CourseDAO {
	public Long addCourse(Course course) throws HibernateException;
	public int updateCourse(Course course);
	public List<Course> getAllCourses();
	public Course getCourseById(Long id);
	
}
