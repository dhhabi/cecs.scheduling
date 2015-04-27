package org.csulb.cecs.ui.project;

import java.util.ArrayList;
import java.util.List;

import org.csulb.cecs.domain.Account;
import org.csulb.cecs.domain.Course;
import org.csulb.cecs.domain.CurrentSemester;
import org.csulb.cecs.domain.Curriculum;
import org.csulb.cecs.domain.Room;
import org.csulb.cecs.domain.ScheduleProject;
import org.csulb.cecs.dto.AccountDAO;
import org.csulb.cecs.dto.CourseDAO;
import org.csulb.cecs.dto.CurrentSemesterDAO;
import org.csulb.cecs.dto.CurriculumDAO;
import org.csulb.cecs.dto.ProjectDAO;
import org.csulb.cecs.dto.RoomDAO;
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

@SuppressWarnings("serial")
@UIScope
@VaadinView(name=ViewToken.PROJECT)
public class ProjectPresenter extends AbstractMvpPresenterView<ProjectPresenter.ProjectView> implements ProjectPresenterHandlers {
	
	public interface ProjectView extends MvpView, MvpHasPresenterHandlers<ProjectPresenterHandlers> {
		void init();
		void setErrorMessage(String errorMessage);
	}
	
	@Autowired
	private ProjectDAO projectDAO;
	@Autowired
	private AccountDAO accountDAO;
	@Autowired
	private RoomDAO roomDAO;
	@Autowired
	private CourseDAO courseDAO;
	@Autowired
	private CurrentSemesterDAO currentSemesterDAO;
	@Autowired
	private CurriculumDAO curriculumDAO;
	
	@Autowired
	public ProjectPresenter(ProjectView view, EventBus eventBus) {
		super(view, eventBus);
		getView().setPresenterHandlers(this);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		getView().init();		
	}

	@Override
	public boolean saveScheduleProject(ScheduleProject scheduleProject) {
		try{
			//Also set Current Semester
			currentSemesterDAO.setCurrentSemester(new CurrentSemester(scheduleProject.getSemester(),scheduleProject.getYear()));
			projectDAO.saveScheduleProject(scheduleProject);
			return true;
		}catch(HibernateException he){
			he.printStackTrace();
			return false;
		}
	}

	@Override
	public List<Account> getAllAccounts() {
		try{
			return accountDAO.getAllAccounts();
		}catch(HibernateException he){
			he.printStackTrace();
			return new ArrayList<Account>();
		}
	}

	@Override
	public boolean isAlreadyExits(String semester, String year) {
		return projectDAO.isAlreadyExists(semester, year);
	}

	@Override
	public List<Course> getAllCourses() {
		try{
			return courseDAO.getAllCourses();
		}catch(HibernateException he){
			he.printStackTrace();
			return new ArrayList<Course>();
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
	public List<Curriculum> getAllCurriculums() {
		try{
			return curriculumDAO.getAllCurriculum();
		
		}catch(HibernateException he){
			he.printStackTrace();
			return new ArrayList<Curriculum>();
		}
	}

	

}
