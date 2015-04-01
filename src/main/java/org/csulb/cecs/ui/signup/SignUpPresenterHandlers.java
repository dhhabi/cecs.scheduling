package org.csulb.cecs.ui.signup;

import org.csulb.cecs.domain.Account;
import org.vaadin.spring.mvp.MvpPresenterHandlers;

public interface SignUpPresenterHandlers extends MvpPresenterHandlers {
	
	boolean tryCreateAccount(Account account);

}
