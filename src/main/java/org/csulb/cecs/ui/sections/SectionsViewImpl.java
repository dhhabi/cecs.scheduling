package org.csulb.cecs.ui.sections;

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
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@UIScope
@VaadinComponent
public class SectionsViewImpl extends AbstractMvpView implements SectionsView, ClickListener {

	private SectionsPresenterHandlers sectionsPresenterHandlers;
	
	private Panel panel;
	
	private VerticalLayout layout;
		

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
		// Get All Sections and display them
		layout.removeAllComponents();
		ScheduleProject scheduleProject = sectionsPresenterHandlers.getScheduleProject("Spring", "2015");
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
			firstLine.addComponent(new Label("<b>SectionId:</b>",ContentMode.HTML));
			firstLine.addComponent(new Label(section.getSectionId().toString()));
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
			firstLine.addComponent(new SectionEditor().editSection(sectionsPresenterHandlers, section, scheduleProject.getRoomList(), scheduleProject.getInstructorList()));			
			
		}
	}

	@Override
	public void setErrorMessage(String message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPresenterHandlers(SectionsPresenterHandlers surveyRequestPresenterHandlers) {
		this.sectionsPresenterHandlers = surveyRequestPresenterHandlers;
	}

}
