package org.csulb.cecs.ui.sections;

import java.util.ArrayList;
import java.util.List;

import org.csulb.cecs.domain.Account;
import org.csulb.cecs.domain.Course;
import org.csulb.cecs.domain.Room;
import org.csulb.cecs.domain.ScheduleProject;
import org.csulb.cecs.domain.Section;
import org.csulb.cecs.domain.Survey;
import org.csulb.cecs.dto.ProjectDAO;
import org.csulb.cecs.dto.RoomDAO;
import org.csulb.cecs.dto.SectionDAO;
import org.csulb.cecs.dto.SurveyDAO;
import org.csulb.cecs.service.ClaraService;
import org.csulb.cecs.ui.ViewToken;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.UIScope;
import org.vaadin.spring.events.EventBus;
import org.vaadin.spring.mvp.MvpHasPresenterHandlers;
import org.vaadin.spring.mvp.MvpView;
import org.vaadin.spring.mvp.presenter.AbstractMvpPresenterView;
import org.vaadin.spring.navigator.VaadinView;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.StreamResource;


@SuppressWarnings("serial")
@UIScope
@VaadinView(name=ViewToken.SECTIONS)
//@Secured({"ROLE_ADMIN"})
public class SectionsPresenter extends AbstractMvpPresenterView<SectionsPresenter.SectionsView> implements SectionsPresenterHandlers  {

	@Autowired
	private ProjectDAO projectDAO;
	
	@Autowired
	private SectionDAO sectionDAO;
	
	@Autowired
	private SurveyDAO surveyDAO;
	@Autowired
	private RoomDAO roomDAO;
	
	@Autowired
	private ClaraService claraService;
	
	@Autowired
	public SectionsPresenter(SectionsView view, EventBus eventBus) {
		super(view, eventBus);
		getView().setPresenterHandlers(this);
	}

	public interface SectionsView extends MvpView, MvpHasPresenterHandlers<SectionsPresenterHandlers> {
		void initView();
		void setErrorMessage(String message);
	}

	@Override
	public void enter(ViewChangeEvent arg0) {
		getView().initView();		
	}

	@Override
	public ScheduleProject getScheduleProject(String semester, String year) {
		try{
			return projectDAO.getScheduleProject(semester, year);
		}catch(HibernateException he){
			he.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean updateSection(Section section) {
		try{
			sectionDAO.updateSection(section);
			return true;
		}catch(HibernateException he){
			he.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean isCheckIfProjectExists(String semester, String year) {
		return projectDAO.isAlreadyExists(semester, year);
	}

	@Override
	public List<Section> getSections(Account account) {
		try{
			return sectionDAO.getSections(account);
		}catch(HibernateException he){
			he.printStackTrace();
			return new ArrayList<Section>();
		}
	}

	@Override
	public List<Course> getPreferredCourses(String instructorEmailId,
			String semester, String year) {
		try{
			return surveyDAO.getSurveyWithPreferredCourses(instructorEmailId, semester, year).getPreferredCourses(); 
			//return survey.getPreferredCourses();
		}catch(HibernateException he){
			he.printStackTrace();
			return new ArrayList<Course>();
		}
	}

	@Override
	public boolean checkSurveyExistence(String instructorEmailId,
			String semester, String year) {
		return surveyDAO.isAlreadyExist(instructorEmailId, semester, year);
	}

	@Override
	public List<Section> getSectionList(String semester, String year,
			Account instructor) {
		List<Section> sectionList = new ArrayList<Section>();
		try{
			ScheduleProject sp = projectDAO.getScheduleProjectWithSectionListInit(semester, year);
			for(Section section:sp.getSections()){
				if(section.getInstructor()!=null){
					if(section.getInstructor().getUsername().equals(instructor.getUsername()))
						sectionList.add(section);
				}
			}
			return sectionList;
		}catch(HibernateException he){
			he.printStackTrace();
			return sectionList;
		}
	}

	@Override
	public List<Room> getAllRooms() {
		try{
			return roomDAO.getAllRooms();
		}catch(HibernateException he){
			he.printStackTrace();
			return new ArrayList<Room>();
		}
	}

	@Override
	public StreamResource createClaraProgram(String semester,String year) {
		return claraService.createProgram(semester,year);
	}

	
}
