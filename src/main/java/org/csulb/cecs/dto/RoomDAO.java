package org.csulb.cecs.dto;

import java.util.List;

import org.csulb.cecs.domain.Course;
import org.csulb.cecs.domain.DayTime;
import org.csulb.cecs.domain.Room;
import org.csulb.cecs.domain.RoomPrimaryKey;
import org.hibernate.HibernateException;

public interface RoomDAO {
	public void addRoom(Room room) throws HibernateException;
	public void updateRoom(Room room);
	public List<Room> getAllRooms();
	public boolean isAlreadyExist(String building,String roomNo);
	public List<Room> searchRoom(String searchString);
	public void deleteRoom(Room room);
	public Room getRoom(RoomPrimaryKey roomId);
}
