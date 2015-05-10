package org.csulb.cecs.ui.claradownload;

import java.util.List;

import org.csulb.cecs.domain.Account;
import org.csulb.cecs.domain.Const;
import org.csulb.cecs.ui.claradownload.ClaraDownloadPresenter.ClaraDownloadView;
import org.csulb.cecs.ui.user.UserPresenterHandlers;
import org.vaadin.spring.UIScope;
import org.vaadin.spring.VaadinComponent;
import org.vaadin.spring.mvp.view.AbstractMvpView;

import com.vaadin.server.FileDownloader;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.StreamResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Window.CloseListener;
import com.vaadin.ui.themes.ValoTheme;

@UIScope
@VaadinComponent
@SuppressWarnings("serial")
public class ClaraDownloadViewImpl extends AbstractMvpView implements ClaraDownloadView{

	private ClaraDownloadPresenterHandlers mvpPresenterHandlers;
	
	private VerticalLayout content;
	private Label caption;
	private Label info;
	
	private Button btnDownloadClara;
	
	@Override
	public void postConstruct() {	
		super.postConstruct();
		
		content = new VerticalLayout();
		content.setSpacing(true);
		content.setMargin(true);
		setCompositionRoot(content);
								
				
				
	}
		
	@Override
	public void initView() {		
		
		final Window dialog = new Window("Select semester and year");
		FormLayout dialogLayout = new FormLayout();
		dialog.setContent(dialogLayout);
		final ComboBox boxSemester = new ComboBox("Semester");
		boxSemester.setNullSelectionAllowed(false);
		dialogLayout.addComponent(boxSemester);
		for(String semester:Const.semesterList)
			boxSemester.addItem(semester);
		
		final ComboBox boxYear = new ComboBox("Year");
		boxYear.setNullSelectionAllowed(false);
		dialogLayout.addComponent(boxYear);
		for(String year:Const.yearList)
			boxYear.addItem(year);
		
		Button btnSubmit = new Button("Submit");
		dialogLayout.addComponent(btnSubmit);
		btnSubmit.addClickListener(new ClickListener() {
			
			@SuppressWarnings("deprecation")
			@Override
			public void buttonClick(ClickEvent event) {
				// TODO Dialog Submit Click
				content.removeAllComponents();
				if(mvpPresenterHandlers.checkProjectExistence((String)boxSemester.getValue(), (String)boxYear.getValue())){
					List<Account> remainingInstructorList = mvpPresenterHandlers.checkIdAllTheSurveyAreDone((String)boxSemester.getValue(), (String)boxYear.getValue());
					if(remainingInstructorList.isEmpty()){
						dialog.close();
						dispalyButtonToDownload((String)boxSemester.getValue(), (String)boxYear.getValue());
					}else{
						dialog.close();
						displayRemainingInstructors(remainingInstructorList);
					}
					
				}else{
					Notification.show("No schedule exists for selected values!",Notification.TYPE_WARNING_MESSAGE);
				}
			}
		});
		dialog.addCloseListener(new CloseListener() {
			
			@Override
			public void windowClose(CloseEvent e) {
				
			}
		});
		dialog.setSizeUndefined();
		dialog.center();
		dialog.setWidth(null);
		dialogLayout.setWidth(null);
		UI.getCurrent().addWindow(dialog);
		
	}

	@Override
	public void setMessage(String message) {
		
		
	}

	@Override
	public void setPresenterHandlers(ClaraDownloadPresenterHandlers mvpPresenterHandlers) {
				this.mvpPresenterHandlers = mvpPresenterHandlers;
	}


	private void dispalyButtonToDownload(String semester,String year){
		
		caption = new Label("Make sure you finished editing the sections!", ContentMode.HTML);
		caption.addStyleName(ValoTheme.LABEL_H3);
		content.addComponent(caption);
		
		info = new Label("Download Clara Program for : "+semester+" "+year, ContentMode.HTML);
		info.addStyleName(ValoTheme.LABEL_H2);
		content.addComponent(info);
		
		btnDownloadClara = new Button("Click me", FontAwesome.STAR);
				
		StreamResource clara = mvpPresenterHandlers.createClaraProgram(semester,year);
        FileDownloader fileDownloader = new FileDownloader(clara);
        fileDownloader.extend(btnDownloadClara);	
        content.addComponent(btnDownloadClara);
	}
	private void displayRemainingInstructors(List<Account> remainingInstructorList){
		
		caption = new Label("Not all the instructor's are done with survey yet, Remaining Instructors are:");
		caption.addStyleName(ValoTheme.LABEL_H3);
		content.addComponent(caption);

		for(Account account:remainingInstructorList){
			HorizontalLayout instructorlayout = new HorizontalLayout();
			instructorlayout.setSpacing(true);
			instructorlayout.addComponent(new Label(account.getFirstName()));
			instructorlayout.addComponent(new Label(account.getLastName()));
			instructorlayout.addComponent(new Label(account.getUsername()));
			content.addComponent(instructorlayout);
			
		}
	}
	
}
