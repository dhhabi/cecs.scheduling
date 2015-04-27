package org.csulb.cecs.ui.project;

import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.csulb.cecs.domain.Account;
import org.csulb.cecs.domain.Const;
import org.csulb.cecs.domain.Course;
import org.csulb.cecs.domain.Curriculum;
import org.csulb.cecs.domain.Room;
import org.csulb.cecs.domain.ScheduleProject;
import org.csulb.cecs.domain.Section;
import org.csulb.cecs.ui.project.ProjectPresenter.ProjectView;
import org.csulb.cecs.utility.SendEmail;
import org.vaadin.spring.UIScope;
import org.vaadin.spring.VaadinComponent;
import org.vaadin.spring.mvp.view.AbstractMvpView;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

@UIScope
@VaadinComponent
@SuppressWarnings("serial")
public class ProjectViewImpl extends AbstractMvpView implements ProjectView, ClickListener {

	private ProjectPresenterHandlers mvpPresenterHandlers;
	
	Panel panel;
	//TabSheet tabSheet = new TabSheet();
	VerticalLayout layout = new VerticalLayout();
	HorizontalLayout tablesLayout;
	FormLayout nameAndProfessors = new FormLayout();
	Button btnNextTab = new Button("Next");
	ComboBox boxSemester;
	ComboBox boxYear;
	Table tableInstructors;
	Table tableCourses;
	Table tableRooms;
	Table tableCurriculums;
	Button btnSubmit;
	
	
	private static final String SELECT = "Select";
	private static final String INSTRUCTOR = "Instructor";
	private static final String EMAILID = "Email";
	private static final String COURSE = "Course";
	private static final String ROOM = "Room";
	private static final String NOOFSECTION = "No of Sections";
	private static final String CURRICULUM = "Curriculum";
	Container instructorContainer = new IndexedContainer();
	Container courseContainer = new IndexedContainer();
	Container roomContainer = new IndexedContainer();
	Container curriculumContainer = new IndexedContainer();
	
	private BeanFieldGroup<ScheduleProject> binder = new BeanFieldGroup<ScheduleProject>(ScheduleProject.class);
	
