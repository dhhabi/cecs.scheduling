package org.csulb.cecs.vaadinui;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Widgetset;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import org.springframework.security.access.annotation.Secured;
import org.vaadin.spring.UIScope;
import org.vaadin.spring.navigator.VaadinView;

import javax.annotation.PostConstruct;

/**
 * @author petter@vaadin.com
 */
@Theme("valo")
@SuppressWarnings("serial")
@VaadinView(name = AdminView.VIEW_NAME)
@Widgetset("AppWidgetset")
@UIScope
@Secured(Roles.ADMIN)
public class AdminView extends VerticalLayout implements View {

    public static final String VIEW_NAME = "adminView";

    @PostConstruct
    void init() {
        addComponent(new Label("This is the admin view"));
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }
}