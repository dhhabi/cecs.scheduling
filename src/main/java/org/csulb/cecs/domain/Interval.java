package org.csulb.cecs.domain;

import javax.persistence.Embeddable;

import org.joda.time.LocalTime;

@Embeddable
public class Interval {
	
	private LocalTime startTime;
	private LocalTime endTime;
	
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
	
}
