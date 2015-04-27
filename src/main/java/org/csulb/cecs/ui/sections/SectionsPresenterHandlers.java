package org.csulb.cecs.ui.sections;

import java.util.List;

import org.csulb.cecs.domain.Account;
import org.csulb.cecs.domain.Course;
import org.csulb.cecs.domain.Room;
import org.csulb.cecs.domain.ScheduleProject;
import org.csulb.cecs.domain.Section;
import org.vaadin.spring.mvp.MvpPresenterHandlers;

public interface SectionsPresenterHandlers extends MvpPresenterHandlers {
	public ScheduleProject getScheduleProject(String semester,String year);
	public boolean updateSection(Section section);
	public boolean isCheckIfProjectExists(String semester,String year);
	public List<Section> getSections(Account account);
	public List<Course> getPreferredCourses(String instructorEmailId,String semester,String year);
	public boolean checkSurveyExistence(String instructorEmailId,String semester,String year);
	public List<Section> getSectionList(String semester, String year,Account instructor);
	public List<Room> getAllRooms();
	
}
