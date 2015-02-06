package org.csulb.cecs.ui.requestsurvey;

import org.csulb.cecs.domain.survey.Survey;
import org.csulb.cecs.ui.requestsurvey.SurveyRequestPresenter.SurveyRequestView;
import org.vaadin.spring.UIScope;
import org.vaadin.spring.VaadinComponent;
import org.vaadin.spring.mvp.view.AbstractMvpView;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
@UIScope
@VaadinComponent
public class SurveyRequestViewImpl extends AbstractMvpView implements SurveyRequestView, ClickListener {

	private SurveyRequestPresenterHandlers surveyRequestPresenterHandlers;
	
	private VerticalLayout layout;
	
	
	
	private Label infoLabel;
	
	
	private BeanFieldGroup<Survey> binder = new BeanFieldGroup<Survey>(Survey.class);
	
	@Override
	public void postConstruct() {
		super.postConstruct();
		layout = new VerticalLayout();
		layout.setSizeFull();
		layout.setSpacing(true);
		setCompositionRoot(layout);
		
		infoLabel = new Label("Request Survey");
		infoLabel.addStyleName(ValoTheme.LABEL_H2);
		infoLabel.setSizeUndefined();
		layout.addComponent(infoLabel);
		layout.setComponentAlignment(infoLabel, Alignment.MIDDLE_CENTER);
		
	}
	
	private void buildForm() {
		
			        		
	}

	@Override
	public void buttonClick(ClickEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub		
	}

	@Override
	public void setErrorMessage(String message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPresenterHandlers(SurveyRequestPresenterHandlers surveyRequestPresenterHandlers) {
		this.surveyRequestPresenterHandlers = surveyRequestPresenterHandlers;
	}

}
