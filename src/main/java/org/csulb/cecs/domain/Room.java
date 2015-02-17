package org.csulb.cecs.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@IdClass(RoomPrimaryKey.class)
public class Room {
	@Id
	@Column(name="building", nullable=false)
	@NotEmpty(message="Building can not be blank")
	private String building;
	
	@Id
	@NotEmpty(message="Room No can not be blank")
	@Column(name="roomno", nullable=false)
	private String roomNo;
	
	@NotEmpty(message="Type can not be blank")
	private String type;
	
	public Room(){
		
	}
	public Room(String building,String roomNo){
		this.building=building;
		this.roomNo=roomNo;
	}
	
	public String getBuilding() {
		return building;
	}

	public void setBuilding(String building) {
		this.building = building;
	}

	public String getRoomNo() {
		return roomNo;
	}

	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString(){
		return building +" "+roomNo;
	}
	
}
