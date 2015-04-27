package org.csulb.cecs.ui.sections;

import java.util.List;

import org.csulb.cecs.domain.Const;
import org.csulb.cecs.domain.Room;
import org.csulb.cecs.domain.ScheduleProject;
import org.csulb.cecs.domain.Section;
import org.csulb.cecs.ui.sections.SectionsPresenter.SectionsView;
import org.vaadin.spring.UIScope;
import org.vaadin.spring.VaadinComponent;
import org.vaadin.spring.mvp.view.AbstractMvpView;

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.CloseListener;

@SuppressWarnings("serial")
@UIScope
@VaadinComponent
public class SectionsViewImpl extends AbstractMvpView implements SectionsView, ClickListener {

	private SectionsPresenterHandlers sectionsPresenterHandlers;
	
	private Panel panel;
	
	private VerticalLayout layout;
	
	private List<Room> roomList;
		

	//private BeanFieldGroup<Survey> binder = new BeanFieldGroup<Survey>(Survey.class);
	
	@Override
	public void postConstruct() {
		super.postConstruct();
		panel = new Panel();
		layout = new VerticalLayout();
		layout.setSizeFull();
		layout.setSpacing(true);
		setCompositionRoot(panel);
		panel.setContent(layout);
		
		
	}
	
	
	@Override
	public void buttonClick(ClickEvent event) {
		
	}

	@Override
	public void initView() {
		
			
		final Window dialog = new Window("Select semester and year");
		FormLayout dialogLayout = new FormLayout();
		dialog.setContent(dialogLayout);
		final ComboBox boxSemester = new ComboBox("Semester");
		boxSemester.setNullSelectionAllowed(false);
		dialogLayout.addComponent(boxSemester);
		for(String semester:Const.semesterList)
			boxSemester.addItem(semester);
		
		final ComboBox boxYear = new ComboBox("Year");
		boxYear.setNullSelectionAllowed(false);
		dialogLayout.addComponent(boxYear);
		for(String year:Const.yearList)
			boxYear.addItem(year);
		
		Button btnSubmit = new Button("Submit");
		dialogLayout.addComponent(btnSubmit);
		btnSubmit.addClickListener(new ClickListener() {
			
			@SuppressWarnings("deprecation")
			@Override
			public void buttonClick(ClickEvent event) {
				// TODO Dialog Submit Click
				if(sectionsPresenterHandlers.isCheckIfProjectExists((String)boxSemester.getValue(), (String)boxYear.getValue())){
					dialog.close();
					displaySections((String)boxSemester.getValue(), (String)boxYear.getValue());
				}else{
					Notification.show("No schedule exists for selected values!",Notification.TYPE_WARNING_MESSAGE);
				}
			}
		});
		dialog.addCloseListener(new CloseListener() {
			
			@Override
			public void windowClose(CloseEvent e) {
				// TODO Dialog Close
				if(sectionsPresenterHandlers.isCheckIfProjectExists((String)boxSemester.getValue(), (String)boxYear.getValue())){
					//dialog.close();
					displaySections((String)boxSemester.getValue(), (String)boxYear.getValue());
				}else{
					UI.getCurrent().addWindow(dialog);
					Notification.show("No schedule exists for selected values!",Notification.TYPE_WARNING_MESSAGE);
				}
			}
		});
		dialog.setSizeUndefined();
		dialog.center();
		dialog.setWidth(null);
		dialogLayout.setWidth(null);
		UI.getCurrent().addWindow(dialog);
	}

	@Override
	public void setErrorMessage(String message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPresenterHandlers(SectionsPresenterHandlers surveyRequestPresenterHandlers) {
		this.sectionsPresenterHandlers = surveyRequestPresenterHandlers;
	}

	private void displaySections(String semester,String year){
		// Get All Sections and display them
		roomList = sectionsPresenterHandlers.getAllRooms();
		layout.removeAllComponents();
		ScheduleProject scheduleProject = sectionsPresenterHandlers.getScheduleProject(semester,year);
		for(Section section: scheduleProject.getSections()){
			Panel sectionPanel = new Panel(section.getCourse().toString());
			layout.addComponent(sectionPanel);
			layout.setComponentAlignment(sectionPanel, Alignment.TOP_CENTER);
			sectionPanel.setSizeUndefined();
			VerticalLayout sectionLayout = new VerticalLayout();
			sectionPanel.setContent(sectionLayout);
			//Display section attributes here. 
			//String course="Course:";
			String instructor="Instructor:";
			String meetingRomo="Meeting Room:";
			String meetingTiming="Meeting Timing:";
			String meetingDays="Meeting Days";
			String labRoom = "Lab Room:";
			String labTiming="Lab Timing:";
			String labDays = "Lab Days";
			
			/*if(section.getCourse()!=null)
				course = section.getCourse().toString();*/
			if(section.getInstructor()!=null)
				instructor = section.getInstructor().toString();
			if(section.getMeetingRoom()!=null)
				meetingRomo = section.getMeetingRoom().toString();
			if(section.getMeetingTiming()!=null)
				meetingTiming=section.getMeetingTiming().toString();
			if(section.getMeetingDaysOfWeek()!=null)
				meetingDays=section.getMeetingDaysOfWeek().toString();
			if(section.getLabRoom()!=null)
				labRoom=section.getLabRoom().toString();
			if(section.getLabTiming()!=null)
				labTiming=section.getLabTiming().toString();
			if(section.getLabDaysOfWeek()!=null)
				labDays=section.getLabDaysOfWeek().toString();
			
			HorizontalLayout firstLine = new HorizontalLayout();
			sectionLayout.addComponent(firstLine);
			firstLine.setSpacing(true);
			//firstLine.setMargin(true);
			//firstLine.addComponent(new Label("<b>SectionId:</b>",ContentMode.HTML));
			firstLine.addComponent(new Label("<b>"+section.getSectionId().toString()+",</b>",ContentMode.HTML));
			//firstLine.addComponent(new Label("<b>Course:</b>",ContentMode.HTML));
			//firstLine.addComponent(new Label(course));
			firstLine.addComponent(new Label("<b>Instructor:</b>",ContentMode.HTML));
			firstLine.addComponent(new Label(instructor));
			
			//HorizontalLayout secondLine = new HorizontalLayout();
			//sectionLayout.addComponent(secondLine);
			//secondLine.setSpacing(true);
			//secondLine.setMargin(true);
			firstLine.addComponent(new Label("<b>Meeting Room:</b>",ContentMode.HTML));
			firstLine.addComponent(new Label(meetingRomo));
			firstLine.addComponent(new Label(meetingTiming));
			firstLine.addComponent(new Label(meetingDays));
			firstLine.addComponent(new Label("<b>Lab Room:</b>",ContentMode.HTML));
			firstLine.addComponent(new Label(labRoom));
			firstLine.addComponent(new Label(labTiming));
			firstLine.addComponent(new Label(labDays));
			firstLine.addComponent(new SectionEditor().editSection(sectionsPresenterHandlers, section, roomList, scheduleProject.getInstructorList(),semester,year));			
			
		}
	}
}
