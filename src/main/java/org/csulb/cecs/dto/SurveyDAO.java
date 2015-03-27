package org.csulb.cecs.dto;

import org.csulb.cecs.domain.Survey;

public interface SurveyDAO {

	public void saveSurvey(Survey survey);
	public Survey getSurvey(String surveyId);
	
	
}
