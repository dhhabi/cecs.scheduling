package org.csulb.cecs.dto;

import java.util.List;

import org.csulb.cecs.domain.Survey;

public interface SurveyDAO {

	public void saveSurvey(Survey survey);
	public Survey getSurvey(String surveyId);
	boolean isAlreadyExist(String instructorEmailId,String semester,String year);
	Long getSurveyId(String instructorEmailId,String semester,String year);
	public void updateSurvey(Survey survey);
	public Survey getSurveyWithPreferredCourses(String instructorEmailId,String semester,String year);
	public Survey getSurvey(String instructorEmailId,String semester,String year);
	public List<Survey> getAllSurvey(String semester,String year);
	public List<String> getAllInstructorEmailIds(String semester,String year);
}
