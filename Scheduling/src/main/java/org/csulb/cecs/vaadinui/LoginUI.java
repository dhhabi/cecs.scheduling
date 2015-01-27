package org.csulb.cecs.vaadinui;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.VaadinUI;
import org.vaadin.spring.security.Security;


@SuppressWarnings("serial")
@VaadinUI(path = "/login")
public class LoginUI extends UI {

    @Autowired
    Security security;

    @Override
    protected void init(VaadinRequest request) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    private void loginSucceeded() {
    	
    }
}