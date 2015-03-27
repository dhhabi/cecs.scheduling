package org.csulb.cecs.ui.requestsurvey;

import org.csulb.cecs.ui.ViewToken;
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
@VaadinView(name=ViewToken.SURVEYREQUEST)
//@Secured({"ROLE_ADMIN"})
public class SurveyRequestPresenter extends AbstractMvpPresenterView<SurveyRequestPresenter.SurveyRequestView> implements SurveyRequestPresenterHandlers  {

	@Autowired
	Security security;
	
	@Autowired
	public SurveyRequestPresenter(SurveyRequestView view, EventBus eventBus) {
		super(view, eventBus);
		getView().setPresenterHandlers(this);
	}

	public interface SurveyRequestView extends MvpView, MvpHasPresenterHandlers<SurveyRequestPresenterHandlers> {
		void initView();
		void setErrorMessage(String message);
	}

	@Override
	public void enter(ViewChangeEvent arg0) {
		getView().initView();		
	}
		
}
