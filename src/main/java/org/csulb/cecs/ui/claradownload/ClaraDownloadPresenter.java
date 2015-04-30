package org.csulb.cecs.ui.claradownload;

import org.csulb.cecs.dto.ProjectDAO;
import org.csulb.cecs.service.ClaraService;
import org.csulb.cecs.service.DummyService;
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
import com.vaadin.server.StreamResource;

@SuppressWarnings("serial")
@UIScope
@VaadinView(name=ViewToken.CLARADOWNLOAD)
//@Secured({"ROLE_USER", "ROLE_ADMIN"})
public class ClaraDownloadPresenter extends AbstractMvpPresenterView<ClaraDownloadPresenter.ClaraDownloadView> implements ClaraDownloadPresenterHandlers {
	
	public interface ClaraDownloadView extends MvpView, MvpHasPresenterHandlers<ClaraDownloadPresenterHandlers> {
		public void initView();
		public void setMessage(String message);
	}
	
	@Autowired
	private ProjectDAO projectDAO;
	
	@Autowired
	private ClaraService claraService;
	
	@Autowired
	public ClaraDownloadPresenter(ClaraDownloadView view, EventBus eventBus) {
		super(view, eventBus);
		getView().setPresenterHandlers(this);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		getView().initView();
		
	}

	@Override
	public boolean checkProjectExistence(String semester, String year) {
		return projectDAO.isAlreadyExists(semester, year);
	}

	@Override
	public StreamResource createClaraProgram(String semester, String year) {
		return claraService.createProgram(semester,year);
	}

		

}
