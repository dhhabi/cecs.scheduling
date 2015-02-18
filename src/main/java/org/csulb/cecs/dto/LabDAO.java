package org.csulb.cecs.dto;

import java.util.List;

import org.csulb.cecs.domain.Lab;
import org.hibernate.HibernateException;

public interface LabDAO {

	public void addLab(Lab lab) throws HibernateException;
	public void updateLab(Lab lab);
	public List<Lab> getAllLabs();
	public boolean isAlreadyExist(String building,String labNo);
	public List<Lab> searchLab(String searchString);
	void deleteRoom(Lab lab);
	
}
