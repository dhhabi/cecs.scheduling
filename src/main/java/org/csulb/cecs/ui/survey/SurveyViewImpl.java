package org.csulb.cecs.ui.survey;

import java.util.List;

import org.csulb.cecs.domain.Availability;
import org.csulb.cecs.domain.Course;
import org.csulb.cecs.domain.CurrentSemester;
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

import com.vaadin.data.Item;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItemContainer;
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
	private Button btnUpdate;
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
	private ComboBox boxNoOfCoursesWantToTeach;
	
	final BeanItemContainer<Course> allCourseContainer =  new BeanItemContainer<Course>(Course.class);
	final BeanItemContainer<Room> allRoomsContainer = new BeanItemContainer<Room>(Room.class);
	
	final BeanItemContainer<Course> preferredCourseContainer =  new BeanItemContainer<Course>(Course.class);
	final BeanItemContainer<Room> preferredRoomsContainer = new BeanItemContainer<Room>(Room.class);
	
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
					
		btnUpdate = new Button("Update", FontAwesome.FLOPPY_O);
		btnSubmit = new Button("Submit", FontAwesome.FLOPPY_O);
		
		btnSubmit.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		btnUpdate.addStyleName(ValoTheme.BUTTON_FRIENDLY);		
		btnSubmit.addClickListener(this);
		container.addComponent(btnSubmit);
		container.addComponent(btnUpdate);
		btnUpdate.setVisible(false);
		container.setComponentAlignment(btnSubmit, Alignment.MIDDLE_CENTER);
		container.setComponentAlignment(btnUpdate, Alignment.MIDDLE_CENTER);
	}
	
	@SuppressWarnings("deprecation")
	private void buildForm() {
		
			
		 	lblInstructorEmailId = new Label();
		    //lblInstructorEmailId.setCaption("Instructor Email Id: ");
		 	infoSection.addComponent(lblInstructorEmailId);
		 	lblInstructorEmailId.setStyleName(ValoTheme.LABEL_H4);
	        
	        lblSemester = new Label();
	        //lblSemester.setCaption("Semester: ");
	        infoSection.addComponent(lblSemester);
	        lblSemester.setStyleName(ValoTheme.LABEL_H4);
	        
	        lblYear = new Label();
	        //lblYear.setCaption("Year");
	        infoSection.addComponent(lblYear);
	        lblYear.setStyleName(ValoTheme.LABEL_H4);
	        
	        form.addComponent(new Label("How many courses you anticipate teaching this semester?"));
	        boxNoOfCoursesWantToTeach = new ComboBox("Courses:");
	        boxNoOfCoursesWantToTeach.setNullSelectionAllowed(false);
	        for(int i=1;i<11;i++)
	        		boxNoOfCoursesWantToTeach.addItem(i);
	        
	        form.addComponent(boxNoOfCoursesWantToTeach);

	        HorizontalLayout allCourseLayout = new HorizontalLayout();
	        form.addComponent(allCourseLayout);
	        boxAllCourses = new ComboBox();
	        boxAllCourses.setContainerDataSource(allCourseContainer);	        
	        boxAllCourses.setNullSelectionAllowed(false);
	       // boxAllCourses.setItemCaptionPropertyId("title");
	        boxAllCourses.setInputPrompt("Select preferred courses");
	        boxAllCourses.setWidth("300px");
	        allCourseLayout.addComponent(boxAllCourses);
	        
	        btnAddToPreferredCourses = new Button("Add To Preferred");
	        allCourseLayout.addComponent(btnAddToPreferredCourses);
	        
	        btnAddToPreferredCourses.addClickListener(new ClickListener() {
				
				@Override
				public void buttonClick(ClickEvent event) {
					// TODO add to preferred course listener
					//listpreferredCourses.addItem((Course)boxAllCourses.getItem(boxAllCourses.getValue()));
					preferredCourseContainer.addItem(allCourseContainer.getItem(boxAllCourses.getValue()).getBean());
				}
			});
	        
	        listpreferredCourses = new ListSelect("Preffered Courses",preferredCourseContainer);
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
	        boxAllRooms.setContainerDataSource(allRoomsContainer);
	        boxAllRooms.setWidth("300px");
	        boxAllRooms.setInputPrompt("Select preferred rooms");
	        boxAllRooms.setNullSelectionAllowed(false);
	        preferredRoomslayout.addComponent(boxAllRooms);
	        
	        btnAddToPreferredRooms = new Button("Add to preferred rooms");
	        preferredRoomslayout.addComponent(btnAddToPreferredRooms);
	        btnAddToPreferredRooms.addClickListener(new ClickListener() {
				
				@Override
				public void buttonClick(ClickEvent event) {
					//listPreferredRooms.addItem((Room)boxAllRooms.getItem(boxAllRooms.getValue()));					
					preferredRoomsContainer.addItem(allRoomsContainer.getItem(boxAllRooms.getValue()).getBean());
				}
			});
	        form.addComponent(preferredRoomslayout);
	        
	        listPreferredRooms  = new ListSelect("Preferred Rooms",preferredRoomsContainer);
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
	        
	        form.addComponent(new Label("Availability : Block the time (Check the box) when you are not available!"));
	        
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

	@SuppressWarnings("deprecation")
	@Override
	public void buttonClick(ClickEvent event) {
		try {
			binder.commit();
			Survey survey = binder.getItemDataSource().getBean();
			if(!surveyPresenterHandlers.isSurveyAlreadyExist(lblInstructorEmailId.getValue(), lblSemester.getValue()
					, lblYear.getValue())){
				survey.setInstructorEmailId(lblInstructorEmailId.getValue());
				survey.setSemester(lblSemester.getValue());
				survey.setYear(lblYear.getValue());
				//TODO get preferred Courses
				getPreferredCourse(survey.getPreferredCourses());
				//TODO get preferred Rooms
				getPreferredRooms(survey.getPreferredRooms());
				//Get available time and initialize the table property 
				getAvailablity(survey.getAvailabilityList());
				if(surveyPresenterHandlers.saveSurvey(survey))
					Notification.show("Survey Added Successfully!", Notification.TYPE_TRAY_NOTIFICATION);
				else
					Notification.show("Something went wronge please correct the data");
			}else{
				//TODO survey already exists 
				Notification.show("Survey Already Exists! Click!",Notification.TYPE_TRAY_NOTIFICATION);
				btnUpdate.setVisible(true);
				btnUpdate.addClickListener(new ClickListener() {
					@Override
					public void buttonClick(ClickEvent event) {
						Survey survey = binder.getItemDataSource().getBean();
						survey.setInstructorEmailId(lblInstructorEmailId.getValue());
						survey.setSemester(lblSemester.getValue());
						survey.setYear(lblYear.getValue());
						//Set survey Id so it can update based on it
						survey.setSurveyId(surveyPresenterHandlers.getSurveyId(survey.getInstructorEmailId(), survey.getSemester(), survey.getYear()));
						//TODO get preferred Courses
						getPreferredCourse(survey.getPreferredCourses());
						//TODO get preferred Rooms
						getPreferredRooms(survey.getPreferredRooms());
						//Get available time and initialize the table property 
						getAvailablity(survey.getAvailabilityList());
						if(surveyPresenterHandlers.updateSurvey(survey))
							Notification.show("Survey Updated Successfully!",Notification.TYPE_TRAY_NOTIFICATION);
						else
							Notification.show("Something went wronge please correct the data");
					}
				});
				
			}			
						
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
	public void initView(String instructorId, CurrentSemester currentSemester) {
		lblInstructorEmailId.setValue(instructorId);
		lblSemester.setValue(currentSemester.getSemester());
		lblYear.setValue(currentSemester.getYear());
		Survey survey = new Survey();
		binder.setItemDataSource(survey);
		
		boxNoOfCoursesWantToTeach.setValue(3);
		
		
		//TODO populate all course
		boxAllCourses.removeAllItems();
		 if(surveyPresenterHandlers.getAllCourses()!=null){
				for(Course course:surveyPresenterHandlers.getAllCourses()){
					allCourseContainer.addItem(course);
				}					
			}
		 
		//TODO populate all rooms
		 boxAllRooms.removeAllItems();
		 if(surveyPresenterHandlers.getAllRooms()!=null){
				for(Room room:surveyPresenterHandlers.getAllRooms()){
					allRoomsContainer.addItem(room);
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
		
	private void getAvailablity(List<Availability> availablityList){
		availablityList.removeAll(availablityList);
		for(Object itemId:tableAvailability.getItemIds()){
			Item row = tableAvailability.getItem(itemId);
			for(Object columnId:row.getItemPropertyIds()){
				CheckBox timeBox = (CheckBox)row.getItemProperty(columnId).getValue();
				Availability availability = new Availability((String)columnId,(LocalTime)timeBox.getData(),timeBox.getValue());
				//availablityTable.put((LocalTime)timeBox.getData(), (String)columnId, timeBox.getValue());
				availablityList.add(availability);
			}
		}
	}

	private void getPreferredCourse(List<Course> preferredCourses){
		preferredCourses.removeAll(preferredCourses);
		for(Object itemId:listpreferredCourses.getItemIds()){
			preferredCourses.add(preferredCourseContainer.getItem(itemId).getBean());
		}
	}
	
	private void getPreferredRooms(List<Room> preferredRooms){
		preferredRooms.removeAll(preferredRooms);
		for(Object itemId:listPreferredRooms.getItemIds()){
			preferredRooms.add(preferredRoomsContainer.getItem(itemId).getBean());
		}
	}
	
}
