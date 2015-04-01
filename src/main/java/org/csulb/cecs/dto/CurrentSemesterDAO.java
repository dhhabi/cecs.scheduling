package org.csulb.cecs.dto;

import org.csulb.cecs.domain.CurrentSemester;

public interface CurrentSemesterDAO {
	public void setCurrentSemester(CurrentSemester currentSemester);
	public CurrentSemester getCurrentSemester();
}
