package org.csulb.cecs.ui.sections;

import java.util.List;

import org.csulb.cecs.domain.Account;
import org.csulb.cecs.domain.Const;
import org.csulb.cecs.domain.Course;
import org.csulb.cecs.domain.Day;
import org.csulb.cecs.domain.Interval;
import org.csulb.cecs.domain.Room;
import org.csulb.cecs.domain.Section;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Window.CloseListener;

public class SectionEditor extends CustomField<Section> {

    /**
	 * 
	*/
	private static final long serialVersionUID = 2404522037709613898L;
	
	private FieldGroup fieldGroup;
	
	private SectionsPresenterHandlers sectionPresenterHandler;
	private List<Room> roomList;
	private List<Account> instructorList;
	
	private String semester;
	private String year;
	
	private Label lblSectionId;
	private Label lblCourse;
	
	private ComboBox boxInstructor;
	private ComboBox boxRoom;
	private ComboBox boxMeetingStartTime;
	private ComboBox boxMeetingEndTime;
	private ComboBox boxMeetingDays;
	private ListSelect listMeetingDays;
	
	private ComboBox boxLab;
	private ComboBox boxLabStartTime;
	private ComboBox boxLabEndTime;
	private ComboBox boxLabDays;
	private ListSelect listLabDays;
	
	 DateTimeFormatter parseFormat = new DateTimeFormatterBuilder().appendPattern("h:mm a").toFormatter();
	
	//private final BeanItemContainer<Course> courseContainer =  new BeanItemContainer<Course>(Course.class);
	private final BeanItemContainer<Account> instructorContainer =  new BeanItemContainer<Account>(Account.class);
	private final BeanItemContainer<Room> roomContainer = new BeanItemContainer<Room>(Room.class);
	private final BeanItemContainer<Day> meetingDaysContainer = new BeanItemContainer<Day>(Day.class);
	private final BeanItemContainer<Day> selectedMeetingDaysContainer = new BeanItemContainer<Day>(Day.class);
	
	private final BeanItemContainer<Room> labContainer = new BeanItemContainer<Room>(Room.class);
	private final BeanItemContainer<Day> labDaysContainer = new BeanItemContainer<Day>(Day.class);
	private final BeanItemContainer<Day> selectedLabDaysContainer = new BeanItemContainer<Day>(Day.class);
	
	
	private Section section;
	
	protected Component editSection(SectionsPresenterHandlers sectionPresenterHandler,Section section,
			List<Room> roomList,List<Account> instructorList,String semester, String year){
		this.sectionPresenterHandler=sectionPresenterHandler;
		this.section = section;
		this.instructorList=instructorList;
		this.roomList=roomList;
		this.semester=semester;
		this.year=year;
		
		return initContent();
	}

