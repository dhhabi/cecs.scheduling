package org.csulb.cecs.domain;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.hibernate.validator.constraints.NotEmpty;

@Embeddable
public class Timing {
	
	@Enumerated(EnumType.STRING)
	@NotEmpty(message="Can not be blank")
	private Day dayOfTheWeek;
	@NotEmpty(message="Can not be blank")
	private String startTime;
	@NotEmpty(message="Can not be blank")
	private String endTime;
	
	
	public Day getDayOfTheWeek() {
		return dayOfTheWeek;
	}
	public void setDayOfTheWeek(Day dayOfTheWeek) {
		this.dayOfTheWeek = dayOfTheWeek;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
}
