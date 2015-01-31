package org.csulb.cecs.ui.survey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
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
@Secured({"ROLE_USER", "ROLE_ADMIN"})
public class SurveyPresenter extends AbstractMvpPresenterView<SurveyPresenter.SurveyView> implements SurveyPresenterHandlers  {

	@Autowired
	Security security;
	
	@Autowired
	public SurveyPresenter(SurveyView view, EventBus eventBus) {
		super(view, eventBus);
		getView().setPresenterHandlers(this);
	}

	public interface SurveyView extends MvpView, MvpHasPresenterHandlers<SurveyPresenterHandlers> {
		void initView();
		void setErrorMessage(String message);
	}

	@Override
	public void saveSurvey() {
		// TODO logic to add servay to database
		
	}

	@Override
	public void enter(ViewChangeEvent arg0) {
		getView().initView();		
	}
	
}
