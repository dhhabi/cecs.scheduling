package org.csulb.cecs.domain;

import java.io.Serializable;

import javax.persistence.Embeddable;

import org.joda.time.LocalTime;

@Embeddable
public class Interval implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private LocalTime startTime;
	private LocalTime endTime;
	
	public Interval(){
		
	}
	public Interval(LocalTime startTime,LocalTime endTime){
		this.startTime = startTime;
		this.endTime = endTime;
	}
	
	public LocalTime getStartTime() {
		return startTime;
	}
	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}
	public LocalTime getEndTime() {
		return endTime;
	}
	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}
	@Override
	public String toString(){
		return startTime.toString()+" to "+ endTime.toString();
	}
}
