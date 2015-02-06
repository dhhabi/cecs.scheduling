package org.csulb.cecs.ui;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.RememberMeServices;

public class UserSignedInEvent implements Serializable {
	
	@Autowired
	RememberMeServices rememberMeServices;
	
	private static final long serialVersionUID = -8843033010472505451L;

}
