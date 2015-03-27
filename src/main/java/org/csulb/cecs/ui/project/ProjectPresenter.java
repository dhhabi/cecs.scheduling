package org.csulb.cecs.ui.project;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.csulb.cecs.ui.UserSignedInEvent;
import org.csulb.cecs.ui.ViewToken;
import org.csulb.cecs.ui.security.HttpRequestResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.AbstractRememberMeServices;
import org.vaadin.spring.UIScope;
import org.vaadin.spring.events.EventBus;
import org.vaadin.spring.events.EventScope;
import org.vaadin.spring.mvp.MvpHasPresenterHandlers;
import org.vaadin.spring.mvp.MvpView;
import org.vaadin.spring.mvp.presenter.AbstractMvpPresenterView;
import org.vaadin.spring.navigator.VaadinView;
import org.vaadin.spring.security.Security;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.UI;

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
