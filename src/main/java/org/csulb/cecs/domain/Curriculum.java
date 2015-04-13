package org.csulb.cecs.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

@Entity
public class Curriculum {

	@Id
	@NotNull(message="Name can not be null!")
	private String name;
	
	private boolean essential;
	
	@ElementCollection
	@ManyToMany(cascade=CascadeType.ALL)
	private final List<Course> curriculumCourseList = new ArrayList<Course>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Course> getCurriculumCourseList() {
		return curriculumCourseList;
	}

	public boolean isEssential() {
		return essential;
	}

	public void setEssential(boolean essential) {
		this.essential = essential;
	} 
		
}