    @SuppressWarnings("serial")
	@Override
    protected Component initContent() {
    	fieldGroup = new BeanFieldGroup<Section>(Section.class);
        FormLayout layout = new FormLayout();
        final Window window = new Window("Edit Section", layout);
        lblSectionId = new Label("<b>Section: "+section.getSectionId().toString()+"</b>",ContentMode.HTML);
        layout.addComponent(lblSectionId);
        lblCourse = new Label("<b>"+section.getCourse().toString()+"</b>",ContentMode.HTML);
        layout.addComponent(lblCourse);     
        
        boxInstructor = new ComboBox("Instructor");
        boxInstructor.setNullSelectionAllowed(true);
        boxInstructor.setWidth("300px");
        layout.addComponent(boxInstructor);
        boxInstructor.setContainerDataSource(instructorContainer);
        for(Account instructor:instructorList)
        	instructorContainer.addBean(instructor);
        
        HorizontalLayout instructorLayout = new HorizontalLayout();
        instructorLayout.setSpacing(true);
        final ListSelect listPreferredCourses = new ListSelect("Preferred Course");
        listPreferredCourses.setNullSelectionAllowed(false);
        listPreferredCourses.setHeight("50px");
        final ListSelect listAssignedSections = new ListSelect("Assigned Sections");
        listAssignedSections.setNullSelectionAllowed(false);
        listAssignedSections.setHeight("50px");
        instructorLayout.addComponent(listPreferredCourses);
        instructorLayout.addComponent(listAssignedSections);
        layout.addComponent(instructorLayout);
        
        boxInstructor.addValueChangeListener(new ValueChangeListener() {
			
			@Override
			public void valueChange(
					com.vaadin.data.Property.ValueChangeEvent event) {
				// TODO Instructor Value Change 
				listAssignedSections.removeAllItems();
				listPreferredCourses.removeAllItems();
				if(boxInstructor.getValue()!=null){
					for(Section section: sectionPresenterHandler.getSectionList(semester,year,instructorContainer.getItem(boxInstructor.getValue()).getBean())){
						listAssignedSections.addItem(section);
					}
					if(sectionPresenterHandler.checkSurveyExistence(instructorContainer.getItem(boxInstructor.getValue()).getBean().getUsername(), semester, year)){
						for(Course course:sectionPresenterHandler.getPreferredCourses(instructorContainer.getItem(boxInstructor.getValue()).getBean().getUsername(), semester, year)){
							listPreferredCourses.addItem(course);
						}
					}
				}				
			}
		}); 
        
       
        
        HorizontalLayout roomLayout = new HorizontalLayout();
        roomLayout.setSpacing(true);
        layout.addComponent(roomLayout);
        VerticalLayout roomLeftLayout = new VerticalLayout();
        VerticalLayout roomRightLayout = new VerticalLayout();
        roomLayout.addComponent(roomLeftLayout);
        roomLayout.addComponent(roomRightLayout);
        
        boxRoom = new ComboBox("Meeting Room");
        boxRoom.setNullSelectionAllowed(true);
       // boxRoom.setWidth("300px");
        roomLeftLayout.addComponent(boxRoom);
        boxRoom.setContainerDataSource(roomContainer);
        for(Room room:roomList)
        	roomContainer.addBean(room);
        
        boxMeetingStartTime = new ComboBox("Meeting StartTime");
        boxMeetingStartTime.setNullSelectionAllowed(true);
        roomLeftLayout.addComponent(boxMeetingStartTime);
        boxMeetingEndTime = new ComboBox("Meeting EndTime");
        boxMeetingEndTime.setNullSelectionAllowed(true);
        roomLeftLayout.addComponent(boxMeetingEndTime);
        for(String time:Const.timeList){
        	boxMeetingStartTime.addItem(time);
        	boxMeetingEndTime.addItem(time);
        }
        
        boxMeetingDays = new ComboBox("Meeting Days");
        boxMeetingDays.setNullSelectionAllowed(false);
        boxMeetingDays.setContainerDataSource(meetingDaysContainer);
        roomRightLayout.addComponent(boxMeetingDays);
        for(Day day:Day.values())
        	meetingDaysContainer.addBean(day);
        
        Button btnAddToMeetingDays = new Button("Add");
        roomLayout.addComponent(btnAddToMeetingDays);
        btnAddToMeetingDays.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				selectedMeetingDaysContainer.addBean(meetingDaysContainer.getItem(boxMeetingDays.getValue()).getBean());				
			}
		});
                       
        listMeetingDays = new ListSelect();
        listMeetingDays.setContainerDataSource(selectedMeetingDaysContainer);
        listMeetingDays.setWidth("150px");
        listMeetingDays.setHeight("80px");
        listMeetingDays.setNullSelectionAllowed(false);
        roomRightLayout.addComponent(listMeetingDays);
        Button btnRemoveFromMeetingDays = new Button("Remove");
        roomRightLayout.addComponent(btnRemoveFromMeetingDays);
        btnRemoveFromMeetingDays.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				listMeetingDays.removeItem(listMeetingDays.getValue());
			}
		});
        
        HorizontalLayout labLayout = new HorizontalLayout();
        labLayout.setSpacing(true);
        layout.addComponent(labLayout);
        VerticalLayout labLeftLayout = new VerticalLayout();
        VerticalLayout labRightLayout = new VerticalLayout();
        labLayout.addComponent(labLeftLayout);
        labLayout.addComponent(labRightLayout);
         
        boxLab = new ComboBox("Lab Room");
        // boxRoom.setWidth("300px");
         labLeftLayout.addComponent(boxLab);
         boxLab.setContainerDataSource(labContainer);
         for(Room room:roomList){
        	 if(room.isLab())
        	 labContainer.addBean(room); 
         }
         	
         
         boxLabStartTime = new ComboBox("Lab StartTime");
         boxLabStartTime.setNullSelectionAllowed(false);
         labLeftLayout.addComponent(boxLabStartTime);
         boxLabEndTime = new ComboBox("Lab EndTime");
         boxLabEndTime.setNullSelectionAllowed(false);
         labLeftLayout.addComponent(boxLabEndTime);
         for(String time:Const.timeList){
         	boxLabStartTime.addItem(time);
         	boxLabEndTime.addItem(time);
         }
         
         boxLabDays = new ComboBox("Lab Days");
         boxLabDays.setNullSelectionAllowed(false);
         boxLabDays.setContainerDataSource(labDaysContainer);
         labRightLayout.addComponent(boxLabDays);
         for(Day day:Day.values())
         	labDaysContainer.addBean(day);
         
         Button btnAddToLabDays = new Button("Add");
         labLayout.addComponent(btnAddToLabDays);
         btnAddToLabDays.addClickListener(new ClickListener() {
 			
 			@Override
 			public void buttonClick(ClickEvent event) {
 				selectedLabDaysContainer.addBean(labDaysContainer.getItem(boxLabDays.getValue()).getBean());				
 			}
 		});
         
         listLabDays = new ListSelect();
         listLabDays.setContainerDataSource(selectedLabDaysContainer);
         listLabDays.setWidth("150px");
         listLabDays.setHeight("80px");
         listLabDays.setNullSelectionAllowed(false);
         labRightLayout.addComponent(listLabDays);
       
         Button btnRemoveFromLabDays = new Button("Remove");
         labRightLayout.addComponent(btnRemoveFromLabDays);
         btnRemoveFromLabDays.addClickListener(new ClickListener() {
 			@Override
 			public void buttonClick(ClickEvent event) {
 				listLabDays.removeItem(listLabDays.getValue());
 			}
 		});
         //TODO set section valuese 
         setSectionValues();
        /*fieldGroup.bind(street, "street");
        fieldGroup.bind(zip, "zip");
        fieldGroup.bind(city, "city");
        fieldGroup.bind(country, "country");*/
        Button button = new Button("Edit", new ClickListener() {

            public void buttonClick(ClickEvent event) {
               // getUI().addWindow(window);
            	try{
            		 UI.getCurrent().addWindow(window);
            	}catch(IllegalArgumentException e){
            		Notification.show("Window already added!");
            	}
            }
        });
        window.addCloseListener(new CloseListener() {
            @SuppressWarnings("deprecation")
			public void windowClose(CloseEvent e) {
                try {
                    fieldGroup.commit();
                    //TODO get section  values and try to save it 
                    getSectionValues();
                    if(sectionPresenterHandler.updateSection(section)){
                    	Notification.show("Section Updated Successfully !",Notification.TYPE_TRAY_NOTIFICATION);
                    	
                    }else{
                    	Notification.show("Database Exception",Notification.TYPE_ERROR_MESSAGE);
                    }
                } catch (CommitException ex) {
                    ex.printStackTrace();
                }
            }
        });

        window.center();
        window.setWidth(null);
        layout.setWidth(null);
        layout.setMargin(true);
        return button;
    }

    @Override
    public Class<Section> getType() {
        return Section.class;
    }

    @Override
    protected void setInternalValue(Section section) {
        super.setInternalValue(section);
        fieldGroup.setItemDataSource(new BeanItem<Section>(section));
    }
    
    private void getSectionValues(){
    	if(instructorContainer.getItem(boxInstructor.getValue())!=null)
    		section.setInstructor(instructorContainer.getItem(boxInstructor.getValue()).getBean());
    	else
    		section.setInstructor(null);
    	
    	if(roomContainer.getItem(boxRoom.getValue())!=null)
    		section.setMeetingRoom(roomContainer.getItem(boxRoom.getValue()).getBean());
    	else
    		section.setMeetingRoom(null);
    	
    	if(boxMeetingStartTime.getValue()!=null){
    		if(boxMeetingEndTime.getValue()!=null)
    			section.setMeetingTiming(new Interval(LocalTime.parse((String)boxMeetingStartTime.getValue()), LocalTime.parse((String)boxMeetingEndTime.getValue())));
    	}else{
    		section.setMeetingTiming(null);
    	}
    		
    	
    	section.getMeetingDaysOfWeek().removeAll(section.getMeetingDaysOfWeek());
    	for(Object itemId:selectedMeetingDaysContainer.getItemIds()){
    		section.getMeetingDaysOfWeek().add(selectedMeetingDaysContainer.getItem(itemId).getBean());
    	}
    	if(labContainer.getItem(boxLab.getValue())!=null)
    		section.setLabRoom(labContainer.getItem(boxLab.getValue()).getBean());
    	if(boxLabStartTime.getValue()!=null){
    		if(boxLabEndTime.getValue()!=null)
    			section.setLabTiming(new Interval(LocalTime.parse((String)boxLabStartTime.getValue()), LocalTime.parse((String)boxLabEndTime.getValue())));
    	
    	}else{
    		section.setLabTiming(null);
    	}
    	section.getLabDaysOfWeek().removeAll(section.getLabDaysOfWeek());
    	for(Object itemId:selectedLabDaysContainer.getItemIds()){
    		section.getLabDaysOfWeek().add(selectedLabDaysContainer.getItem(itemId).getBean());
    	}
    	
    }
    private void setSectionValues(){
    	if(section.getInstructor()!=null)
    		boxInstructor.select(section.getInstructor());
    	if(section.getMeetingRoom()!=null)
    		boxRoom.select(section.getMeetingRoom());
    	if(section.getMeetingTiming()!=null){
    		boxMeetingEndTime.select(section.getMeetingTiming().getEndTime().toString(parseFormat));
        	boxMeetingStartTime.select(section.getMeetingTiming().getStartTime().toString(parseFormat));
    	}
    	if(section.getLabRoom()!=null)
    		boxLab.select(section.getLabRoom());
    	if(section.getLabTiming()!=null){
    		boxLabStartTime.select(section.getLabTiming().getStartTime().toString(parseFormat));
        	boxLabEndTime.select(section.getLabTiming().getEndTime().toString(parseFormat));
    	}
    	selectedMeetingDaysContainer.removeAllItems();
    	for(Day day:section.getMeetingDaysOfWeek())
    		selectedMeetingDaysContainer.addBean(day);
    	
    	selectedLabDaysContainer.removeAllItems();
    	for(Day day:section.getLabDaysOfWeek())
    		selectedLabDaysContainer.addBean(day);
    	
    }
}