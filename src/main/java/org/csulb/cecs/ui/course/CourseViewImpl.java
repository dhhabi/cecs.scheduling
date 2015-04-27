package org.csulb.cecs.ui.course;

import org.csulb.cecs.domain.AvailableActivities;
import org.csulb.cecs.domain.Const;
import org.csulb.cecs.domain.Course;
import org.csulb.cecs.ui.course.CoursePresenter.CourseView;
import org.vaadin.spring.UIScope;
import org.vaadin.spring.VaadinComponent;
import org.vaadin.spring.mvp.view.AbstractMvpView;

import com.vaadin.data.Item;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.validator.DoubleRangeValidator;
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
	
	private static final String PREFIX = "Prefix";
	private static final String COURSENO = "Course No.";
	private static final String TITLE = "Course Title";
	private static final String UNITS = "Units";
	private static final String ACTIVITY = "Activity";
	private static final String COURSEHOURS = "Course Hours";
	private static final String ACTIVITYHOURS = "Activity Hours";
	
	
	 private Table courseList = new Table();
	 //private PagedTable courseList = new PagedTable();
     private TextField searchField = new TextField();
     private Button addNewCourseButton = new Button("New");
     private Button updateCourseButton = new Button("Update");
     private Button addCourseButton = new Button("Add");
     private Button buttonLoadAllCourses = new Button("Load All");
     private Button searchButton = new Button("Search");
     private FormLayout editorLayout = new FormLayout();
     private VerticalLayout leftLayout = new VerticalLayout();
     private HorizontalLayout bottomLeftLayout = new HorizontalLayout();
    
     TextField prefixField = new TextField("Prefix");
     TextField courseNoField = new TextField("Course No.");
     ComboBox activityBox = new ComboBox("Activity");
     TextField fieldTitle = new TextField("Title");
     ComboBox boxUnits = new ComboBox("Units");
     ComboBox boxLectureHours = new ComboBox("Lecture Hours");
     ComboBox boxActivityHours = new ComboBox("Activity Hours");
     
     
     	
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
		
		bottomLeftLayout.addComponent(searchField);
		bottomLeftLayout.addComponent(searchButton);
		searchField.setWidth("100%");
		bottomLeftLayout.addComponent(buttonLoadAllCourses);
		bottomLeftLayout.addComponent(addNewCourseButton);
		bottomLeftLayout.setExpandRatio(searchField, 1);
		searchField.setInputPrompt("Type and click search to find a course");
		bottomLeftLayout.setWidth("100%");
		bottomLeftLayout.setComponentAlignment(addNewCourseButton, Alignment.MIDDLE_RIGHT);
		
		leftLayout.addComponent(bottomLeftLayout);
		//leftLayout.addComponent(courseList.createControls());
		leftLayout.addComponent(courseList);
		
		
		horizontalSplitPanel.setFirstComponent(leftLayout);
		horizontalSplitPanel.setSecondComponent(editorLayout);
		editorLayout.setMargin(true);
		initCourseList();
		initEditorForm();
	}
	
	private void initEditorForm() {
		editorLayout.addComponent(new Label("Note* You can not edit Prefix and Course No once added !"));	
		
		prefixField.setWidth("300px");
		prefixField.setValidationVisible(false);
		prefixField.setNullRepresentation("");
		prefixField.setInputPrompt("Course Prefix");
		
		
		courseNoField.setWidth("300px");
		courseNoField.setValidationVisible(false);
		courseNoField.setNullRepresentation("");
		courseNoField.setInputPrompt("Course Number");
		
		fieldTitle.setWidth("350px");
		fieldTitle.setValidationVisible(false);
		fieldTitle.setNullRepresentation("");
		fieldTitle.setInputPrompt("Title of the course");
		
		for(int i=0;i<10;i++)
			boxUnits.addItem(i);
		boxUnits.setNullSelectionAllowed(false);
		boxUnits.setValidationVisible(false);
		
		
		for(String activity:AvailableActivities.activities){
			activityBox.addItem(activity);
		}
		activityBox.setNullSelectionAllowed(false);
		activityBox.setValidationVisible(false);
		activityBox.select(AvailableActivities.NO_ACTIVITY);
		
		editorLayout.addComponent(prefixField);
		editorLayout.addComponent(courseNoField);
		editorLayout.addComponent(fieldTitle);
		editorLayout.addComponent(boxUnits);
		editorLayout.addComponent(activityBox);
		
		boxLectureHours.setValidationVisible(false);
		boxActivityHours.setValidationVisible(false);
		
		boxLectureHours.setNullSelectionAllowed(false);
		boxActivityHours.setNullSelectionAllowed(false);
		
		for(String hour:Const.hoursList){
			boxLectureHours.addItem(hour);
			boxActivityHours.addItem(hour);
		}
		editorLayout.addComponent(boxLectureHours);
		editorLayout.addComponent(boxActivityHours);
		
		HorizontalLayout buttons = new HorizontalLayout();
		buttons.addComponent(addCourseButton);
		buttons.addComponent(updateCourseButton);
		buttons.setMargin(true);
	
				
		editorLayout.addComponent(buttons);
		
		updateCourseButton.setVisible(false);
		updateCourseButton.addClickListener(new ClickListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void buttonClick(ClickEvent event) {
				try {
					binder.commit();
					Course course = binder.getItemDataSource().getBean();
					int status = mvpPresenterHandlers.updateCourse(course);
					if(status==1){
						Notification.show("Course Updated Successfully", Notification.TYPE_TRAY_NOTIFICATION);				
						Object itemId = courseList.getValue();
						if(itemId!=null){
							updateCourseList(course, itemId);
						}
					}else if(status==2){
						Notification.show("Database Exception occure please see the log !", Notification.TYPE_TRAY_NOTIFICATION);
					}
				} catch (CommitException e) {
					showValidations();
					e.printStackTrace();
				}			
			}
		});
		
		
		addNewCourseButton.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				prefixField.setValue("");
				courseNoField.setValue("");
				fieldTitle.setValue("");
				boxUnits.setValue(3);
				boxActivityHours.setValue("2.0");
				boxLectureHours.setValue("5.0");
				activityBox.setValue(AvailableActivities.NO_ACTIVITY);
				prefixField.focus();
				//Enable primary key fields
				prefixField.setEnabled(true);
				courseNoField.setEnabled(true);
			}
		});
		
		addCourseButton.addClickListener(new ClickListener() {
			
			@SuppressWarnings("deprecation")
			@Override
			public void buttonClick(ClickEvent event) {
				try {
					binder.commit();
					Course course = binder.getItemDataSource().getBean();
					int status = mvpPresenterHandlers.saveCourse(course);
					if(status==1){
						Notification.show("Course Added Successfully", Notification.TYPE_TRAY_NOTIFICATION);				
						addCourseToList(course);
					}else if(status==0){
						Notification.show("Already Exists !","A course is already exists with this information", Notification.TYPE_TRAY_NOTIFICATION);						
					}else if(status==2){
						Notification.show("Database Exception occure please see the log !", Notification.TYPE_TRAY_NOTIFICATION);
					}
				} catch (CommitException e) {
					showValidations();
					e.printStackTrace();
				}
			}
		});
		
		buttonLoadAllCourses.addClickListener(new ClickListener() {
			
			@SuppressWarnings("deprecation")
			@Override
			public void buttonClick(ClickEvent event) {
				if(courseList.removeAllItems()){
					if(mvpPresenterHandlers.getAllCourse()!=null){
						for(Course course:mvpPresenterHandlers.getAllCourse())
							addCourseToList(course);
					}
				}else{
					Notification.show("Something went wrong please see the log",Notification.TYPE_TRAY_NOTIFICATION);
				}
			}
		});
		
		searchButton.addClickListener(new ClickListener() {
			
			@SuppressWarnings("deprecation")
			@Override
			public void buttonClick(ClickEvent event) {
				if(courseList.removeAllItems()){
					if(mvpPresenterHandlers.getAllCourse()!=null){
						for(Course course:mvpPresenterHandlers.searchCourse(searchField.getValue()))
							addCourseToList(course);
					}
				}else{
					Notification.show("Something went wrong please see the log",Notification.TYPE_TRAY_NOTIFICATION);
				}
			}
		});
		
		binder.bind(prefixField, "prefix");
		binder.bind(courseNoField, "courseNo");
		binder.bind(activityBox, "activity");
		binder.bind(boxUnits, "units");
		binder.bind(fieldTitle, "title");
		binder.bind(boxLectureHours, "lectureHours");
		binder.bind(boxActivityHours, "activityHours");
		
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
		courseList.addContainerProperty(PREFIX, String.class, null);
		courseList.addContainerProperty(COURSENO, String.class, null);
		courseList.addContainerProperty(TITLE, String.class, null);
		courseList.addContainerProperty(UNITS, Integer.class, null);
		courseList.addContainerProperty(ACTIVITY, String.class, null);
		courseList.addContainerProperty(COURSEHOURS,String.class,null);
		courseList.addContainerProperty(ACTIVITYHOURS, String.class,null);
		courseList.setSelectable(true);
		courseList.setNullSelectionItemId("");
		courseList.setImmediate(true);
		courseList.setHeightUndefined();
		courseList.setWidth("100%");
				
				
		courseList.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {
				if(!updateCourseButton.isVisible())
					updateCourseButton.setVisible(true);
				//Disable primary key fields
				prefixField.setEnabled(false);
				courseNoField.setEnabled(false);
				Object itemId = courseList.getValue();
				if(itemId!=null){
					prefixField.setValue((String)courseList.getContainerProperty(itemId,PREFIX).getValue());
					courseNoField.setValue((String)courseList.getContainerProperty(itemId, COURSENO).getValue());
					activityBox.setValue((String)courseList.getContainerProperty(itemId, ACTIVITY).getValue());
					fieldTitle.setValue((String)courseList.getContainerProperty(itemId, TITLE).getValue());
					boxUnits.setValue(courseList.getContainerProperty(itemId, UNITS).getValue());
					boxActivityHours.setValue(courseList.getContainerProperty(itemId, ACTIVITYHOURS).getValue());
					boxLectureHours.setValue(courseList.getContainerProperty(itemId, COURSEHOURS).getValue());
				}
				
			}
		});
	}
	
	@SuppressWarnings("unchecked")
	private void addCourseToList(Course course){
		Object itemId = courseList.addItem();
		Item row = courseList.getItem(itemId);
		row.getItemProperty(PREFIX).setValue(course.getPrefix());
		row.getItemProperty(COURSENO).setValue(course.getCourseNo());
		row.getItemProperty(TITLE).setValue(course.getTitle());
		row.getItemProperty(UNITS).setValue(course.getUnits());
		row.getItemProperty(ACTIVITY).setValue(course.getActivity());
		row.getItemProperty(COURSEHOURS).setValue(course.getLectureHours());
		row.getItemProperty(ACTIVITYHOURS).setValue(course.getActivityHours());
		courseList.select(itemId);
	}
	
	@SuppressWarnings("unchecked")
	private void updateCourseList(Course course, Object itemId){
		Item row = courseList.getItem(itemId);
		row.getItemProperty(PREFIX).setValue(course.getPrefix());
		row.getItemProperty(COURSENO).setValue(course.getCourseNo());
		row.getItemProperty(TITLE).setValue(course.getTitle());
		row.getItemProperty(UNITS).setValue(course.getUnits());
		row.getItemProperty(ACTIVITY).setValue(course.getActivity());
		row.getItemProperty(COURSEHOURS).setValue(course.getLectureHours());
		row.getItemProperty(ACTIVITYHOURS).setValue(course.getActivityHours());
		courseList.select(itemId);
	}
	
	private void showValidations(){
		activityBox.setValidationVisible(true);
		prefixField.setValidationVisible(true);
		courseNoField.setValidationVisible(true);
		fieldTitle.setValidationVisible(true);
		boxUnits.setValidationVisible(true);
		boxLectureHours.setValidationVisible(true);
		boxActivityHours.setValidationVisible(true);
	}
	
		
}
