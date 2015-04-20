package org.csulb.cecs.domain;

import javax.persistence.Column;

import org.hibernate.validator.constraints.NotEmpty;
//We are not using Lab class for now- islab boolean is added to Room class insted of this 
//@Entity
public class Lab extends Room{
	
	public Lab(){
		//Pass roomType as none 
		//super("none");
	}
	
	@Column(name="labtype", nullable=false, columnDefinition = "varchar(255) default 'none'")
	@NotEmpty(message="Lab type can not be empty.")
	private String labType;

	public String getLabType() {
		return labType;
	}

	public void setLabType(String labType) {
		this.labType = labType;
	}
	
}
