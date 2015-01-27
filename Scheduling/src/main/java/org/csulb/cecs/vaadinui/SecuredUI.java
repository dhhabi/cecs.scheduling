package org.csulb.cecs.vaadinui;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.vaadin.spring.VaadinUI;
import org.vaadin.spring.boot.security.ExternalUIAuthentication;

/**
 * @author petter@vaadin.com
 */
@Theme("valo")
@VaadinUI(path = "/secured")
@Widgetset("AppWidgetset")
@ExternalUIAuthentication(authenticationUI = LoginUI.class)
@Secured({Roles.USER, Roles.ADMIN})
public class SecuredUI extends UI {

    /**
	 * 
	 */
	private static final long serialVersionUID = 982120393918430595L;
	@Autowired
    SecuredUIContent uiContent;

    @Override
    protected void init(VaadinRequest request) {
        setContent(uiContent);
    }
}