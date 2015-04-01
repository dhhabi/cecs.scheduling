package org.csulb.cecs.ui.room;

import java.util.List;

import org.csulb.cecs.domain.Interval;
import org.csulb.cecs.domain.Room;
import org.csulb.cecs.domain.RoomPrimaryKey;
import org.vaadin.spring.mvp.MvpPresenterHandlers;

public interface RoomPresenterHandlers extends MvpPresenterHandlers {
	public int saveRoom(Room room);
	public int updateRoom(Room room);
	public int deleteRoom(Room room);
	public List<Room> getAllRooms();
	public List<Room> searchRoom(String serchString);
	public Room getRoom(RoomPrimaryKey roomId);
}
