package org.csulb.cecs.service;

import java.util.List;

import org.csulb.cecs.domain.Account;


public interface SurveyService {

		public List<Account> getAcccountsWhoDidNotFillSurvey(String semester,String year);
		
}
