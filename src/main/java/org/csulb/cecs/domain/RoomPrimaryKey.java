package org.csulb.cecs.domain;

import java.io.Serializable;

import javax.persistence.Column;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;


public class RoomPrimaryKey implements Serializable {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 6342526105920112627L;

	public RoomPrimaryKey() {
		
	}
	public RoomPrimaryKey(String building, String roomNo){
		this.building=building;
		this.roomNo=roomNo;
	}
	
	@Column(name="building", nullable=false)
	private String building;
	
	@Column(name="roomNo", nullable=false)
	private String roomNo;
	
	@Override
	public int hashCode(){
		return new HashCodeBuilder().append(building).append(roomNo).toHashCode();
	}
	
	@Override
	public boolean equals(Object obj){
		if( obj == null ) return false;
		if( obj == this ) return true;
		if( obj.getClass() != getClass()) return false;
		RoomPrimaryKey rhs = (RoomPrimaryKey)obj;
		return new EqualsBuilder().append(building, rhs.building).append(roomNo, rhs.roomNo).isEquals();
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
	
	
}