	@Override
	public void postConstruct() {	
		super.postConstruct();		
		setSizeFull();
		panel = new Panel();
		panel.setSizeFull();
		setCompositionRoot(panel);
		panel.setContent(layout);
		layout.addComponent(nameAndProfessors);
		
				
		//First Tab, name and select professors 
		nameAndProfessors.addComponent(new Label("Select semester, year and instructor you want to send email to"));
		boxSemester = new ComboBox("Semester");
		boxSemester.setNullSelectionAllowed(false);
		
		boxYear = new ComboBox("Year");
		boxYear.setNullSelectionAllowed(false);
		for(String year:Const.yearList)
			boxYear.addItem(year);
		
		nameAndProfessors.addComponent(boxSemester);
		nameAndProfessors.addComponent(boxYear);
		for(String semsester:Const.semesterList)
			boxSemester.addItem(semsester);
		
		tablesLayout = new HorizontalLayout();
		//tablesLayout.setMargin(true);
		tablesLayout.setSpacing(true);
		nameAndProfessors.addComponent(tablesLayout);
		tableInstructors = new Table("Select Instructors");
		initInstructorTable();
		tablesLayout.addComponent(tableInstructors);
		
		tableCourses = new Table("Select Courses");
		initCourseTable();
		tablesLayout.addComponent(tableCourses);
		
		tableRooms = new Table("Select Rooms");
		//initRoomTable();
		//tablesLayout.addComponent(tableRooms);
		
		tableCurriculums = new Table("Select Curriculums");
		initCurriculumTable();
		tablesLayout.addComponent(tableCurriculums);
		
		btnSubmit = new Button("Start Scheduling");
		
		nameAndProfessors.addComponent(btnSubmit);
		btnSubmit.addClickListener(new ClickListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void buttonClick(ClickEvent event) {
				// TODO Submit Click
				try {
					binder.commit();
					ScheduleProject scheduleProject = new ScheduleProject();
					scheduleProject.setSemester((String)boxSemester.getValue());
					scheduleProject.setYear((String)boxYear.getValue());
					getSelectedInstructors(scheduleProject.getInstructorList(), instructorContainer);
					getSelectedCoursesAndSections(scheduleProject.getSections(),scheduleProject.getCourseList(), courseContainer);
					getSelectedRooms(scheduleProject.getRoomList(), roomContainer);
					
					InternetAddress[] emailAddresses = new InternetAddress[scheduleProject.getInstructorList().size()];
					int index = 0;
					for(Account instructor:scheduleProject.getInstructorList()){
						InternetAddress address = new InternetAddress(instructor.getUsername());
						emailAddresses[index] = address;
					}
					
					
					SendEmail.sendMail(emailAddresses, Const.emailSubject, Const.emailMessage);
					
					if(mvpPresenterHandlers.isAlreadyExits(scheduleProject.getSemester(), scheduleProject.getYear())){
						Notification.show("Schedule Already Exists!",Notification.TYPE_WARNING_MESSAGE);
						
					}else{
						if(mvpPresenterHandlers.saveScheduleProject(scheduleProject)){
							Notification.show("Scheduling Initiated and Schedule is saved successfully",Notification.TYPE_TRAY_NOTIFICATION);
						}else{
							Notification.show("Somthing went wrong please see the log",Notification.TYPE_ERROR_MESSAGE);
						}
					}			
					
				} catch (CommitException e) {
					boxSemester.setValidationVisible(true);
					boxYear.setValidationVisible(true);
					e.printStackTrace();
				} catch (AddressException e) {
					Notification.show("Email Id of selected Instructor is not valid ",Notification.TYPE_ERROR_MESSAGE);
					e.printStackTrace();
				} catch (MessagingException e) {
					Notification.show("Can not send email to all instructors, Please see the log",Notification.TYPE_ERROR_MESSAGE);
					e.printStackTrace();
				}
				
			}
		});
		
