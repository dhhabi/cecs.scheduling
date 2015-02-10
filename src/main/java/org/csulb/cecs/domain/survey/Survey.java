package org.csulb.cecs.domain.survey;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.csulb.cecs.domain.account.Account;

public class Survey {
	
	@Id
	@GeneratedValue
	private Long survey_id;
	@ManyToOne
	private Account professor;
	
	
	

}
