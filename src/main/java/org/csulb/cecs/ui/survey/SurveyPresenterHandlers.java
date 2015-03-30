package org.csulb.cecs.ui.survey;

import java.util.List;

import org.csulb.cecs.domain.Course;
import org.csulb.cecs.domain.Survey;
import org.vaadin.spring.mvp.MvpPresenterHandlers;

public interface SurveyPresenterHandlers extends MvpPresenterHandlers {
	public boolean saveSurvey(Survey survey);
	public Survey getSurvey(String surveyId);
	public List<Course> getAllCourses();
}
