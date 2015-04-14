package org.csulb.cecs.dto;

import org.csulb.cecs.domain.ScheduleProject;

public interface ProjectDAO {
	public void saveScheduleProject(ScheduleProject scheduleProject);
	public ScheduleProject getScheduleProject(String semester, String year);
	public ScheduleProject getScheduleProjectWithCourseListInit(String semester, String year);
	public ScheduleProject getScheduleProjectWithRoomListInit(String semester, String year);
	public ScheduleProject getScheduleProjectWithSectionListInit(String semester, String year);
	public ScheduleProject getScheduleProjectWithInstructorListInit(String semester, String year);
}
