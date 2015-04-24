package org.csulb.cecs.ui.room;

import java.util.List;

import org.csulb.cecs.domain.Availability;
import org.csulb.cecs.domain.Days;
import org.csulb.cecs.domain.Room;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;
import com.vaadin.data.Item;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Window.CloseListener;

public class RoomTimingEditor extends CustomField<Room> {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//private FieldGroup fieldGroup;
	private String semester="";
	private RoomPresenterHandlers roomPresenterHandlers;
	private String buttonCaption = "Edit";
	private Room room;
	private Table tableAvailability;
	private Button btnUpdate;
	
	DateTimeFormatter parseFormat = new DateTimeFormatterBuilder().appendPattern("h:mm a").toFormatter();
		
	protected Component editAvailablity(Room room,String semester,RoomPresenterHandlers roomPresenterHandlers,String buttonCaption){
		this.semester=semester;
		this.roomPresenterHandlers=roomPresenterHandlers;
		this.buttonCaption=buttonCaption;
		this.room = room;
		return initContent();
	}

    @SuppressWarnings("serial")
	@Override
    protected Component initContent() {
        FormLayout layout = new FormLayout();
        final Window window = new Window("Room Availablity", layout);
        tableAvailability = new Table();
        initTableAvailability();
        layout.addComponent(tableAvailability);
        if(semester.equals("Spring"))
        	showAvailablity(room.getSpringAvailability());
        else
        	showAvailablity(room.getFallAvailability());
        
        btnUpdate = new Button("Update");
        layout.addComponent(btnUpdate);
        
        btnUpdate.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				// TODO Update availability button click 
				if(semester.equals("Spring"))
					getAvailablity(room.getSpringAvailability());
				else
					getAvailablity(room.getFallAvailability());
				
				int status = roomPresenterHandlers.updateRoom(room);
				if(status==1){
					Notification.show("Room Availablity Updated Successfully", Notification.TYPE_TRAY_NOTIFICATION);				
					window.close();
				}else if(status==2){
					Notification.show("Database Exception occure please see the log !", Notification.TYPE_TRAY_NOTIFICATION);
				}
			}
		});
        //layout.setComponentAlignment(btnUpdate, Alignment.BOTTOM_RIGHT);
        
        //fieldGroup = new BeanFieldGroup<Availablity>(Address.class);
        
        Button button = new Button(buttonCaption, new ClickListener() {

        	public void buttonClick(ClickEvent event) {
				window.setCaption(room.toString()+" "+semester+" Availability");
                UI.getCurrent().addWindow(window);
            }
        });
        window.addCloseListener(new CloseListener() {
            public void windowClose(CloseEvent e) {
               
            }
        });

        window.center();
        window.setWidth(null);
        layout.setWidth(null);
        layout.setMargin(true);
        return button;
    }

    @Override
    public Class<Room> getType() {
        return Room.class;
    }

    @Override
    protected void setInternalValue(Room room) {
        super.setInternalValue(room);
        //fieldGroup.setItemDataSource(new BeanItem<Room>(room));
    }
    
    @SuppressWarnings("unchecked")
	public void initTableAvailability(){
		//tableAvailability.addContainerProperty("Time", LocalTime.class, null);
		for(String day:Days.daysOfTheWeek){
			tableAvailability.addContainerProperty(day, CheckBox.class, null);
		}//int i=0;
		LocalTime time = LocalTime.parse("08:00 AM", parseFormat);
		
		while(time.isBefore(LocalTime.parse("10:00 PM",parseFormat))){
			Object itemId = tableAvailability.addItem();
			Item row = tableAvailability.getItem(itemId);
			for(Object column:row.getItemPropertyIds()){
				CheckBox box = new CheckBox(time.toString(parseFormat));
				box.setData(time);
				row.getItemProperty(column).setValue(box);
			}
			time = time.plusMinutes(30);
		}
			
	}
 
    @SuppressWarnings("unused")
	private void getAvailablity(List<Availability> availablityList){
    	availablityList.removeAll(availablityList);
		for(Object itemId:tableAvailability.getItemIds()){
			Item row = tableAvailability.getItem(itemId);
			for(Object columnId:row.getItemPropertyIds()){
				CheckBox timeBox = (CheckBox)row.getItemProperty(columnId).getValue();
				Availability availability = new Availability((String)columnId,(LocalTime)timeBox.getData(),timeBox.getValue());
				//availablityTable.put((LocalTime)timeBox.getData(), (String)columnId, timeBox.getValue());
				availablityList.add(availability);
			}
		}
	}
    private void showAvailablity(List<Availability> availablityList){
    	
    	if(availablityList.size()>2){
    		int i = 0;
        	for(Object itemId:tableAvailability.getItemIds()){
    			Item row = tableAvailability.getItem(itemId);
    			for(Object columnId:row.getItemPropertyIds()){
    				CheckBox timeBox = (CheckBox)row.getItemProperty(columnId).getValue();
    				if(availablityList.get(i).getAvailable())
    					timeBox.setValue(true);
    				i++;
    			}
    		}
    	}
    	
    }
}
