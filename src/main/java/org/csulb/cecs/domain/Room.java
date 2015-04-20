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
	@Column(name="roomNo", nullable=false)
	private String roomNo;
	
	@Column(name="type", columnDefinition = "varchar(255) default 'Medium'")
	//@NotEmpty(message="Type can not be blank")
	private String labType;
	
	@Column(name="lab",nullable=false,columnDefinition="boolean default false")
	private boolean lab = false;
			
	@ElementCollection(fetch=FetchType.LAZY)
	private final List<Interval> fallTimings = new ArrayList<Interval>();
	
	@ElementCollection(fetch=FetchType.LAZY)
	private final List<Interval> springTimings = new ArrayList<Interval>();
	
	@Column(name="small",nullable=false,columnDefinition="boolean default false")
	private boolean small = false;
	
	@Column(name="owned", nullable=false, columnDefinition="boolean default false")
	private boolean owned = false;
	
			
	public boolean isOwned() {
		return owned;
	}
	public void setOwned(boolean owned) {
		this.owned = owned;
	}
	
	public String getLabType() {
		return labType;
	}
	public void setLabType(String labType) {
		this.labType = labType;
	}
	public boolean isLab() {
		return lab;
	}
	public void setLab(boolean lab) {
		this.lab = lab;
	}
	public List<Interval> getFallTimings() {
		return fallTimings;
	}
	public List<Interval> getSpringTimings(){
		return springTimings;
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
		
	@Override
	public String toString(){
		return building +" "+roomNo;
	}
	}
