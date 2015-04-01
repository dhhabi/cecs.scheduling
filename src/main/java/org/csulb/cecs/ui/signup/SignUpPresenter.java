package org.csulb.cecs.ui.signup;

import org.csulb.cecs.domain.Account;
import org.csulb.cecs.domain.account.AccountRepository;
import org.csulb.cecs.domain.account.UsernameAlreadyInUseException;
import org.csulb.cecs.ui.ViewToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.vaadin.spring.UIScope;
import org.vaadin.spring.events.EventBus;
import org.vaadin.spring.mvp.MvpHasPresenterHandlers;
import org.vaadin.spring.mvp.MvpView;
import org.vaadin.spring.mvp.presenter.AbstractMvpPresenterView;
import org.vaadin.spring.navigator.VaadinView;
import org.vaadin.spring.security.Security;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Notification;

@SuppressWarnings("serial")
@UIScope
//Secured({"ROLE_ADMIN"})
@VaadinView(name=ViewToken.SIGNUP)
public class SignUpPresenter extends AbstractMvpPresenterView<SignUpPresenter.SignUpView> implements SignUpPresenterHandlers {

	public interface SignUpView extends MvpView, MvpHasPresenterHandlers<SignUpPresenterHandlers> {
		void initView();
		void setErrorMessage(String message);
	}
	
	@Autowired
	AccountRepository accountRepository;
	
	@Autowired
	Security security;
	
	@Autowired
	public SignUpPresenter(SignUpView view, EventBus eventBus) {
		super(view, eventBus);
		getView().setPresenterHandlers(this);
	
	}	

	@Override
	public void enter(ViewChangeEvent event) {
		getView().initView();
		
	}

	@Override
	public boolean tryCreateAccount(Account account) {
		
		try {
			return accountRepository.createAccount(account);
														
		} catch (UsernameAlreadyInUseException e) {
			getView().setErrorMessage(e.getMessage());
			return false;
		}
		
		/*try {			
			security.login(account.getUsername(), account.getPassword());
			
			getEventBus().publish(EventScope.UI, this, new UserSignedInEvent());
			
			//Redirect to UserHome or Admin Home
			if (security.hasAuthority("ROLE_USER")) {
				UI.getCurrent().getNavigator().navigateTo(ViewToken.USER);
			} else {
				UI.getCurrent().getNavigator().navigateTo(ViewToken.ADMIN);
			}		
								
		} catch (AuthenticationException e) {
			getView().setErrorMessage(e.getMessage());
		}		*/		
		
	}
}
