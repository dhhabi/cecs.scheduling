package org.csulb.cecs.domain;


import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.csulb.cecs.domain.account.Account;

@Entity
@Table(name="instructor")
@PrimaryKeyJoinColumn(name="id")
public class Instructor extends Account{
	//We can add other attributes here specific to instructors
	@Override
	public String toString(){
		return getFirstName() + getLastName();
	}
	
}
