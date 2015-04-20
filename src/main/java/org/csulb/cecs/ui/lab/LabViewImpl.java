package org.csulb.cecs.ui.lab;

import org.csulb.cecs.domain.AvailableActivities;
import org.csulb.cecs.domain.Lab;
import org.csulb.cecs.domain.LabType;
import org.csulb.cecs.domain.RoomType;
import org.csulb.cecs.ui.lab.LabPresenter.LabView;
import org.vaadin.spring.UIScope;
import org.vaadin.spring.VaadinComponent;
import org.vaadin.spring.mvp.view.AbstractMvpView;

import com.vaadin.data.Item;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@UIScope
@VaadinComponent
public class LabViewImpl extends AbstractMvpView implements LabView, ClickListener {

	private LabPresenterHandlers mvpPresenterHandlers;
	
	private static final String BUILDING = "Building";
	private static final String ROOMNO = "Lab No.";
	private static final String TYPE = "Lab Type";
	
	
	
	 private Table roomList = new Table();
	 //private PagedTable roomList = new PagedTable();
     private TextField searchField = new TextField();
     private Button addNewRoomButton = new Button("New");
     private Button updateRoomButton = new Button("Update");
     private Button addRoomButton = new Button("Add");
     private Button buttonLoadAllRooms = new Button("Load All");
     private Button searchButton = new Button("Search");
     private FormLayout editorLayout = new FormLayout();
     private VerticalLayout leftLayout = new VerticalLayout();
     private HorizontalLayout bottomLeftLayout = new HorizontalLayout();
    
     TextField fieldBuilding = new TextField(BUILDING);
     TextField fieldRoomNo = new TextField(ROOMNO);
     ComboBox boxType = new ComboBox(TYPE);
          
     	
	private BeanFieldGroup<Lab> binder = new BeanFieldGroup<Lab>(Lab.class);
	
	@Override
	public void postConstruct() {
		super.postConstruct();		
		setSizeFull();
		Panel panel = new Panel();
		panel.setWidth("100%");
		panel.setHeightUndefined();
		setCompositionRoot(panel);
		HorizontalSplitPanel horizontalSplitPanel = new HorizontalSplitPanel();
		panel.setContent(horizontalSplitPanel);
		
		bottomLeftLayout.addComponent(searchField);
		bottomLeftLayout.addComponent(searchButton);
		searchField.setWidth("100%");
		bottomLeftLayout.addComponent(buttonLoadAllRooms);
		bottomLeftLayout.addComponent(addNewRoomButton);
		bottomLeftLayout.setExpandRatio(searchField, 1);
		searchField.setInputPrompt("Type and click search to find a lab");
		bottomLeftLayout.setWidth("100%");
		bottomLeftLayout.setComponentAlignment(addNewRoomButton, Alignment.MIDDLE_RIGHT);
		
		leftLayout.addComponent(bottomLeftLayout);
		//leftLayout.addComponent(roomList.createControls());
		leftLayout.addComponent(roomList);
		
		
		horizontalSplitPanel.setFirstComponent(leftLayout);
		horizontalSplitPanel.setSecondComponent(editorLayout);
		editorLayout.setMargin(true);
		initCourseList();
		initEditorForm();
	}
	
