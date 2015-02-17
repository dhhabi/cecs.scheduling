package org.csulb.cecs.ui.course;

import java.util.List;

import org.csulb.cecs.domain.Course;
import org.vaadin.spring.mvp.MvpPresenterHandlers;

public interface CoursePresenterHandlers extends MvpPresenterHandlers {
	public int saveCourse(Course course);
	public int updateCourse(Course course);
	public int removeCourse(Course course);
	public List<Course> getAllCourse();
	public List<Course> searchCourse(String serchString);
}
