package org.csulb.cecs.ui.course;

import java.util.List;

import org.csulb.cecs.domain.Course;
import org.vaadin.spring.mvp.MvpPresenterHandlers;

public interface CoursePresenterHandlers extends MvpPresenterHandlers {
	public Long saveCourse(Course course);
	//public List<Course> getAllCourses();
	
}