	private void initEditorForm() {
		editorLayout.addComponent(new Label("Note* You can not edit Building name and Lab no once added !"));	
		
		fieldBuilding.setWidth("300px");
		fieldBuilding.setValidationVisible(false);
		fieldBuilding.setNullRepresentation("");
		fieldBuilding.setInputPrompt("Lab Prefix");
		
		
		fieldRoomNo.setWidth("300px");
		fieldRoomNo.setValidationVisible(false);
		fieldRoomNo.setNullRepresentation("");
		fieldRoomNo.setInputPrompt("Lab Number");
		
		
				
		
		for(String lab:LabType.allTypes){
			boxType.addItem(lab);
		}
		boxType.setNullSelectionAllowed(false);
		boxType.setValidationVisible(false);
		
		
		editorLayout.addComponent(fieldBuilding);
		editorLayout.addComponent(fieldRoomNo);
		editorLayout.addComponent(boxType);
		boxType.select(LabType.LINUX);
		
		HorizontalLayout buttons = new HorizontalLayout();
		buttons.addComponent(addRoomButton);
		buttons.addComponent(updateRoomButton);
		buttons.setMargin(true);
	
				
		editorLayout.addComponent(buttons);
		
		updateRoomButton.setVisible(false);
		updateRoomButton.addClickListener(new ClickListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void buttonClick(ClickEvent event) {
				try {
					binder.commit();
					Lab lab = binder.getItemDataSource().getBean();
					int status = mvpPresenterHandlers.updateLab(lab);
					if(status==1){
						Notification.show("Lab Updated Successfully", Notification.TYPE_TRAY_NOTIFICATION);				
						Object itemId = roomList.getValue();
						if(itemId!=null){
							updateCourseList(lab, itemId);
						}
					}else if(status==2){
						Notification.show("Database Exception occure please see the log !", Notification.TYPE_TRAY_NOTIFICATION);
					}
				} catch (CommitException e) {
					showValidations();
					e.printStackTrace();
				}			
			}
		});
		
		
		addNewRoomButton.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				fieldBuilding.setValue("");
				fieldRoomNo.setValue("");
				//Enable primary key field
				fieldBuilding.setEnabled(true);
				fieldRoomNo.setEnabled(true);
				boxType.setValue(AvailableActivities.NO_ACTIVITY);
				fieldBuilding.focus();
			}
		});
		
		addRoomButton.addClickListener(new ClickListener() {
			
			@SuppressWarnings("deprecation")
			@Override
			public void buttonClick(ClickEvent event) {
				try {
					binder.commit();
					Lab lab = binder.getItemDataSource().getBean();
					int status = mvpPresenterHandlers.saveLab(lab);
					if(status==1){
						Notification.show("Lab Added Successfully", Notification.TYPE_TRAY_NOTIFICATION);				
						addCourseToList(lab);
					}else if(status==0){
						Notification.show("Already Exists !","A lab is already exists with this information", Notification.TYPE_TRAY_NOTIFICATION);						
					}else if(status==2){
						Notification.show("Database Exception occure please see the log !", Notification.TYPE_TRAY_NOTIFICATION);
					}
				} catch (CommitException e) {
					showValidations();
					e.printStackTrace();
				}
			}
		});
		
		buttonLoadAllRooms.addClickListener(new ClickListener() {
			
			@SuppressWarnings("deprecation")
			@Override
			public void buttonClick(ClickEvent event) {
				if(roomList.removeAllItems()){
					if(mvpPresenterHandlers.getAllLabs()!=null){
						for(Lab lab:mvpPresenterHandlers.getAllLabs())
							addCourseToList(lab);
					}
				}else{
					Notification.show("Something went wrong please see the log",Notification.TYPE_TRAY_NOTIFICATION);
				}
			}
		});
		
		searchButton.addClickListener(new ClickListener() {
			
			@SuppressWarnings("deprecation")
			@Override
			public void buttonClick(ClickEvent event) {
				if(roomList.removeAllItems()){
					if(mvpPresenterHandlers.getAllLabs()!=null){
						for(Lab lab:mvpPresenterHandlers.searchLab(searchField.getValue()))
							addCourseToList(lab);
					}
				}else{
					Notification.show("Something went wrong please see the log",Notification.TYPE_TRAY_NOTIFICATION);
				}
			}
		});
		
		binder.bind(fieldBuilding, "building");
		binder.bind(fieldRoomNo, "roomNo");
		binder.bind(boxType, "labType");
		
	}

	@Override
	public void initView() {
		Lab lab = new Lab();
		binder.setItemDataSource(lab);
	}
	
	@Override
	public void buttonClick(ClickEvent event) {
		//Universal button click 
	}
	

	@Override
	public void setErrorMessage(String message) {
		
	}

	@Override
	public void setPresenterHandlers(
			LabPresenterHandlers mvpPresenterHandlers) {
		this.mvpPresenterHandlers = mvpPresenterHandlers;
		
	}
	
	private void initCourseList(){
		roomList.addContainerProperty(BUILDING, String.class, null);
		roomList.addContainerProperty(ROOMNO, String.class, null);
		roomList.addContainerProperty(TYPE, String.class, null);
		roomList.setSelectable(true);
		roomList.setNullSelectionItemId("");
		roomList.setImmediate(true);
		roomList.setHeightUndefined();
		roomList.setWidth("100%");
				
				
		roomList.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {
				if(!updateRoomButton.isVisible())
					updateRoomButton.setVisible(true);
				fieldBuilding.setEnabled(false);
				fieldRoomNo.setEnabled(false);
				Object itemId = roomList.getValue();
				if(itemId!=null){
					fieldBuilding.setValue((String)roomList.getContainerProperty(itemId,BUILDING).getValue());
					fieldRoomNo.setValue((String)roomList.getContainerProperty(itemId, ROOMNO).getValue());
					boxType.setValue((String)roomList.getContainerProperty(itemId, TYPE).getValue());
				}
				
			}
		});
	}
	
	@SuppressWarnings("unchecked")
	private void addCourseToList(Lab lab){
		Object itemId = roomList.addItem();
		Item row = roomList.getItem(itemId);
		row.getItemProperty(BUILDING).setValue(lab.getBuilding());
		row.getItemProperty(ROOMNO).setValue(lab.getRoomNo());
		row.getItemProperty(TYPE).setValue(lab.getLabType());
		
		
		roomList.select(itemId);
	}
	
	@SuppressWarnings("unchecked")
	private void updateCourseList(Lab lab, Object itemId){
		Item row = roomList.getItem(itemId);
		row.getItemProperty(BUILDING).setValue(lab.getBuilding());
		row.getItemProperty(ROOMNO).setValue(lab.getRoomNo());
		row.getItemProperty(TYPE).setValue(lab.getLabType());
		roomList.select(itemId);
	}
	
	private void showValidations(){
		boxType.setValidationVisible(true);
		fieldBuilding.setValidationVisible(true);
		fieldRoomNo.setValidationVisible(true);
	}
	
		
}
