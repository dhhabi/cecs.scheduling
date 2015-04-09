package org.csulb.cecs.ui.curriculum;

import java.util.List;

import org.csulb.cecs.domain.Course;
import org.csulb.cecs.dto.CourseDAO;
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
//Secured({"ROLE_ADMIN"})
@VaadinView(name=ViewToken.CURRICULUM)
public class CurriculumPresenter extends AbstractMvpPresenterView<CurriculumPresenter.CurriculumView> implements CurriculumPresenterHandlers {

	public interface CurriculumView extends MvpView, MvpHasPresenterHandlers<CurriculumPresenterHandlers> {
		void initView();
		void setErrorMessage(String message);
	}
	
	@Autowired
	private CourseDAO courseDAO;
	
	@Autowired
	public CurriculumPresenter(CurriculumView view, EventBus eventBus) {
		super(view, eventBus);
		getView().setPresenterHandlers(this);
	
	}	

	@Override
	public void enter(ViewChangeEvent event) {
		getView().initView();
		
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

}
