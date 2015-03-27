package org.csulb.cecs.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.csulb.cecs.domain.account.Account;

@Entity
public class Survey {
	
	@Id
	private String instructorEmailId;
	
	private int noOfCourseWantToTeach;
	
	@ElementCollection
	@OneToMany
	private List<Course> preferredCourses = new ArrayList<Course>();
	
	
	@ElementCollection
	@OneToMany
	private List<Room> prefferredRoom = new ArrayList<Room>();
	
	
	

}
