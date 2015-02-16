package org.csulb.cecs.ui.course;

import org.csulb.cecs.domain.AvailableActivities;
import org.csulb.cecs.domain.Course;
import org.csulb.cecs.ui.course.CoursePresenter.CourseView;
import org.hibernate.HibernateException;
import org.vaadin.spring.UIScope;
import org.vaadin.spring.VaadinComponent;
import org.vaadin.spring.mvp.view.AbstractMvpView;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@UIScope
@VaadinComponent
public class CourseViewImpl extends AbstractMvpView implements CourseView, ClickListener {

	private CoursePresenterHandlers mvpPresenterHandlers;
	
	 private Table courseList = new Table();
     private TextField searchField = new TextField();
     private Button addNewCourseButton = new Button("New");
     private Button removeCourseButton = new Button("Remove this Course");
     private Button addCourseButton = new Button("Add Course");
     private FormLayout editorLayout = new FormLayout();
     private FieldGroup editorFields = new FieldGroup();
     private VerticalLayout leftLayout = new VerticalLayout();
     private HorizontalLayout bottomLeftLayout = new HorizontalLayout();
	
	private BeanFieldGroup<Course> binder = new BeanFieldGroup<Course>(Course.class);
	
	@Override
	public void postConstruct() {
		super.postConstruct();		
		setSizeFull();
		Panel panel = new Panel("Available Courses");
		panel.setWidth("100%");
		panel.setHeightUndefined();
		setCompositionRoot(panel);
		HorizontalSplitPanel horizontalSplitPanel = new HorizontalSplitPanel();
		panel.setContent(horizontalSplitPanel);
		leftLayout.addComponent(courseList);
		leftLayout.addComponent(bottomLeftLayout);
		bottomLeftLayout.addComponent(searchField);
		searchField.setWidth("100%");
		bottomLeftLayout.addComponent(addNewCourseButton);
		bottomLeftLayout.setExpandRatio(searchField, 1);
		bottomLeftLayout.setWidth("100%");
		bottomLeftLayout.setComponentAlignment(addNewCourseButton, Alignment.MIDDLE_RIGHT);
		horizontalSplitPanel.setFirstComponent(leftLayout);
		courseList.setWidth("100%");
		horizontalSplitPanel.setSecondComponent(editorLayout);
		editorLayout.setMargin(true);
		initCourseList();
		initEditorForm();
	}
	
	private void initEditorForm() {
		editorLayout.addComponent(new Label("Edit selected course: "));	
		TextField prefixField = new TextField("Prefix");
		prefixField.setWidth("300px");
		prefixField.setValidationVisible(false);
		prefixField.setNullRepresentation("");
		prefixField.setInputPrompt("Course Prefix");
		
		TextField courseNoField = new TextField("Course No.");
		courseNoField.setWidth("300px");
		courseNoField.setValidationVisible(false);
		courseNoField.setNullRepresentation("");
		courseNoField.setInputPrompt("Course Number");
		ComboBox activityBox = new ComboBox("Activity");
		activityBox.addItem(AvailableActivities.NO_ACTIVITY);
		activityBox.addItem(AvailableActivities.LAB_ACTIVITY);
		activityBox.addItem(AvailableActivities.DISCUSSION_ACTIVITY);
		activityBox.select(AvailableActivities.NO_ACTIVITY);
		editorLayout.addComponent(prefixField);
		editorLayout.addComponent(courseNoField);
		editorLayout.addComponent(activityBox);
		editorLayout.addComponent(addCourseButton);
		
		addCourseButton.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				try {
					binder.commit();
					Course course = binder.getItemDataSource().getBean();
					if(mvpPresenterHandlers.saveCourse(course))
						Notification.show("Course Added Successfully");
					else
						Notification.show("Something went wrong!");
				} catch (CommitException e) {
					e.printStackTrace();
				} catch(HibernateException e){
					e.printStackTrace();
				}
			}
		});
		
		binder.bind(prefixField, "prefix");
		binder.bind(courseNoField, "courseNo");
		binder.bind(activityBox, "activity");
	}

	@Override
	public void initView() {
		Course course = new Course();
		binder.setItemDataSource(course);
	}
	
	@Override
	public void buttonClick(ClickEvent event) {
		//Universal button click 
	}
	

	@Override
	public void setErrorMessage(String message) {
		
	}

	@Override
	public void setPresenterHandlers(
			CoursePresenterHandlers mvpPresenterHandlers) {
		this.mvpPresenterHandlers = mvpPresenterHandlers;
		
	}
	
	private void initCourseList(){
		courseList.addContainerProperty("Id", Long.class, null);
		courseList.addContainerProperty("Prefix", String.class, null);
		courseList.addContainerProperty("Course No.", String.class, null);
		courseList.addContainerProperty("Activity", String.class, null);
	}
		
}
