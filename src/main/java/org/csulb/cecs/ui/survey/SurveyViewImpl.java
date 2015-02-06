package org.csulb.cecs.ui.survey;

import java.util.Date;

import org.csulb.cecs.domain.survey.Survey;
import org.csulb.cecs.ui.survey.SurveyPresenter.SurveyView;
import org.vaadin.spring.UIScope;
import org.vaadin.spring.VaadinComponent;
import org.vaadin.spring.mvp.view.AbstractMvpView;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.UserError;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
@UIScope
@VaadinComponent
public class SurveyViewImpl extends AbstractMvpView implements SurveyView, ClickListener {

	private SurveyPresenterHandlers surveyPresenterHandlers;
	
	private VerticalLayout layout;
	
	private VerticalLayout container;
	private FormLayout form;

	
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
		form.setSpacing(true);
		form.setMargin(false);
        form.setWidth("800px");
        form.addStyleName("light");
        container.setSpacing(true);
		container.setMargin(true);
		container.addComponent(form);
			
		buildForm();
					
		btnSubmit = new Button("Submit", FontAwesome.FLOPPY_O);
		btnSubmit.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		btnSubmit.addClickListener(this);
		container.addComponent(btnSubmit);
		container.setComponentAlignment(btnSubmit, Alignment.MIDDLE_CENTER);
	}
	
	@SuppressWarnings("deprecation")
	private void buildForm() {
		
			
		 Label section = new Label("Personal Info");
	        section.addStyleName("h2");
	        section.addStyleName("colored");
	        //form.addComponent(section);
	        //StringGenerator sg = new StringGenerator();

	        TextField name = new TextField("Name");
	       // name.setValue(sg.nextString(true) + " " + sg.nextString(true));
	        name.setWidth("50%");
	        form.addComponent(name);

	        DateField birthday = new DateField("Birthday");
	        birthday.setValue(new Date(80, 0, 31));
	        form.addComponent(birthday);

	        TextField username = new TextField("Username");
	       // username.setValue(sg.nextString(false) + sg.nextString(false));
	        username.setRequired(true);
	        form.addComponent(username);

	        OptionGroup sex = new OptionGroup("Sex");
	        sex.addItem("Female");
	        sex.addItem("Male");
	        sex.select("Male");
	        sex.addStyleName("horizontal");
	        form.addComponent(sex);

	        section = new Label("Contact Info");
	        section.addStyleName("h3");
	        section.addStyleName("colored");
	        //form.addComponent(section);

	        TextField email = new TextField("Email");
	        email.setValue("email id");
	        //email.setValue(sg.nextString(false) + "@" + sg.nextString(false)  + ".com");
	        email.setWidth("50%");
	        email.setRequired(true);
	        form.addComponent(email);

	        TextField location = new TextField("Location");
	       // location.setValue(sg.nextString(true) + ", " + sg.nextString(true));
	        location.setWidth("50%");
	        location.setComponentError(new UserError("This address doesn't exist"));
	        form.addComponent(location);

	        TextField phone = new TextField("Phone");
	        phone.setWidth("50%");
	        form.addComponent(phone);

	        HorizontalLayout wrap = new HorizontalLayout();
	        wrap.setSpacing(true);
	        wrap.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
	        wrap.setCaption("Newsletter");
	        CheckBox newsletter = new CheckBox("Subscribe to newsletter", true);
	        wrap.addComponent(newsletter);

	        ComboBox period = new ComboBox();
	        period.setTextInputAllowed(true);
	        period.addItem("Daily");
	        period.addItem("Weekly");
	        period.addItem("Monthly");
	        period.setNullSelectionAllowed(false);
	        period.select("Weekly");
	        period.addStyleName("small");
	        period.setWidth("10em");
	        wrap.addComponent(period);
	        form.addComponent(wrap);

	        section = new Label("Additional Info");
	        section.addStyleName("h4");
	        section.addStyleName("colored");
	        //form.addComponent(section);

	        TextField website = new TextField("Website");
	        website.setInputPrompt("http://");
	        website.setWidth("100%");
	        form.addComponent(website);

	        TextArea shortbio = new TextArea("Short Bio");
	        shortbio.setValue("");
	        shortbio.setWidth("100%");
	        shortbio.setRows(2);
	        form.addComponent(shortbio);

	        final RichTextArea bio = new RichTextArea("Bio");
	        bio.setWidth("100%");
	        bio.setValue("<div>Use Html here !</div>");
	        form.addComponent(bio);

	        form.setReadOnly(true);
	        bio.setReadOnly(true);

	        

	        HorizontalLayout footer = new HorizontalLayout();
	        footer.setMargin(new MarginInfo(true, false, true, false));
	        footer.setSpacing(true);
	        footer.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
	        form.addComponent(footer);
	        		
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
