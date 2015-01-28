package org.csulb.cecs.ui.admin;

import org.csulb.cecs.ui.admin.AdminPresenter.AdminView;
import org.vaadin.spring.VaadinComponent;
import org.vaadin.spring.UIScope;
import org.vaadin.spring.mvp.view.AbstractMvpView;

import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
@UIScope
@VaadinComponent
public class AdminViewImpl extends AbstractMvpView implements AdminView {
	
	private CssLayout layout;
	
	@Override
	public void postConstruct() {	
		super.postConstruct();
		setSizeFull();
		layout = new CssLayout();
		layout.setSizeFull();
		setCompositionRoot(layout);
		
		
		final Label label = new Label("This is admin only view");
		label.addStyleName(ValoTheme.LABEL_H2);
		layout.addComponent(label);		
		
	}
}
