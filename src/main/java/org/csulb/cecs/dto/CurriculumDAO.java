package org.csulb.cecs.dto;

import java.util.List;

import org.csulb.cecs.domain.Curriculum;

public interface CurriculumDAO {
	public boolean isAlreadyExist(String curriculumName);
	public void saveCurriculum(Curriculum curriculum);
	public Curriculum getCurriculum(String curriculumName);
	public List<Curriculum> getAllCurriculum();
}
