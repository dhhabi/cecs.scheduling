package org.csulb.cecs.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CurrentSemester {
	@Id
	private int id;
	
	private String semester;
	private String year;
	
	public CurrentSemester(){
		this.id = 1;
	}
	public CurrentSemester(String semester, String year){
		this.id=1;
		this.semester=semester;
		this.year=year;
	}

	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}
	
	
}
