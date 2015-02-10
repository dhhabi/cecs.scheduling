package org.csulb.cecs.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Room {
	
	@Id
	@GeneratedValue
	private Long id;
	private String building;
	@Column(name="roomno", nullable=false, unique=true)
	private Long roomNo;
	private String type;
	
	public Room(){
		
	}
	public Room(String building,Long roomNo){
		this.building=building;
		this.roomNo=roomNo;
	}
	
	public Long getId() {
		return id;
	}

	public String getBuilding() {
		return building;
	}

	public void setBuilding(String building) {
		this.building = building;
	}

	public Long getRoomNo() {
		return roomNo;
	}

	public void setRoomNo(Long roomNo) {
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
