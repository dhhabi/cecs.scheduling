package org.csulb.cecs.ui.room;

import org.csulb.cecs.domain.AvailableActivities;
import org.csulb.cecs.domain.Room;
import org.csulb.cecs.domain.RoomType;
import org.csulb.cecs.ui.room.RoomPresenter.RoomView;
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
import com.vaadin.ui.CheckBox;
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
public class RoomViewImpl extends AbstractMvpView implements RoomView, ClickListener {

	private RoomPresenterHandlers mvpPresenterHandlers;
	
	private static final String BUILDING = "Building";
	private static final String ROOMNO = "Room No.";
	private static final String TYPE = "Room Type";
	private static final String IS_SMALL = "Is Small?";
	private static final String START_TIME = "Start Time";
	private static final String END_TIME="End Time";
	private static final String DAY = "Day";
	
	 private Table roomList = new Table();
	 private Table tableTiming = new Table();
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
     private VerticalLayout rightLayout = new VerticalLayout();
     private HorizontalLayout timingEditLayout = new HorizontalLayout();
     private VerticalLayout timingLayout = new VerticalLayout();
     
     ComboBox boxStartTime = new ComboBox();
     ComboBox boxEndTime = new ComboBox();
     Button btnUpdateTiming = new Button("Update Timing");
     
     TextField fieldBuilding = new TextField(BUILDING);
     TextField fieldRoomNo = new TextField(ROOMNO);
     ComboBox boxType = new ComboBox(TYPE);
     CheckBox boxIsSmall = new CheckBox(IS_SMALL);
     	
	private BeanFieldGroup<Room> binder = new BeanFieldGroup<Room>(Room.class);
	
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
		searchField.setInputPrompt("Type and click search to find a room");
		bottomLeftLayout.setWidth("100%");
		bottomLeftLayout.setComponentAlignment(addNewRoomButton, Alignment.MIDDLE_RIGHT);
		
		leftLayout.addComponent(bottomLeftLayout);
		//leftLayout.addComponent(roomList.createControls());
		leftLayout.addComponent(roomList);
		rightLayout.addComponent(editorLayout);
		rightLayout.addComponent(timingLayout);
		
		horizontalSplitPanel.setFirstComponent(leftLayout);
		horizontalSplitPanel.setSecondComponent(rightLayout);
		editorLayout.setMargin(true);
		initCourseList();
		initEditorForm();
		initTimingLayout();
	}
	
	private void initEditorForm() {
		editorLayout.addComponent(new Label("Note* You can not edit Building name and Room no once added !"));	
		
		fieldBuilding.setWidth("300px");
		fieldBuilding.setValidationVisible(false);
		fieldBuilding.setNullRepresentation("");
		fieldBuilding.setInputPrompt("Room Prefix");
		
		
		fieldRoomNo.setWidth("300px");
		fieldRoomNo.setValidationVisible(false);
		fieldRoomNo.setNullRepresentation("");
		fieldRoomNo.setInputPrompt("Room Number");
		
		for(String room:RoomType.rooms){
			boxType.addItem(room);
		}
		boxType.setNullSelectionAllowed(false);
		boxType.setValidationVisible(false);
		
		
		editorLayout.addComponent(fieldBuilding);
		editorLayout.addComponent(fieldRoomNo);
		editorLayout.addComponent(boxType);
		boxType.select(RoomType.LECTURE_ROOM);
		editorLayout.addComponent(boxIsSmall);
		
		
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
					Room room = binder.getItemDataSource().getBean();
					int status = mvpPresenterHandlers.updateRoom(room);
					if(status==1){
						Notification.show("Room Updated Successfully", Notification.TYPE_TRAY_NOTIFICATION);				
						Object itemId = roomList.getValue();
						if(itemId!=null){
							updateCourseList(room, itemId);
						}
					}else if(status==2){
						Notification.show("Database Exception occured please see the log !", Notification.TYPE_TRAY_NOTIFICATION);
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
					Room room = binder.getItemDataSource().getBean();
					int status = mvpPresenterHandlers.saveRoom(room);
					if(status==1){
						Notification.show("Room Added Successfully", Notification.TYPE_TRAY_NOTIFICATION);				
						addCourseToList(room);
					}else if(status==0){
						Notification.show("Already Exists !","A room is already exists with this information", Notification.TYPE_TRAY_NOTIFICATION);						
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
					if(mvpPresenterHandlers.getAllRooms()!=null){
						for(Room room:mvpPresenterHandlers.getAllRooms())
							addCourseToList(room);
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
					if(mvpPresenterHandlers.getAllRooms()!=null){
						for(Room room:mvpPresenterHandlers.searchRoom(searchField.getValue()))
							addCourseToList(room);
					}
				}else{
					Notification.show("Something went wrong please see the log",Notification.TYPE_TRAY_NOTIFICATION);
				}
			}
		});
		
		binder.bind(fieldBuilding, "building");
		binder.bind(fieldRoomNo, "roomNo");
		binder.bind(boxType, "roomType");
		binder.bind(boxIsSmall, "small");
		
	}

	@Override
	public void initView() {
		Room room = new Room();
		binder.setItemDataSource(room);
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
			RoomPresenterHandlers mvpPresenterHandlers) {
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
	private void addCourseToList(Room room){
		Object itemId = roomList.addItem();
		Item row = roomList.getItem(itemId);
		row.getItemProperty(BUILDING).setValue(room.getBuilding());
		row.getItemProperty(ROOMNO).setValue(room.getRoomNo());
		row.getItemProperty(TYPE).setValue(room.getRoomType());
		
		
		roomList.select(itemId);
	}
	
	@SuppressWarnings("unchecked")
	private void updateCourseList(Room room, Object itemId){
		Item row = roomList.getItem(itemId);
		row.getItemProperty(BUILDING).setValue(room.getBuilding());
		row.getItemProperty(ROOMNO).setValue(room.getRoomNo());
		row.getItemProperty(TYPE).setValue(room.getRoomType());
		roomList.select(itemId);
	}
	
	private void showValidations(){
		boxType.setValidationVisible(true);
		fieldBuilding.setValidationVisible(true);
		fieldRoomNo.setValidationVisible(true);
	}
	
	private void initTimingLayout(){
		timingEditLayout.addComponent(boxStartTime);
		timingEditLayout.addComponent(boxEndTime);
		timingEditLayout.addComponent(btnUpdateTiming);
		timingEditLayout.setWidth("100%");
		timingLayout.addComponent(timingEditLayout);
		//Init timing table 
		tableTiming.addContainerProperty(DAY, String.class, null);
		tableTiming.addContainerProperty(START_TIME, String.class, null);
		tableTiming.addContainerProperty(END_TIME, String.class, null);
		tableTiming.setSelectable(true);
		tableTiming.setNullSelectionItemId("");
		tableTiming.setImmediate(true);
		tableTiming.setHeightUndefined();
		tableTiming.setWidth("100%");
		timingLayout.addComponent(tableTiming);
		
		
		
	}
		
}
