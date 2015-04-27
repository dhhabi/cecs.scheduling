package org.csulb.cecs.ui.project;

import java.util.List;

import org.csulb.cecs.domain.Account;
import org.csulb.cecs.domain.Course;
import org.csulb.cecs.domain.Curriculum;
import org.csulb.cecs.domain.Room;
import org.csulb.cecs.domain.ScheduleProject;
import org.vaadin.spring.mvp.MvpPresenterHandlers;

public interface ProjectPresenterHandlers extends MvpPresenterHandlers {
	public boolean saveScheduleProject(ScheduleProject scheduleProject);
	public List<Account> getAllAccounts();
	public boolean isAlreadyExits(String semester,String year);
	public List<Course> getAllCourses();
	public List<Room> getAllRooms();
	public List<Curriculum> getAllCurriculums();
}
