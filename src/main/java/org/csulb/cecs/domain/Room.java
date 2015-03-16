package org.csulb.cecs.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@IdClass(RoomPrimaryKey.class)
public class Room {
	
	public Room(){
		
	}
	public Room(String roomType){
		this.roomType=roomType;
	}

	public Room(String building,String roomNo){
		this.building=building;
		this.roomNo=roomNo;
	}
	
	@Id
	@Column(name="building", nullable=false)
	@NotEmpty(message="Building can not be blank")
	private String building;
	
	@Id
	@NotEmpty(message="Room No can not be blank")
	@Column(name="roomno", nullable=false)
	private String roomNo;
	
	@Column(name="type", columnDefinition = "varchar(255) default 'Medium'")
	@NotEmpty(message="Type can not be blank")
	private String roomType;
	
	
	@Column(name="isavailableallday", nullable=false, columnDefinition="boolean default false")
	private boolean isAvailableAllDay;
		
	@ElementCollection(fetch=FetchType.EAGER)
	private final List<DayTime> roomTimings = new ArrayList<DayTime>();
	
	@Column(name="small",nullable=false,columnDefinition="boolean default false")
	private boolean small = false;
	
	
	
		
	public String getRoomType() {
		return roomType;
	}
	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}
	public List<DayTime> getRoomTimings() {
		return roomTimings;
	}
	public boolean isSmall() {
		return small;
	}
	public void setSmall(boolean small) {
		this.small = small;
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
	
	public boolean isAvailableAllDay() {
		return isAvailableAllDay;
	}
	public void setAvailableAllDay(boolean isAvailableAllDay) {
		this.isAvailableAllDay = isAvailableAllDay;
	}
	
	
	
	@Override
	public String toString(){
		return building +" "+roomNo;
	}
	}
