package org.csulb.cecs.ui.course;

import org.csulb.cecs.domain.AvailableActivities;
import org.csulb.cecs.domain.Course;
import org.csulb.cecs.ui.course.CoursePresenter.CourseView;
import org.vaadin.spring.UIScope;
import org.vaadin.spring.VaadinComponent;
import org.vaadin.spring.mvp.view.AbstractMvpView;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.UserError;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
@UIScope
@VaadinComponent
public class CourseViewImpl extends AbstractMvpView implements CourseView, ClickListener {

	private CoursePresenterHandlers mvpPresenterHandlers;
	
	private VerticalLayout layout;
	
	private VerticalLayout container;
	private FormLayout form;

	private TextField prefix;
	private TextField courseNo;	
	private ComboBox activity;
	
	private Button btnSubmit;
	
	private BeanFieldGroup<Course> binder = new BeanFieldGroup<Course>(Course.class);
	
	@Override
	public void postConstruct() {
		super.postConstruct();		
		setSizeFull();
		
		layout = new VerticalLayout();
		layout.setSizeFull();
		layout.setSpacing(true);
		setCompositionRoot(layout);
		
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
					
		btnSubmit = new Button("Add Course", FontAwesome.FLOPPY_O);
		btnSubmit.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		btnSubmit.addClickListener(this);
		container.addComponent(btnSubmit);
		container.setComponentAlignment(btnSubmit, Alignment.MIDDLE_CENTER);
	
	}
	
	@Override
	public void initView() {
		Course course = new Course();
		binder.setItemDataSource(course);
	}
	
	@Override
	public void buttonClick(ClickEvent event) {
		try {
			binder.commit();
			
		} catch (CommitException e) {
			Notification.show("Please validate the data !");
			prefix.setValidationVisible(true);
			courseNo.setValidationVisible(true);
		}
	}
	
	private void buildForm() {
		prefix = new TextField("Prefix");
		prefix.setWidth("100%");
		prefix.setImmediate(true);
		prefix.setValidationVisible(false);
		prefix.setNullRepresentation("");
		prefix.setRequired(true);		
		form.addComponent(prefix);
		
		courseNo = new TextField("Course No");
		courseNo.setWidth("100%");
		courseNo.setValidationVisible(false);
		courseNo.setNullRepresentation("");
		courseNo.setImmediate(true);
		courseNo.setRequired(true);
		form.addComponent(courseNo);
				
		activity = new ComboBox();
		activity.setTextInputAllowed(true);
		activity.addItem(AvailableActivities.NO_ACTIVITY);
		activity.addItem(AvailableActivities.LAB_ACTIVITY);
		activity.addItem(AvailableActivities.DISCUSSION_ACTIVITY);
		activity.setNullSelectionAllowed(false);
		activity.addStyleName("small");
		activity.setWidth("12em");
		activity.select(AvailableActivities.NO_ACTIVITY);
        form.addComponent(activity);
		
		binder.bind(prefix, "prefix");
		binder.bind(courseNo, "courseNo");
		binder.bind(activity, "activity");
	}

	

	@Override
	public void setErrorMessage(String message) {
		prefix.setComponentError(new UserError(message));
		prefix.setValidationVisible(true);
		courseNo.setValidationVisible(true);
		activity.setValidationVisible(true);
	}

	@Override
	public void setPresenterHandlers(
			CoursePresenterHandlers mvpPresenterHandlers) {
		this.mvpPresenterHandlers = mvpPresenterHandlers;
		
	}
}
