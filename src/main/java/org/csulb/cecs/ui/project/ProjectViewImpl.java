package org.csulb.cecs.ui.project;

import org.csulb.cecs.ui.project.ProjectPresenter.ProjectView;
import org.vaadin.spring.UIScope;
import org.vaadin.spring.VaadinComponent;
import org.vaadin.spring.mvp.view.AbstractMvpView;

import com.vaadin.navigator.Navigator;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

@UIScope
@VaadinComponent
@SuppressWarnings("serial")
public class ProjectViewImpl extends AbstractMvpView implements ProjectView, ClickListener {

	private ProjectPresenterHandlers mvpPresenterHandlers;
	
	Panel panel;
	TabSheet tabSheet = new TabSheet();
	VerticalLayout layout = new VerticalLayout();
	HorizontalLayout tabOne = new HorizontalLayout();
	HorizontalLayout tabTwo = new HorizontalLayout();
	HorizontalLayout tabThree = new HorizontalLayout();
	Button btnNextTab = new Button("Next");
	
	@Override
	public void postConstruct() {	
		super.postConstruct();		
		setSizeFull();
		panel = new Panel();
		panel.setSizeFull();
		setCompositionRoot(panel);
		panel.setContent(layout);
		
		layout.addComponent(tabSheet);
		//Add tabs to tabsheet
		tabSheet.addTab(tabOne,"Naming");
		tabSheet.addTab(tabTwo,"TabTwo");
		tabSheet.addTab(tabThree,"TabThree");
		
		btnNextTab.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				tabSheet.setSelectedTab(tabTwo);
			}
		});
		
		tabOne.addComponent(btnNextTab);
	}
	
	
	@Override
	public void init() {
						
	}


	@Override
	public void buttonClick(ClickEvent event) {
				
	}


	@Override
	public void setPresenterHandlers(
			ProjectPresenterHandlers mvpPresenterHandlers) {
		this.mvpPresenterHandlers = mvpPresenterHandlers;
		
	}


	@Override
	public void setErrorMessage(String error) {
				
	}


}
