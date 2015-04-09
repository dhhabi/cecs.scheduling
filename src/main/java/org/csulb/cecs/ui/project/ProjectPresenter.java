package org.csulb.cecs.ui.project;

import org.csulb.cecs.ui.ViewToken;
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
	
	//@Autowired
	//Security security;
	
	@Autowired
	public ProjectPresenter(ProjectView view, EventBus eventBus) {
		super(view, eventBus);
		getView().setPresenterHandlers(this);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		getView().init();		
	}

	

}