		binder.bind(boxSemester, "semester");
		binder.bind(boxYear, "year");
		//End of PostConstruct
	}
	

	private void initCurriculumTable() {
		// TODO Init Curriculum Table
		curriculumContainer.addContainerProperty(SELECT, CheckBox.class, null);
		curriculumContainer.addContainerProperty(CURRICULUM, Curriculum.class,null);
		tableCurriculums.setContainerDataSource(curriculumContainer);
		tableCurriculums.setNullSelectionItemId("");
		tableCurriculums.setImmediate(true);
		tableCurriculums.setHeight("500px");
		
	}


	@SuppressWarnings("unchecked")
	@Override
	public void init() {
		
		binder.setItemDataSource(new ScheduleProject());
		//Populate instructor list
		instructorContainer.removeAllItems();
		for(Account instructor:mvpPresenterHandlers.getAllAccounts()){
			Object itemId = instructorContainer.addItem();
			Item row = instructorContainer.getItem(itemId);
			row.getItemProperty(SELECT).setValue(new CheckBox());
			row.getItemProperty(INSTRUCTOR).setValue(instructor);
			row.getItemProperty(EMAILID).setValue(instructor.getUsername());
		}
		
		//Populate course list
		courseContainer.removeAllItems();
		for(Course course:mvpPresenterHandlers.getAllCourses()){
			Object itemId = courseContainer.addItem();
			Item row = courseContainer.getItem(itemId);
			row.getItemProperty(SELECT).setValue(new CheckBox());
			row.getItemProperty(COURSE).setValue(course);
			ComboBox noOfSection = new ComboBox();
			for(int i=1;i<10;i++)
				noOfSection.addItem(i);
			
			noOfSection.select(1);
			row.getItemProperty(NOOFSECTION).setValue(noOfSection);
		}
		/*//Populate room list
		roomContainer.removeAllItems();
		for(Room room:mvpPresenterHandlers.getAllRooms()){
			Object itemId = roomContainer.addItem();
			Item row = roomContainer.getItem(itemId);
			row.getItemProperty(SELECT).setValue(new CheckBox());
			row.getItemProperty(ROOM).setValue(room);
		}*/
		//Populate CurriculumList
				curriculumContainer.removeAllItems();
				for(Curriculum curriculum :mvpPresenterHandlers.getAllCurriculums()){
					Object itemId = curriculumContainer.addItem();
					Item row = curriculumContainer.getItem(itemId);
					row.getItemProperty(SELECT).setValue(new CheckBox());
					row.getItemProperty(CURRICULUM).setValue(curriculum);
				}
	}


	@Override
	public void buttonClick(ClickEvent event) {
				
	}


	@Override
	public void setPresenterHandlers(
			ProjectPresenterHandlers mvpPresenterHandlers) {
		this.mvpPresenterHandlers = mvpPresenterHandlers;
		
	}


	@Override
	public void setErrorMessage(String error) {
				
	}

	public void initInstructorTable(){
		instructorContainer.addContainerProperty(SELECT, CheckBox.class, null);
		instructorContainer.addContainerProperty(INSTRUCTOR, Account.class, null);
		instructorContainer.addContainerProperty(EMAILID, String.class, null);
		tableInstructors.setContainerDataSource(instructorContainer);
		tableInstructors.setNullSelectionItemId("");
		tableInstructors.setImmediate(true);
		tableInstructors.setHeight("500px");	
		
	}
	
	private void initRoomTable() {
		roomContainer.addContainerProperty(SELECT, CheckBox.class, null);
		roomContainer.addContainerProperty(ROOM, Room.class,null);
		tableRooms.setContainerDataSource(roomContainer);
		tableRooms.setNullSelectionItemId("");
		tableRooms.setImmediate(true);
		tableRooms.setHeight("500px");	
	}


	private void initCourseTable() {
		courseContainer.addContainerProperty(SELECT, CheckBox.class, null);
		courseContainer.addContainerProperty(COURSE, Course.class,null);
		courseContainer.addContainerProperty(NOOFSECTION, ComboBox.class, null);
		tableCourses.setContainerDataSource(courseContainer);
		tableCourses.setNullSelectionItemId("");
		tableCourses.setImmediate(true);
		tableCourses.setHeight("500px");	
	}

	private void getSelectedInstructors(List<Account> instructorList,Container instructorContainer){
		//TODO get selected instructors
		for(Object rowId:instructorContainer.getItemIds()){
			Item row = instructorContainer.getItem(rowId);
			if(((CheckBox)row.getItemProperty(SELECT).getValue()).getValue()){
				instructorList.add((Account)row.getItemProperty(INSTRUCTOR).getValue());
			}
		}
	}
	
	private void getSelectedCoursesAndSections(List<Section> sectionList,List<Course> courseList,Container courseContainer){
		//TODO get selected Course
		for(Object rowId:courseContainer.getItemIds()){
			Item row = courseContainer.getItem(rowId);
			if(((CheckBox)row.getItemProperty(SELECT).getValue()).getValue()){
				Course course = (Course)row.getItemProperty(COURSE).getValue();
				courseList.add(course);
				for(int i=0;i<(int)((ComboBox)row.getItemProperty(NOOFSECTION).getValue()).getValue();i++){
					Section section = new Section(course);
					sectionList.add(section);
				}
			}
		}
	}
	
	private void getSelectedRooms(List<Room> roomList, Container roomContainer){
		//TODO get selected Rooms
		for(Object rowId:roomContainer.getItemIds()){
			Item row = roomContainer.getItem(rowId);
			if(((CheckBox)row.getItemProperty(SELECT).getValue()).getValue()){
				roomList.add((Room)row.getItemProperty(ROOM).getValue());
			}
		}
	}

}
