package org.csulb.cecs.ui.survey;

import java.util.List;

import org.csulb.cecs.domain.Course;
import org.csulb.cecs.domain.Room;
import org.csulb.cecs.domain.Survey;
import org.vaadin.spring.mvp.MvpPresenterHandlers;

public interface SurveyPresenterHandlers extends MvpPresenterHandlers {
	public boolean saveSurvey(Survey survey);
	public Survey getSurvey(String surveyId);
	public List<Course> getAllCourses();
	public List<Room> getAllRooms();
	boolean isSurveyAlreadyExist(String instructorEmailId, String semester, String year);
	Long getSurveyId(String instructorEmailId, String semester, String year);
}
