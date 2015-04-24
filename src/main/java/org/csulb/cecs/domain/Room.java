package org.csulb.cecs.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

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
			
	@OneToMany(cascade=CascadeType.ALL)
	@JoinTable(name="fallAvailability")
	private final List<Availability> fallAvailability = new ArrayList<Availability>();
	
	@OneToMany(cascade=CascadeType.ALL)
	@JoinTable(name="springAvailability")
	private final List<Availability> springAvailability = new ArrayList<Availability>();
	
	
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
	
	public List<Availability> getFallAvailability() {
		return fallAvailability;
	}
	public List<Availability> getSpringAvailability() {
		return springAvailability;
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
