package org.csulb.cecs.vaadinui;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import org.springframework.context.annotation.Scope;
import org.vaadin.spring.VaadinComponent;


@SuppressWarnings("serial")
@VaadinComponent
@Scope("prototype")
public class ErrorView extends VerticalLayout implements View {

    private Label message;

    ErrorView() {
        setMargin(true);
        addComponent(message = new Label());
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        message.setValue(String.format("No such view: %s", event.getViewName()));
    }
}