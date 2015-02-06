package org.csulb.cecs.ui;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.RememberMeServices;

@SuppressWarnings("serial")
public class UserSignedOutEvent implements Serializable {

	@Autowired
	RememberMeServices rememberMeServices;
	
	public UserSignedOutEvent() {	
		
	}
	
}
