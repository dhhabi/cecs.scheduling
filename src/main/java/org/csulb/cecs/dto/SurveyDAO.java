package org.csulb.cecs.dto;

import org.csulb.cecs.domain.Survey;

public interface SurveyDAO {

	public void saveSurvey(Survey survey);
	public Survey getSurvey(String surveyId);
	boolean isAlreadyExist(String instructorEmailId,String semester,String year);
	Long getSurveyId(String instructorEmailId,String semester,String year);
	
}
