package org.csulb.cecs.dto;

import java.util.List;

import org.csulb.cecs.domain.Account;
import org.csulb.cecs.domain.ScheduleProject;
import org.csulb.cecs.ui.sidebar.Sections;

public interface ProjectDAO {
	public void saveScheduleProject(ScheduleProject scheduleProject);
	public ScheduleProject getScheduleProject(String semester, String year);
	public ScheduleProject getScheduleProjectWithCourseListInit(String semester, String year);
	public ScheduleProject getScheduleProjectWithRoomListInit(String semester, String year);
	public ScheduleProject getScheduleProjectWithSectionListInit(String semester, String year);
	public ScheduleProject getScheduleProjectWithInstructorListInit(String semester, String year);
	public boolean isAlreadyExists(String semester,String year);
	//public List<Sections> getSections(String semester,String year,Account instructor);
}
