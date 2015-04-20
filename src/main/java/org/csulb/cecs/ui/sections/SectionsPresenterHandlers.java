package org.csulb.cecs.ui.sections;

import org.csulb.cecs.domain.ScheduleProject;
import org.csulb.cecs.domain.Section;
import org.vaadin.spring.mvp.MvpPresenterHandlers;

public interface SectionsPresenterHandlers extends MvpPresenterHandlers {
	public ScheduleProject getScheduleProject(String semester,String year);
	public boolean updateSection(Section section);
}
