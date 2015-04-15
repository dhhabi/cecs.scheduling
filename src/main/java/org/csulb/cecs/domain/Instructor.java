package org.csulb.cecs.domain;


import javax.persistence.Entity;


@Entity
public class Instructor extends Account{
	//We can add other attributes here specific to instructors
	
	private String instructorDetails;
	private String qualificaiton;
	
	@Override
	public String toString(){
		return getFirstName() + getLastName();
	}

	public String getInstructorDetails() {
		return instructorDetails;
	}

	public void setInstructorDetails(String instructorDetails) {
		this.instructorDetails = instructorDetails;
	}

	public String getQualificaiton() {
		return qualificaiton;
	}

	public void setQualificaiton(String qualificaiton) {
		this.qualificaiton = qualificaiton;
	}
	
		
}
