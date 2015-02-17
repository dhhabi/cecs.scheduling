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

	@Column(name="building", nullable=false)
	private String building;
	
	@Column(name="roomno", nullable=false)
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
}
