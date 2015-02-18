package org.csulb.cecs.ui.lab;

import java.util.List;

import org.csulb.cecs.domain.Lab;
import org.vaadin.spring.mvp.MvpPresenterHandlers;

public interface LabPresenterHandlers extends MvpPresenterHandlers {
	public int saveLab(Lab lab);
	public int updateLab(Lab lab);
	public int deleteLab(Lab lab);
	public List<Lab> getAllLabs();
	public List<Lab> searchLab(String serchString);
}
