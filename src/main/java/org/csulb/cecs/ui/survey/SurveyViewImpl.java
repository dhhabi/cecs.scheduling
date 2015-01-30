package org.csulb.cecs.ui.survey;

import org.csulb.cecs.model.survey.Survey;
import org.csulb.cecs.ui.survey.SurveyPresenter.SurveyView;
import org.vaadin.spring.UIScope;
import org.vaadin.spring.VaadinComponent;
import org.vaadin.spring.mvp.view.AbstractMvpView;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
@UIScope
@VaadinComponent
public class SurveyViewImpl extends AbstractMvpView implements SurveyView, ClickListener {

	private SurveyPresenterHandlers surveyPresenterHandlers;
	
	private VerticalLayout layout;
	
	private VerticalLayout container;
	private FormLayout form;

	private TextField username;
	private PasswordField password;
	private TextField firstName;	
	private TextField lastName;
	
	private Label infoLabel;
	
	private Button btnSubmit;
	
	private BeanFieldGroup<Survey> binder = new BeanFieldGroup<Survey>(Survey.class);
	

	@Override
	public void postConstruct() {
		super.postConstruct();
		layout = new VerticalLayout();
		layout.setSizeFull();
		layout.setSpacing(true);
		setCompositionRoot(layout);
		
		infoLabel = new Label("Survey Form");
		infoLabel.addStyleName(ValoTheme.LABEL_H2);
		infoLabel.setSizeUndefined();
		layout.addComponent(infoLabel);
		layout.setComponentAlignment(infoLabel, Alignment.MIDDLE_CENTER);
		
		container = new VerticalLayout();
		container.setSizeUndefined();
		container.setSpacing(true);
		layout.addComponent(container);
		layout.setComponentAlignment(container, Alignment.MIDDLE_CENTER);
		layout.setExpandRatio(container, 1);
						
		form = new FormLayout();
		form.setWidth("400px");
		form.setSpacing(true);
		container.addComponent(form);
		buildForm();
					
		btnSubmit = new Button("Signup", FontAwesome.FLOPPY_O);
		btnSubmit.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		btnSubmit.addClickListener(this);
		container.addComponent(btnSubmit);
		container.setComponentAlignment(btnSubmit, Alignment.MIDDLE_CENTER);
	}
	
	private void buildForm() {
		username = new TextField("Username");
		username.setWidth("100%");
		username.setImmediate(true);
		username.setValidationVisible(false);
		username.setNullRepresentation("");
		username.setRequired(true);		
		form.addComponent(username);
		
	}

	@Override
	public void buttonClick(ClickEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPresenterHandlers(SurveyPresenterHandlers surveyPresenterHandlers) {
		this.surveyPresenterHandlers = surveyPresenterHandlers;
		
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setErrorMessage(String message) {
		// TODO Auto-generated method stub
		
	}

}
