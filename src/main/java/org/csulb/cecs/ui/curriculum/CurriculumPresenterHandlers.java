package org.csulb.cecs.ui.curriculum;

import java.util.List;

import org.csulb.cecs.domain.Account;
import org.csulb.cecs.domain.Course;
import org.csulb.cecs.domain.Curriculum;
import org.vaadin.spring.mvp.MvpPresenterHandlers;

public interface CurriculumPresenterHandlers extends MvpPresenterHandlers {
	
	public List<Course> getAllCourses();
	public List<Curriculum> getAllCurriculum();
	public boolean saveCurriculum(Curriculum curriculum); 
	public boolean isAlreadyExist(String curriculumName);
	
}
