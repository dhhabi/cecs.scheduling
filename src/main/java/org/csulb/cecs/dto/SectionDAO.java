package org.csulb.cecs.dto;

import java.util.List;

import org.csulb.cecs.domain.Account;
import org.csulb.cecs.domain.Section;

public interface SectionDAO {
	public void updateSection(Section section);
	public List<Section> getSections(Account account);
}
