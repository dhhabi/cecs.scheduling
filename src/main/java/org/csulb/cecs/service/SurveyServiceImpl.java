package org.csulb.cecs.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.csulb.cecs.domain.Account;
import org.csulb.cecs.domain.Survey;
import org.csulb.cecs.dto.ProjectDAO;
import org.csulb.cecs.dto.SurveyDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SurveyServiceImpl implements SurveyService{
	
	@Autowired
	private ProjectDAO projectDAO;
	@Autowired
	private SurveyDAO surveyDAO;
	/**
	 * This method accept two parameters Semester and Year, 
	 * Return the Instructor list who did not fill out the survey yet.  
	 * 
	 */
	@Override
	public List<Account> getAcccountsWhoDidNotFillSurvey(String semester,
			String year) {
		List<Account> allAccountInProject = projectDAO.getScheduleProjectWithInstructorListInit(semester, year).getInstructorList();
					
		HashSet<String> emailHash = new HashSet<String>(surveyDAO.getAllInstructorEmailIds(semester, year));
		
		Iterator<Account> accountIterator = allAccountInProject.iterator();
		
		while(accountIterator.hasNext()){
			if(emailHash.contains(accountIterator.next().getUsername()))
				accountIterator.remove();
		}
		
		return allAccountInProject;
	}
	
}
