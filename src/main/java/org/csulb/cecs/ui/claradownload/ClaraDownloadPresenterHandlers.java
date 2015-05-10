package org.csulb.cecs.ui.claradownload;

import java.util.List;

import org.csulb.cecs.domain.Account;
import org.vaadin.spring.mvp.MvpPresenterHandlers;

import com.vaadin.server.StreamResource;

public interface ClaraDownloadPresenterHandlers extends MvpPresenterHandlers {
	public boolean checkProjectExistence(String semester,String year);
	public StreamResource createClaraProgram(String semester,String year);	
	public List<Account> checkIdAllTheSurveyAreDone(String semester,String year);
}
