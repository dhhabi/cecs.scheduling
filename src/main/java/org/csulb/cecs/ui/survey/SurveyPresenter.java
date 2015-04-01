package org.csulb.cecs.ui.survey;

import java.util.List;

import org.csulb.cecs.domain.Course;
import org.csulb.cecs.domain.CurrentSemester;
import org.csulb.cecs.domain.Room;
import org.csulb.cecs.domain.Survey;
import org.csulb.cecs.dto.CourseDAO;
import org.csulb.cecs.dto.CurrentSemesterDAO;
import org.csulb.cecs.dto.RoomDAO;
import org.csulb.cecs.dto.SurveyDAO;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.vaadin.spring.UIScope;
import org.vaadin.spring.events.EventBus;
import org.vaadin.spring.mvp.MvpHasPresenterHandlers;
import org.vaadin.spring.mvp.MvpView;
import org.vaadin.spring.mvp.presenter.AbstractMvpPresenterView;
import org.vaadin.spring.navigator.VaadinView;
import org.vaadin.spring.security.Security;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;


@SuppressWarnings("serial")
@UIScope
@VaadinView(name="/survey")
//@Secured({"ROLE_USER", "ROLE_ADMIN"})
public class SurveyPresenter extends AbstractMvpPresenterView<SurveyPresenter.SurveyView> implements SurveyPresenterHandlers  {

	@Autowired
	Security security;
	
	@Autowired
	private SurveyDAO surveyDAO;
	
	@Autowired
	private CourseDAO courseDAO;
	
	@Autowired
	private CurrentSemesterDAO currentSemesterDAO;
	
	@Autowired
	private RoomDAO roomDAO;
	
	@Autowired
	public SurveyPresenter(SurveyView view, EventBus eventBus) {
		super(view, eventBus);
		getView().setPresenterHandlers(this);
	}

	public interface SurveyView extends MvpView, MvpHasPresenterHandlers<SurveyPresenterHandlers> {
		void initView(String instructorId,CurrentSemester currentSemester);
		void setErrorMessage(String message);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		Authentication a = security.getAuthentication();
		
		getView().initView(a.getName(),currentSemesterDAO.getCurrentSemester());		
	}

	@Override
	public boolean saveSurvey(Survey survey) {
		try{
			surveyDAO.saveSurvey(survey);
			return true;
		}catch(HibernateException ex){
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public Survey getSurvey(String surveyId) {
		return surveyDAO.getSurvey(surveyId);
	}

	@Override
	public List<Course> getAllCourses() {
		try{
			return courseDAO.getAllCourses();
		}catch(HibernateException he){
			he.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Room> getAllRooms() {
		try{
			return roomDAO.getAllRooms();
		}catch(HibernateException he){
			he.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean isSurveyAlreadyExist(String instructorEmailId,
			String semester, String year) {
		return surveyDAO.isAlreadyExist(instructorEmailId, semester, year);
	}

	@Override
	public Long getSurveyId(String instructorEmailId, String semester,
			String year) {
		return surveyDAO.getSurveyId(instructorEmailId, semester, year);
	}

	@Override
	public boolean updateSurvey(Survey survey) {
		try{
			surveyDAO.updateSurvey(survey);
			return true;
		}catch(HibernateException ex){
			ex.printStackTrace();
			return false;
		}
	}
	
}
