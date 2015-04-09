package org.csulb.cecs.dto;

import org.csulb.cecs.domain.Curriculum;

public interface CurriculumDAO {
	public boolean isAlreadyExist(String curriculumName);
	public void saveCurriculum(Curriculum curriculum);
	public Curriculum getCurriculum(String curriculumName);
}
