package org.csulb.cecs.ui.survey;

import org.csulb.cecs.domain.Course;
import org.csulb.cecs.domain.Day;
import org.csulb.cecs.domain.Days;
import org.csulb.cecs.domain.Room;
import org.csulb.cecs.domain.Survey;
import org.csulb.cecs.ui.survey.SurveyPresenter.SurveyView;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;
import org.vaadin.spring.UIScope;
import org.vaadin.spring.VaadinComponent;
import org.vaadin.spring.mvp.view.AbstractMvpView;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
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
	private HorizontalLayout infoSection;
	private ComboBox boxAllCourses;
	private Button btnAddToPreferredCourses;
	private ListSelect listpreferredCourses;
	private Button btnRemoveFromPrefrredCourses;
	private ComboBox boxAllRooms;
	private Button btnAddToPreferredRooms;
	private ListSelect listPreferredRooms;
	private Button btnRemoveFromPrefferedRooms;
	private Table tableAvailability;
	private Label lblSemester;
	private Label lblYear;
	private Label lblInstructorEmailId;
	
	DateTimeFormatter parseFormat = new DateTimeFormatterBuilder().appendPattern("h:mm a").toFormatter();
	
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
		//layout.setComponentAlignment(container, Alignment.MIDDLE_CENTER);
		//layout.setExpandRatio(container, 1);
						
		form = new FormLayout();
		form.setSpacing(true);
		form.setMargin(false);
        //form.setWidth("800px");
		infoSection = new HorizontalLayout();
		infoSection.setMargin(true);
		infoSection.setSpacing(true);
		
		form.addComponent(infoSection);
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
		
			
		 	lblInstructorEmailId = new Label("");
		    infoSection.addComponent(new Label(" Instructor Email Id: "));
		 	infoSection.addComponent(lblInstructorEmailId);
	        
	        lblSemester = new Label("");
	        infoSection.addComponent(new Label(" Semester: "));
	        infoSection.addComponent(lblSemester);
	        
	        lblYear = new Label("");
	        infoSection.addComponent(new Label(" Year: "));
	        infoSection.addComponent(lblYear);
	        
	        form.addComponent(new Label("How many courses you anticipate teaching this semester?"));
	        ComboBox boxNoOfCoursesWantToTeach = new ComboBox("Courses:");
	        for(int i=1;i<11;i++)
	        		boxNoOfCoursesWantToTeach.addItem(i);
	        
	        form.addComponent(boxNoOfCoursesWantToTeach);

	        HorizontalLayout allCourseLayout = new HorizontalLayout();
	        form.addComponent(allCourseLayout);
	        boxAllCourses = new ComboBox();
	        boxAllCourses.setNullSelectionAllowed(false);
	        boxAllCourses.setInputPrompt("Select preferred courses");
	        boxAllCourses.setWidth("300px");
	        allCourseLayout.addComponent(boxAllCourses);
	        
	        btnAddToPreferredCourses = new Button("Add To Preferred");
	        allCourseLayout.addComponent(btnAddToPreferredCourses);
	        
	        btnAddToPreferredCourses.addClickListener(new ClickListener() {
				
				@Override
				public void buttonClick(ClickEvent event) {
					// TODO add to preferred course listener
					listpreferredCourses.addItem(boxAllCourses.getValue());
					
				}
			});
	        
	        listpreferredCourses = new ListSelect("Preffered Courses");
	        listpreferredCourses.setNullSelectionAllowed(false);
	        listpreferredCourses.setWidth("300px");
	        listpreferredCourses.setHeight("100px");
	        form.addComponent(listpreferredCourses);
	        
	        btnRemoveFromPrefrredCourses = new Button("Remove from Preferred Courses");
	        form.addComponent(btnRemoveFromPrefrredCourses);
	        	        
	        btnRemoveFromPrefrredCourses.addClickListener(new ClickListener() {
				
				@Override
				public void buttonClick(ClickEvent event) {
					// TODO remove frm preff list
					listpreferredCourses.removeItem(listpreferredCourses.getValue());					
				}
			});
	        
	        HorizontalLayout preferredRoomslayout = new HorizontalLayout();
	        boxAllRooms = new ComboBox();
	        boxAllRooms.setWidth("300px");
	        boxAllRooms.setInputPrompt("Select preferred rooms");
	        boxAllRooms.setNullSelectionAllowed(false);
	        preferredRoomslayout.addComponent(boxAllRooms);
	        
	        btnAddToPreferredRooms = new Button("Add to preferred rooms");
	        preferredRoomslayout.addComponent(btnAddToPreferredRooms);
	        btnAddToPreferredRooms.addClickListener(new ClickListener() {
				
				@Override
				public void buttonClick(ClickEvent event) {
					listPreferredRooms.addItem(boxAllRooms.getValue());					
				}
			});
	        form.addComponent(preferredRoomslayout);
	        
	        listPreferredRooms  = new ListSelect("Preferred Rooms");
	        listPreferredRooms.setNullSelectionAllowed(false);
	        listPreferredRooms.setWidth("300px");
	        listPreferredRooms.setHeight("100px");
	        form.addComponent(listPreferredRooms);
	        
	        btnRemoveFromPrefferedRooms = new Button("Remove from preferred rooms");
	        form.addComponent(btnRemoveFromPrefferedRooms);
	        btnRemoveFromPrefferedRooms.addClickListener(new ClickListener() {
				
				@Override
				public void buttonClick(ClickEvent event) {
					listPreferredRooms.removeItem(listPreferredRooms.getValue());				
				}
			});
	        
	        form.addComponent(new Label("Availablity : Block the time (Check the box) when you are not available!"));
	        
	        tableAvailability = new Table();
	        initTableAvailability();
	        form.addComponent(tableAvailability);
	        
	        HorizontalLayout footer = new HorizontalLayout();
	        footer.setMargin(new MarginInfo(true, false, true, false));
	        footer.setSpacing(true);
	        footer.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
	        form.addComponent(footer);
	        
	        binder.bind(boxNoOfCoursesWantToTeach, "noOfCourseWantToTeach");
	        
	        //binder.bind(listpreferredCourses, "preferredCourses");
	        //binder.bind(listPreferredRooms, "preferredRooms");
	        
	}

	@Override
	public void buttonClick(ClickEvent event) {
		try {
			binder.commit();
			Survey survey = binder.getItemDataSource().getBean();
			
			
			//Get available time and initialize the table property 
			getAvailablity(survey.getAvailablityTable());
			
			
		} catch (CommitException e) {
			Notification.show("Please correct the entered values!");
			e.printStackTrace();
		}
		
	}

	@Override
	public void setPresenterHandlers(SurveyPresenterHandlers surveyPresenterHandlers) {
		this.surveyPresenterHandlers = surveyPresenterHandlers;
		
	}

	@Override
	public void initView(String instructorId) {
		lblInstructorEmailId.setValue(instructorId);
		Survey survey = new Survey();
		binder.setItemDataSource(survey);
		
		//TODO populate all course
		 if(surveyPresenterHandlers.getAllCourses()!=null){
				for(Course course:surveyPresenterHandlers.getAllCourses()){
					boxAllCourses.addItem(course);
				}					
			}
		 
		//TODO populate all rooms
		 if(surveyPresenterHandlers.getAllRooms()!=null){
				for(Room room:surveyPresenterHandlers.getAllRooms()){
					boxAllRooms.addItem(room);
				}					
			}
				 
	}

	@Override
	public void setErrorMessage(String message) {
		// TODO Auto-generated method stub
		
	}
	
	@SuppressWarnings("unchecked")
	public void initTableAvailability(){
		//tableAvailability.addContainerProperty("Time", LocalTime.class, null);
		for(String day:Days.daysOfTheWeek)
			tableAvailability.addContainerProperty(day, CheckBox.class, null);		
		//int i=0;
		LocalTime time = LocalTime.parse("08:00 AM", parseFormat);
		
		while(time.isBefore(LocalTime.parse("11:00 PM",parseFormat))){
			Object itemId = tableAvailability.addItem();
			Item row = tableAvailability.getItem(itemId);
			for(Object column:row.getItemPropertyIds()){
				CheckBox box = new CheckBox(time.toString(parseFormat));
				box.setData(time);
				row.getItemProperty(column).setValue(box);
			}
			time = time.plusMinutes(30);
		}
			
	}
		
	private void getAvailablity(com.google.common.collect.Table<LocalTime, String, Boolean> availablityTable){
		for(Object itemId:tableAvailability.getItemIds()){
			Item row = tableAvailability.getItem(itemId);
			for(Object columnId:row.getItemPropertyIds()){
				CheckBox timeBox = (CheckBox)row.getItemProperty(columnId).getValue();
				availablityTable.put((LocalTime)timeBox.getData(), (String)columnId, timeBox.getValue());
			}
		}
	}

}
