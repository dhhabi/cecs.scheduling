package org.csulb.cecs.ui.survey;

import org.csulb.cecs.domain.Survey;
import org.vaadin.spring.mvp.MvpPresenterHandlers;

public interface SurveyPresenterHandlers extends MvpPresenterHandlers {
	public boolean saveSurvey(Survey survey);
	public Survey getSurvey(String surveyId);
}
