package org.csulb.cecs.ui.room;

import java.util.List;

import org.csulb.cecs.domain.Room;
import org.csulb.cecs.domain.RoomPrimaryKey;
import org.csulb.cecs.dto.RoomDAO;
import org.csulb.cecs.ui.ViewToken;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.UIScope;
import org.vaadin.spring.events.EventBus;
import org.vaadin.spring.mvp.MvpHasPresenterHandlers;
import org.vaadin.spring.mvp.MvpView;
import org.vaadin.spring.mvp.presenter.AbstractMvpPresenterView;
import org.vaadin.spring.navigator.VaadinView;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;

@SuppressWarnings("serial")
@UIScope
//@Secured({"ROLE_ADMIN"})
@VaadinView(name=ViewToken.ROOMS)
public class RoomPresenter extends AbstractMvpPresenterView<RoomPresenter.RoomView> implements RoomPresenterHandlers {

	@Autowired
	private RoomDAO roomDAO;
	
	
	public RoomDAO getRoomDAO() {
		return roomDAO;
	}

	public void setRoomDAO(RoomDAO roomDAO) {
		this.roomDAO = roomDAO;
	}

	public interface RoomView extends MvpView, MvpHasPresenterHandlers<RoomPresenterHandlers> {
		void initView();
		void setErrorMessage(String message);
	}
	
	@Autowired
	public RoomPresenter(RoomView view, EventBus eventBus) {
		super(view, eventBus);
		getView().setPresenterHandlers(this);
	
	}	

	@Override
	public void enter(ViewChangeEvent event) {
		getView().initView();
		
	}

	@Override
	public int saveRoom(Room Room) {
		try{
			if(!roomDAO.isAlreadyExist(Room.getBuilding(), Room.getRoomNo()))
				roomDAO.addRoom(Room);
			else
				return 0;
		}catch(HibernateException he){
			he.printStackTrace();
			return 2;
		}
		return 1;
	}

	@Override
	public int updateRoom(Room Room) {
		try{
			roomDAO.updateRoom(Room);
		}catch(HibernateException he){
			he.printStackTrace();
			return 2;
		}
		return 1;
	}

	@Override
	public List<Room> getAllRooms() {
		try{
			return roomDAO.getAllRooms();
		}catch(HibernateException he){
			he.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Room> searchRoom(String serchString) {
		try{
			return roomDAO.searchRoom(serchString);
		}catch(HibernateException he){
			he.printStackTrace();
			return null;
		}
	}

	@Override
	public int deleteRoom(Room room) {
		try{
			roomDAO.deleteRoom(room);
		}catch(HibernateException he){
			he.printStackTrace();
			return 2;
		}
		return 1;
	}

	@Override
	public Room getRoom(RoomPrimaryKey roomId) {
		return roomDAO.getRoom(roomId);
	}
	
}
