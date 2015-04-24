package org.csulb.cecs.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.joda.time.LocalTime;

@Entity
public class Availability {
	@Id
	@GeneratedValue
	private Long id;
	private String day;
	private LocalTime time;
	private Boolean available;
	
	public Availability(){
		this.available=false;
	}
	public Availability(String day,LocalTime time,Boolean available){
		this.day = day;
		this.time = time;
		this.available = available;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public LocalTime getTime() {
		return time;
	}
	public void setTime(LocalTime time) {
		this.time = time;
	}
	public Boolean getAvailable() {
		return available;
	}
	public void setAvailable(Boolean available) {
		this.available = available;
	}
	
}
