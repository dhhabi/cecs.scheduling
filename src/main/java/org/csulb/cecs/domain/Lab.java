package org.csulb.cecs.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class Lab extends Room{
	
	public Lab(){
		super("none");
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
