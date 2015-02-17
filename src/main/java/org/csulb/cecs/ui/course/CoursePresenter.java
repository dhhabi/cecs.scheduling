package org.csulb.cecs.ui.course;

import java.util.List;

import org.csulb.cecs.domain.Course;
import org.csulb.cecs.dto.CourseDAO;
import org.csulb.cecs.ui.ViewToken;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.vaadin.spring.UIScope;
import org.vaadin.spring.events.EventBus;
import org.vaadin.spring.mvp.MvpHasPresenterHandlers;
import org.vaadin.spring.mvp.MvpView;
import org.vaadin.spring.mvp.presenter.AbstractMvpPresenterView;
import org.vaadin.spring.navigator.VaadinView;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;

@SuppressWarnings("serial")
@UIScope
//@Secured({"ROLE_ADMIN"})
@VaadinView(name=ViewToken.COURSE)
public class CoursePresenter extends AbstractMvpPresenterView<CoursePresenter.CourseView> implements CoursePresenterHandlers {

	@Autowired
	private CourseDAO courseDAO;
	
	public interface CourseView extends MvpView, MvpHasPresenterHandlers<CoursePresenterHandlers> {
		void initView();
		void setErrorMessage(String message);
	}
	
	@Autowired
	public CoursePresenter(CourseView view, EventBus eventBus) {
		super(view, eventBus);
		getView().setPresenterHandlers(this);
	
	}	

	@Override
	public void enter(ViewChangeEvent event) {
		getView().initView();
		
	}

	@Override
	public int saveCourse(Course course) {
		try{
			if(!courseDAO.isAlreadyExist(course.getPrefix(), course.getCourseNo()))
				courseDAO.addCourse(course);
			else
				return 0;
		}catch(HibernateException he){
			//he.printStackTrace();
			return 2;
		}
		return 1;
	}

	@Override
	public int updateCourse(Course course) {
		try{
			courseDAO.updateCourse(course);
		}catch(HibernateException he){
			//he.printStackTrace();
			return 2;
		}
		return 1;
	}

	@Override
	public int removeCourse(Course course) {
		try{
			courseDAO.deleteCourse(course);
		}catch(HibernateException he){
			//he.printStackTrace();
			return 2;
		}
		return 1;
	}

	@Override
	public List<Course> getAllCourse() {
		try{
			return courseDAO.getAllCourses();
		}catch(HibernateException he){
			he.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Course> searchCourse(String serchString) {
		try{
			return courseDAO.searchCourse(serchString);
		}catch(HibernateException he){
			he.printStackTrace();
			return null;
		}
	}

}
