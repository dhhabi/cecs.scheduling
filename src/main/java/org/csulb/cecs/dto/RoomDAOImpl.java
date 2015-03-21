package org.csulb.cecs.dto;

import java.util.List;

import javax.transaction.Transactional;

import org.csulb.cecs.domain.DayTime;
import org.csulb.cecs.domain.Room;
import org.csulb.cecs.domain.RoomPrimaryKey;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class RoomDAOImpl implements RoomDAO {
	
	@Autowired
	SessionFactory _sessionFactory;
	
	private Session getSession(){
		return _sessionFactory.getCurrentSession();
	}

	@Override
	public void addRoom(Room room) throws HibernateException {
		getSession().save(room);
	}

	@Override
	public void updateRoom(Room room) {
		getSession().update(room);		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Room> getAllRooms() {
		return getSession().createCriteria(Room.class)
				.list();
	}

	
	@Override
	public boolean isAlreadyExist(String building, String roomNo) {
		return (getSession().createQuery("select 1 from Room r where r.building =:building and r.roomNo =:roomNo")
				.setParameter("building", building)
				.setParameter("roomNo", roomNo)
				.uniqueResult() != null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Room> searchRoom(String searchString) {
		String search = "%"+searchString+"%";
		return getSession().createCriteria(Room.class)
			    .add( Restrictions.disjunction()
			            .add( Restrictions.ilike("building", search ) )
			            .add( Restrictions.ilike("roomNo", search ) )
			            .add( Restrictions.ilike("roomType", search ) )
			        )
			    .list();
	}
	
	@Override
	public void deleteRoom(Room room) {
		getSession().delete(room);
	}

	@Override
	public Room getRoom(RoomPrimaryKey roomId) {
		Room room = (Room) getSession().createQuery("from Room where building =:building and roomNo =:roomNo")
				.setParameter("building", roomId.getBuilding())
				.setParameter("roomNo", roomId.getRoomNo())
				.uniqueResult();
		Hibernate.initialize(room);
		room.getRoomTimings().size();
		return room;
	}

		
}
